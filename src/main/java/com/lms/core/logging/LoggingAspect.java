package com.lms.core.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * AOP 기반 로깅 처리를 위한 Aspect 클래스
 * 
 * 주요 기능:
 * - 메소드 실행 시간 측정
 * - 메소드 진입/종료 로깅
 * - 예외 발생 시 로깅
 * 
 * @author core
 */
@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 모든 메소드에 대한 포인트컷을 정의합니다.
     * com.core 패키지 하위의 모든 메소드가 대상입니다.
     */
    @Pointcut("execution(* com.core..*.*(..))")
    public void allMethods() {}

    /**
     * 메소드 실행 전후에 로깅을 수행합니다.
     * - 메소드 진입 시 로깅
     * - 실행 시간 측정
     * - 메소드 종료 시 로깅
     * - 예외 발생 시 로깅
     *
     * @param joinPoint 실행 중인 메소드 정보
     * @return 메소드 실행 결과
     * @throws Throwable 메소드 실행 중 발생한 예외
     */
    @Around("allMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("Entering method [{}] of class [{}]", methodName, className);
        
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        try {
            Object result = joinPoint.proceed();
            stopWatch.stop();
            
            logger.info("Method [{}] of class [{}] executed in {} ms", 
                methodName, className, stopWatch.getTotalTimeMillis());
            
            return result;
        } catch (Exception e) {
            logger.error("Exception in method [{}] of class [{}]: {}", 
                methodName, className, e.getMessage());
            throw e;
        }
    }
} 