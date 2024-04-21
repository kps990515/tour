package com.flab.tour.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper(){
        var objectMapper = new ObjectMapper();

        // registerModule : objectMapper에 적용할 범위 세팅
        objectMapper.registerModule(new Jdk8Module()); // java8버전 이후 클래스
        objectMapper.registerModule(new JavaTimeModule()); // local date


        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 모르는 json field는 무시
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 모르는 객체 컬럼은 무시
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 날짜 관련 직렬화
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy()); //스네이크 케이스

        return objectMapper;
    }
}
