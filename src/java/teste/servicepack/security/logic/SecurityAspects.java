package teste.servicepack.security.logic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import teste.servicepack.middlewareaspect2.ServiceAnnotationWithParam;
import teste.servicepack.middlewareaspect2.ServiceAnnotationWithParam2;
import teste.servicepack.security.SecurityContextProvider;

/**
 * Created by jorgemachado on 18/10/18.
 */
@Aspect
public class SecurityAspects
{
    @Pointcut("@annotation(IsAuthenticated)")
    public void isAuthenticatedPointCut(){}

    @Pointcut("@annotation(hasRole)")
    public void hasRolePointCut(HasRole hasRole){}

    @Pointcut("@annotation(printParameterExample)")
    public void printParameterExamplePointCut(PrintParameterExample printParameterExample){}

    @Pointcut("execution(* *(..))")
    public void executionPointCut(){}


    @Around("isAuthenticatedPointCut() && executionPointCut()")
    public Object isAuthenticatedAdvise(ProceedingJoinPoint pjp) throws Throwable
    {
        String cookie = SecurityContextProvider.getInstance().getSecuritySessionContext().getRequester();

        //Com o cookie ir buscar a sessao e seguidamente o User e ver se esta logado
        if(true)
            return pjp.proceed();
        throw new NotAuthenticatedException("Access Denied, not authenticated at " + pjp.getSourceLocation().getFileName() + " " + pjp.getSourceLocation().getLine() + " service: " + pjp.getSignature().getName());
    }

    @Around("hasRolePointCut(hasRole) && executionPointCut()")
    public Object hasRoleAdvise(ProceedingJoinPoint pjp,HasRole hasRole) throws Throwable
    {
        //String username = SecurityContextProvider.getInstance().getSecuritySessionContext().getUsername();
        String cookie = SecurityContextProvider.getInstance().getSecuritySessionContext().getRequester();
        //verificar se o username é um user com o role que está em hasRole.role()
        boolean pass = false;
        if(pass)
           return pjp.proceed();
        throw new FailRoleException("Access Denied, does not have role " + hasRole.role() + " at " + pjp.getSourceLocation().getFileName() + " " + pjp.getSourceLocation().getLine() + " service: " + pjp.getSignature().getName());
    }

    @Around("printParameterExamplePointCut(printParameterExample) && executionPointCut()")
    public Object printParameterExampleAdvise(ProceedingJoinPoint pjp,PrintParameterExample printParameterExample) throws Throwable
    {
        System.out.println("PARAMETRO: " + JointPointUtils.getParameter(pjp,printParameterExample.paramName()));
        return pjp.proceed();
    }
}
