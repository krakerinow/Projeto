package teste.servicepack.middlewareaspect2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by jorgemachado on 18/10/18.
 */
@Aspect
public class ServiceEngine2
{
    @Pointcut("@annotation(serviceAnnotationWithParam)")
    public void pointCutAnotationWithParam(ServiceAnnotationWithParam serviceAnnotationWithParam){}

    @Pointcut("@annotation(serviceAnnotationWithParam2)")
    public void pointCutAnotationWithParam2(ServiceAnnotationWithParam2 serviceAnnotationWithParam2){}

    //Defines a pointcut that we can use in the @Before,@After, @AfterThrowing, @AfterReturning,@Around specifications
    //The pointcut is a catch all pointcut with the scope of execution
    @Pointcut("execution(* *(..))")
    public void atExecution2(){}


    @Around("pointCutAnotationWithParam(serviceAnnotationWithParam) && atExecution2()")
    public Object envolventeServicoComParametrosNaAnotacao(ProceedingJoinPoint pjp,ServiceAnnotationWithParam serviceAnnotationWithParam) throws Throwable
    {
        //Sugestão: um motor de serviços poderia neste ponto abrir a transação na base de dados
        System.out.println("Iniciando chamada do servico com paramtro de anotacao:" + serviceAnnotationWithParam.role());
        try {
            Object returnObj = pjp.proceed();
            //Sugestão: um motor de serviços poderia neste ponto fazer commit da transação na base de dados
            System.out.println("Terminando chamada do servico com paramtro de anotacao:" + serviceAnnotationWithParam.role());
            return returnObj;
        } catch (Exception e) {
            //Sugestão: um motor de serviços poderia neste ponto fazer rollback da transação na base de dados
            System.out.println("Excepção chamada do servico com paramtro de anotacao:" + serviceAnnotationWithParam.role());
        }
        return 0;
    }

    @Around("pointCutAnotationWithParam2(serviceAnnotationWithParam2) && atExecution2()")
    public Object envolventeServicoComParametrosNaAnotacao2(ProceedingJoinPoint pjp,ServiceAnnotationWithParam2 serviceAnnotationWithParam2) throws Throwable
    {
        //Sugestão: um motor de serviços poderia neste ponto abrir a transação na base de dados
        System.out.println("Iniciando chamada do servico com paramtro de anotacao2:" + serviceAnnotationWithParam2.role2());
        try {
            Object returnObj = pjp.proceed();
            //Sugestão: um motor de serviços poderia neste ponto fazer commit da transação na base de dados
            System.out.println("Terminando chamada do servico com paramtro de anotacao2:" + serviceAnnotationWithParam2.role2());
            return returnObj;
        } catch (Exception e) {
            //Sugestão: um motor de serviços poderia neste ponto fazer rollback da transação na base de dados
            System.out.println("Excepção chamada do servico com paramtro de anotacao2:" + serviceAnnotationWithParam2.role2());
        }
        return 0;
    }
}
