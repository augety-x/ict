package com.ftt.aspect;

import com.ftt.annotation.AutoFill;
import com.ftt.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.ftt.mapper.*.*(..))&&@annotation(com.ftt.annotation.AutoFill)")
    public  void autoFillPointCut(){
    }
    //前置通知，在通知中
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("公共字段自动填充");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();
        Object[] args = joinPoint.getArgs();
        Object entity = args[0];
        //准备数据
        LocalDateTime cur = LocalDateTime.now();

        if (operationType.equals(OperationType.INSERT)){
            try {
                entity.getClass().getDeclaredMethod("setCreatedAt", LocalDateTime.class).invoke(entity,cur);
                entity.getClass().getDeclaredMethod("setUpdatedAt", LocalDateTime.class).invoke(entity,cur);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
