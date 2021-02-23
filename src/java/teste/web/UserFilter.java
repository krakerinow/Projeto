package teste.web;

import org.apache.log4j.Logger;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserFilter implements javax.servlet.Filter {


    private static final Logger logger = Logger.getLogger(UserFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(javax.servlet.ServletRequest req,
                         javax.servlet.ServletResponse resp,
                         javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException
    {
        logger.info("Entrou no filtro");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

//        response.sendError(404);

        if(request.getSession().getAttribute("user") == null)
        {
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
        else {
            logger.debug("Passagem Permitida ao artista: " + ((HttpServletRequest) req).getSession().getAttribute("user"));
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }


}
