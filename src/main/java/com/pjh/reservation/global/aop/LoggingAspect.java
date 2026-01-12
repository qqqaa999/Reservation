package com.pjh.reservation.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.pjh.reservation.service..*(..))")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();

        Object[] args = joinPoint.getArgs();

        log.info("[Service Start] {}.{} args={}", className, methodName, Arrays.toString(args));

        try {

            Object result = joinPoint.proceed();

            log.info("[Service End] {}.{} result={}", className, methodName, result);

            return result;

        } catch (Exception e) {

            log.error("[Service Exception] {}.{} error={}", className, methodName, e.getMessage(), e);
            throw e;

        } finally {

            long duration = System.currentTimeMillis() - startTime;

            log.info("[Service Time] {}.{} took {} ms", className, methodName, duration);
        }

    }
}
