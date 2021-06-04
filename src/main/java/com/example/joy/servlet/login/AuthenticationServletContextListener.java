package com.example.joy.servlet.login;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.okta.authn.sdk.client.AuthenticationClient;
import com.okta.authn.sdk.client.AuthenticationClients;
import com.example.joy.servlet.login.AuthenticationLambdaServlet.AuthenticationServletHandler;

import java.util.EnumSet;

@WebListener
public class AuthenticationServletContextListener implements ServletContextListener {
 
    private AuthenticationActions actions;

    public void contextInitialized(ServletContextEvent sce) {

        // configuration can be pulled from various sources, see https://github.com/okta/okta-auth-java#configuration-reference
        AuthenticationClient authenticationClient = AuthenticationClients.builder()
            .setOrgUrl(sce.getServletContext().getInitParameter("orgUrl"))
            .build();

        actions = new AuthenticationActions(authenticationClient);

        ServletContext servletContext = sce.getServletContext();
        registerFilter(servletContext, "/*", new OktaFilter());

        registerAction(servletContext, 
                       "/authn/login", 
                       "/WEB-INF/jsp/authn/login.jsp", 
                       (request,response) -> actions.login(request, response));
        registerAction(servletContext, 
                       "/authn/logout", 
                       null, 
                       (request,response) -> actions.logout(request, response));
    }
    
    public void contextDestroyed(ServletContextEvent sce) {
        actions = null;
    }

    private void registerAction(ServletContext servletContext, String path, 
                                String view, AuthenticationServletHandler postHandler) {
        servletContext.addServlet(path, new AuthenticationLambdaServlet(view, postHandler))
                      .addMapping(path);
    }

    private void registerFilter(ServletContext servletContext, String path, Filter filter) {
        servletContext.addFilter(filter.getClass().getName(), filter)
            .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, path);
    }
}