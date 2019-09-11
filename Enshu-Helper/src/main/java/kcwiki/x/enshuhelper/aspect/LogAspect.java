/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.aspect;

import kcwiki.x.enshuhelper.database.service.LogService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.type.MsgType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 * https://www.cnblogs.com/liuruowang/p/5711563.html
 * 
 */
@Component
@Aspect
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    
    @Autowired //自动注入  
    private LogService logService; 
    
    @Pointcut("execution(* kcwiki.x.enshuhelper.test..*.*(..))"
            + "or execution(* kcwiki.x.enshuhelper.initializer.*.*(..))"
            + "or execution(* kcwiki.x.enshuhelper.core..*.*(..))"
            + "or execution(* kcwiki.x.enshuhelper.database..*.*(..))"
            + "or execution(* kcwiki.x.enshuhelper.httpclient..*.*(..))"
            + "or execution(* kcwiki.x.enshuhelper.message..*.*(..))"
            + "or execution(* kcwiki.x.enshuhelper.web..*.*(..))")
    public void pointCut(){}
    
//    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("AOP Before Advice...");
    }
    
//    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("AOP After Advice...");
    }
    
//    @AfterReturning(pointcut="pointCut()",returning="returnVal")
    public void afterReturn(JoinPoint joinPoint,Object returnVal){
        System.out.println("AOP AfterReturning Advice:" + returnVal);
    }
    
    @AfterThrowing(pointcut="pointCut()", throwing="error")
    public void afterThrowing(JoinPoint joinPoint,Throwable error){
        LOG.error("[Aspect-LogActions] Method Signature:{}", joinPoint.getSignature());
        LOG.error("[Aspect-LogActions] Exception:{}", ExceptionUtils.getStackTrace(error));
        logService.addLog(MsgType.ERROR, joinPoint.getSignature().toLongString(), error);
    }
    
//    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp){
        System.out.println("AOP Aronud before...");
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            LOG.error("[Aspect-LogActions] Method Signature:{}", pjp.getSignature());
            LOG.error("[Aspect-LogActions] Exception:{}", ExceptionUtils.getStackTrace(e));
        }
        System.out.println("AOP Aronud after...");
        return result;
    }
}
