package teste.servicepack.security.logic;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import teste.domain.UserSession;
import teste.servicepack.security.SecurityContextProvider;
import teste.servicepack.security.SecuritySessionContext;
import teste.utils.HibernateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
public class SecurityAspects {
    private static final Logger logger = Logger.getLogger(SecurityAspects.class);

    @Pointcut("@annotation(Transaction)")
    public void serviceTransactionPointCut() {}

    @Pointcut("@annotation(AtributeSession)")
    public void AtributeSessionPointCut() {}

    @Pointcut("@annotation(HasRole)")
    public void HasRolePointCut(HasRole HasRole) {}

    @Pointcut("@annotation(IsAuthenticated)")
    public void IsAuthenticatedPointCut() {}

    @Pointcut("@annotation(pageCreator)")
    public void pageCreator() {}

    @Pointcut("execution(* *(..))")
    public void executionPointCut() {}

    @Around("serviceTransactionPointCut() && executionPointCut()")
    public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {
        // Executa antes
        HibernateUtils.getCurrentSession().beginTransaction();
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

    @Around("AtributeSessionPointCut() && executionPointCut()")
    public Object AtributeSessionAdvise(ProceedingJoinPoint pjp) throws Throwable {
        SecuritySessionContext securitySessionContext = SecurityContextProvider.getInstance().getSecuritySessionContext();
        UserSession session = (UserSession) HibernateUtils.getCurrentSession().load(UserSession.class, securitySessionContext.getRequester());
        final Object[] args = pjp.getArgs();
        List<Object> objectList = new ArrayList<>(Arrays.asList(args));

        objectList.add(args.length - 1, session);
        return pjp.proceed(objectList.toArray());
    }

    @Around("IsAuthenticatedPointCut() && executionPointCut()")
    public Object IsAuthenticatedAdvise(ProceedingJoinPoint pjp) throws Throwable
    {
        logger.info("Is Authenticated Aspect");
        String cookie = SecurityContextProvider.getInstance().getSecuritySessionContext().getRequester();
        UserSession session = (UserSession) HibernateUtils.getCurrentSession().load(UserSession.class,cookie);

        if(session.getUser() != null)
            return pjp.proceed();

        throw new NotAuthenticatedException();
    }

    @Around("HasRolePointCut(hasRole) && executionPointCut()")
    public Object HasRoleAdvise(ProceedingJoinPoint pjp, HasRole hasRole) throws Throwable {
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