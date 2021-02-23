package teste.servicepack.middlewareaspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by jorgemachado on 18/10/18.
 */
@Aspect
public class ServiceEngine
{
    //Defines a pointcut that we can use in the @Before,@After, @AfterThrowing, @AfterReturning,@Around specifications
    //The pointcut will look for the @YourAnnotation
    @Pointcut("@annotation(ServiceAnnotation)")
    public void serviceAnnotationPointCutDefinition(){}

    //Defines a pointcut that we can use in the @Before,@After, @AfterThrowing, @AfterReturning,@Around specifications
    //The pointcut is a catch all pointcut with the scope of execution
    @Pointcut("execution(* *(..))")
    public void atExecution(){}

    //A junção de vários point cuts num só criando um conjunto de condições lógicas
    @Pointcut("within(servicepack.Servico+) && atExecution() && serviceAnnotationPointCutDefinition()")
    public void chamadaServico() {}

    @Around("chamadaServico()")
    public Object envolventeServico(ProceedingJoinPoint pjp) throws Throwable
    {
        //Sugestão: um motor de serviços poderia neste ponto abrir a transação na base de dados
        System.out.println("Iniciando chamada do servico");
        try {
            Object returnObj = pjp.proceed();
            //Sugestão: um motor de serviços poderia neste ponto fazer commit da transação na base de dados
            System.out.println("Terminado chamada do servico");
            return returnObj;
        } catch (Exception e) {
            //Sugestão: um motor de serviços poderia neste ponto fazer rollback da transação na base de dados
            System.out.println("Excepcao no Servico");
        }
        return 0;
    }
}
