package com.flab.tour;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class ProjectApplication {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job updateReservationJob;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Scheduled(cron = "0 0 * * * ?") // 매 시간마다 실행
	public void performBatchJob() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters();
		jobLauncher.run(updateReservationJob, jobParameters);
	}

}
