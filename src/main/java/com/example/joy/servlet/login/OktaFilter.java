package com.example.joy.servlet.login;

import com.okta.authn.sdk.resource.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OktaFilter implements Filter {

    static final String USER_SESSION_KEY = User.class.getName();

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = request.getServletPath();

        // allow anonymous access to static resources and anything under /authn/ and the root index.jsp
        if (isStaticAsset(request) || path.startsWith("/authn/") ) {
            chain.doFilter(request, response);
            return;
        }

        // check if we have a current user in the session
        if (isAuthenticated(request)) {
            chain.doFilter(request, response);
            return;
        }

        // no authenticated user found in session
        // redirect to /authn/login
        response.sendRedirect("/authn/login");
    }

    public void destroy() {}

    private boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession(false) != null 
            && request.getSession().getAttribute(USER_SESSION_KEY) != null;    
    }

    private boolean isStaticAsset(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/static/") || path.equals("/favicon.ico");
    }
}
