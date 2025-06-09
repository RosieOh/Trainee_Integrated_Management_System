package com.lms.core.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 스케줄러 예제 클래스
 * 
 * 주요 기능:
 * - 일일 작업 스케줄링
 * - 시간별 작업 스케줄링
 * - 주기적 작업 스케줄링
 * - 지연 작업 스케줄링
 * 
 * @author core
 */
@Component
public class ExampleScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(ExampleScheduler.class);

    /**
     * 매일 자정에 실행되는 작업
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyTask() {
        logger.info("Executing daily task");
        // 일일 작업 수행
    }

    /**
     * 매 시간마다 실행되는 작업
     */
    @Scheduled(fixedRate = 3600000)
    public void hourlyTask() {
        logger.info("Executing hourly task");
        // 시간별 작업 수행
    }

    /**
     * 5분마다 실행되는 작업
     */
    @Scheduled(fixedDelay = 300000)
    public void periodicTask() {
        logger.info("Executing periodic task");
        // 주기적 작업 수행
    }

    /**
     * 초기 지연 1분 후 10분마다 실행되는 작업
     */
    @Scheduled(initialDelay = 60000, fixedRate = 600000)
    public void delayedTask() {
        logger.info("Executing delayed task");
        // 지연 작업 수행
    }
} 