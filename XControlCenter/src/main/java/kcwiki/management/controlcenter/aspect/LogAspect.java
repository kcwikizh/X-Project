package kcwiki.management.controlcenter.aspect;

import java.io.PrintStream;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.iharu.aspect.AspectInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect
  implements AspectInterface
{
  private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
  
  @Pointcut("execution(* kcwiki.msgtransfer.websocket..*.*(..))or execution(* kcwiki.msgtransfer.initializer.*.*(..))or execution(* kcwiki.msgtransfer.core..*.*(..))or execution(* kcwiki.msgtransfer.database..*.*(..))or execution(* kcwiki.msgtransfer.httpclient..*.*(..))or execution(* kcwiki.msgtransfer.web..*.*(..))")
  public void pointCut() {}
  
  public void doBefore(JoinPoint joinPoint)
  {
    System.out.println("AOP Before Advice...");
  }
  
  public void doAfter(JoinPoint joinPoint)
  {
    System.out.println("AOP After Advice...");
  }
  
  public void afterReturn(JoinPoint joinPoint, Object returnVal)
  {
    System.out.println("AOP AfterReturning Advice:" + returnVal);
  }
  
  @AfterThrowing(pointcut="pointCut()", throwing="error")
  public void afterThrowing(JoinPoint joinPoint, Throwable error)
  {
    LOG.error("[Aspect-LogActions] Method Signature:{}", joinPoint.getSignature());
    LOG.error("[Aspect-LogActions] Exception:{}", ExceptionUtils.getStackTrace(error));
  }
  
  public Object around(ProceedingJoinPoint pjp)
  {
    System.out.println("AOP Aronud before...");
    Object result = null;
    try
    {
      result = pjp.proceed();
    }
    catch (Throwable e)
    {
      LOG.error("[Aspect-LogActions] Method Signature:{}", pjp.getSignature());
      LOG.error("[Aspect-LogActions] Exception:{}", ExceptionUtils.getStackTrace(e));
    }
    System.out.println("AOP Aronud after...");
    return result;
  }
}
