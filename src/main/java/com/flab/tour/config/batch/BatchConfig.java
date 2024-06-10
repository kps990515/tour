package com.flab.tour.config.batch;

import com.flab.tour.db.product.ProductAvailabilityEntity;
import com.flab.tour.db.reservation.ReservationEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    // 배치작업의 메타데이터 관리 저장소
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet(); //초기화 작업 완료 & JobRepository 객체 생성
        return factory.getObject();
    }

    @Bean
    public Job completeReservationJob(JobRepository jobRepository) {
        return new JobBuilder("completeReservationJob", jobRepository)
                .start(completeReservationStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step completeReservationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("completeReservationStep", jobRepository)
                .<ReservationEntity, ReservationEntity>chunk(100, transactionManager)
                .reader(reservationItemReader())
                .processor(reservationItemProcessor())
                .writer(compositeItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<ReservationEntity> reservationItemReader() {
        return new JpaPagingItemReaderBuilder<ReservationEntity>()
                .name("reservationItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT r " +
                             "FROM ReservationEntity r " +
                             "WHERE r.status = 'NEW' " +
                             "ORDER BY r.createdAt ASC")
                .pageSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<ReservationEntity, ReservationEntity> reservationItemProcessor() {
        return reservation -> {
            ProductAvailabilityEntity product = reservation.getProduct();
            // 예약가능인원이 예약인원보다 크면 성공
            if (product != null && product.getQuantityAvailable() - reservation.getQuantity() > 0) {
                product.setQuantityAvailable(product.getQuantityAvailable() - reservation.getQuantity());
                reservation.setStatus("COMPLETE");
            }else{
                reservation.setStatus("FAIL");
            }
            return reservation;
        };
    }

    @Bean
    public ItemWriter<ReservationEntity> compositeItemWriter() {
        CompositeItemWriter<ReservationEntity> writer = new CompositeItemWriter<>();
        writer.setDelegates(Arrays.asList(
                items -> {
                    List<ProductAvailabilityEntity> products = new ArrayList<>();
                    for (ReservationEntity reservation : items) {
                        if (reservation.getProduct() != null) {
                            products.add(reservation.getProduct());
                        }
                    }
                    productItemWriter().write(new Chunk<>(products));
                },
                reservationItemWriter()
        ));
        return writer;
    }

    @Bean
    public JpaItemWriter<ReservationEntity> reservationItemWriter() {
        return new JpaItemWriterBuilder<ReservationEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public JpaItemWriter<ProductAvailabilityEntity> productItemWriter() {
        return new JpaItemWriterBuilder<ProductAvailabilityEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}

