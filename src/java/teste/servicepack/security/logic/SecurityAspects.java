package teste.servicepack.security.logic;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import teste.servicepack.security.SecurityContextProvider;
import teste.servicepack.security.SecuritySessionContext;
import teste.utils.HibernateUtils;
import teste.domain.UserSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
public class SecurityAspects {
    private static final Logger logger = Logger.getLogger(SecurityAspects.class);

    @Pointcut("@annotation(Transaction)")
    public void serviceTransactionPointCut() {}

    @Pointcut("@annotation(injectSession)")
    public void injectSessionPointCut() {}

    @Pointcut("@annotation(hasRole)")
    public void hasRolePointCut(HasRole hasRole) {}

    @Pointcut("@annotation(isAuthenticated)")
    public void isAuthenticatedPointCut() {}

    @Pointcut("@annotation(CriadorPagina)")
    public void criadorPaginaPointcut() {}

    @Pointcut("execution(* *(..))")
    public void executionPointCut() {}

    @Around("serviceTransactionPointCut() && executionPointCut()")
    public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {
        // Executa antes
        HibernateUtils.getCurrentSession().beginTransaction();
        logger.info("Iniciando chamada do servico:" + pjp.getSignature().getName() +
                " na classe " + pjp.getSourceLocation().getClass().getName());
        try {
            // Executa depois
            Object returnObj = pjp.proceed();
            HibernateUtils.getCurrentSession().getTransaction().commit();
            return returnObj;
        } catch(Exception e) {
            HibernateUtils.getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }

    @Around("injectSessionPointCut() && executionPointCut()")
    public Object injectSessionAdvise(ProceedingJoinPoint pjp) throws Throwable {
        SecuritySessionContext securitySessionContext = SecurityContextProvider.getInstance().getSecuritySessionContext();
        UserSession session = (UserSession) HibernateUtils.getCurrentSession().load(UserSession.class, securitySessionContext.getRequester());
        final Object[] args = pjp.getArgs();
        List<Object> objectList = new ArrayList<>(Arrays.asList(args));

        objectList.add(args.length - 1, session);
        return pjp.proceed(objectList.toArray());
    }

    @Around("isAuthenticatedPointCut() && executionPointCut()")
    public Object isAuthenticatedAdvise(ProceedingJoinPoint pjp) throws Throwable
    {
        logger.info("Is Authenticated Aspect");
        String cookie = SecurityContextProvider.getInstance().getSecuritySessionContext().getRequester();
        UserSession session = (UserSession) HibernateUtils.getCurrentSession().load(UserSession.class,cookie);

        if(session.getUser() != null)
            return pjp.proceed();

        throw new NotAuthenticatedException();
    }

    @Around("hasRolePointCut(hasRole) && executionPointCut()")
    public Object hasRoleAdvise(ProceedingJoinPoint pjp, HasRole hasRole) throws Throwable {
        logger.info("Has Role Aspect");
        String cookie = SecurityContextProvider.getInstance().getSecuritySessionContext().getRequester();
        UserSession session = (UserSession) HibernateUtils.getCurrentSession().load(UserSession.class,cookie);

        String[] rolesIn = hasRole.role().split(",");
        String[] roles = session.getUser().getRoles().split(",");
        for(String checkRole: rolesIn) {
            if(Arrays.asList(roles).contains(checkRole)) {
                return pjp.proceed();
            }
        }

        throw new FailRoleException();
    }

}
