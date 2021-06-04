package com.example.joy.servlet.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.okta.authn.sdk.AuthenticationException;
import com.okta.authn.sdk.client.AuthenticationClient;

class AuthenticationActions {

    private final AuthenticationClient authenticationClient;

    AuthenticationActions(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    void login(HttpServletRequest request, HttpServletResponse response) 
        throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        authenticationClient.authenticate(username, 
            password.toCharArray(), 
            "/", 
            new ExampleAuthenticationStateHandler(request, response));
    }

    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        response.sendRedirect("/authn/login");
    }

    static void forward(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (ServletException | IOException e) {
            throw new IllegalStateException("Unable to forward to path: "+ path, e);
        }
    }
}
