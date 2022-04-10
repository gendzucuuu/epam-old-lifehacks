package by.epam.java.training.lifehacks.weblayer.filter;

import by.epam.java.training.lifehacks.model.entityenum.UserRole;
import by.epam.java.training.lifehacks.util.constant.Constant;
import by.epam.java.training.lifehacks.util.constant.PagePath;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter( urlPatterns = { "/pages/admin/*" },
        initParams = { @WebInitParam(name = Constant.INDEX, value = PagePath.START_PAGE_FOR_FILTER) })
public class AdminSecurityFilter implements Filter{
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) {
        indexPath = filterConfig.getInitParameter(Constant.INDEX);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession httpSession = httpRequest.getSession(false);
        if (!httpSession.getAttribute(Constant.ROLE).equals(UserRole.ADMIN)) {
            httpResponse.sendRedirect(indexPath);
        }
        httpRequest.setAttribute(Constant.SECURITY_REDIRECT, "true");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
