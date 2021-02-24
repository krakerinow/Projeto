package teste.web;

import teste.servicepack.pluginSession.PluginFactory;
import teste.servicepack.pluginSession.RegisterSession;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        openSession(servletRequest, filterChain, response);
    }

    private void openSession(ServletRequest servletRequest, FilterChain filterChain, HttpServletResponse response) throws IOException, ServletException {
        RegisterSession plugin = null;
        try {
            plugin = (RegisterSession) PluginFactory.getPlugin(RegisterSession.class);
            plugin.openSession(servletRequest, filterChain,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {

    }

}
