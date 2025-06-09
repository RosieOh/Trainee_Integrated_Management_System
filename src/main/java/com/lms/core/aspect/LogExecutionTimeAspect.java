package com.lms.core.aspect;

import com.lms.core.annotation.LogExecutionTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @LogExecutionTime 어노테이션의 동작을 구현하는 AOP Aspect
 * 메소드의 실행 시간을 측정하고 로깅합니다.
 */
@Aspect
@Component
@Slf4j
public class LogExecutionTimeAspect {

    /**
     * @LogExecutionTime 어노테이션이 적용된 메소드의 실행 시간을 측정하고 로깅합니다.
     * 
     * @param joinPoint 실행 중인 메소드의 정보를 담고 있는 JoinPoint
     * @param logExecutionTime @LogExecutionTime 어노테이션 정보
     * @return 원본 메소드의 실행 결과
     * @throws Throwable 메소드 실행 중 발생한 예외
     */
    @Around("@annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        // 메소드 실행 시작 시간 기록
        long startTime = System.currentTimeMillis();
        
        // 원본 메소드 실행
        Object result = joinPoint.proceed();
        
        // 메소드 실행 종료 시간 기록
        long endTime = System.currentTimeMillis();
        
        // 로깅을 위한 메소드 정보 수집
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        // 로그 메시지 생성
        // value가 지정된 경우 해당 메시지를 사용하고, 그렇지 않은 경우 메소드 정보를 포함한 메시지 생성
        String message = logExecutionTime.value().isEmpty() 
            ? String.format("%s.%s executed in %dms", className, methodName, (endTime - startTime))
            : String.format("%s executed in %dms", logExecutionTime.value(), (endTime - startTime));
            
        // 실행 시간 로깅
        log.info(message);
        
        return result;
    }
} 