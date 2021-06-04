package com.example.joy.servlet.login;

import com.okta.authn.sdk.AuthenticationException;
import com.okta.sdk.resource.ResourceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.joy.servlet.login.AuthenticationActions.forward;

class AuthenticationLambdaServlet extends HttpServlet {
    private final AuthenticationServletHandler renderConsumer;
    private final AuthenticationServletHandler postConsumer;

    AuthenticationLambdaServlet(final String path, AuthenticationServletHandler postConsumer) {
        this((request, response) -> forward(path, request, response), postConsumer);
    }

    AuthenticationLambdaServlet(AuthenticationServletHandler renderConsumer,
            AuthenticationServletHandler postConsumer) {
        this.renderConsumer = renderConsumer;
        this.postConsumer = postConsumer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (renderConsumer == null) {
            super.doGet(req, resp);
            return;
        }

        try {
            renderConsumer.service(req, resp);
        } catch (AuthenticationException | ResourceException e) {
            req.setAttribute("error", e);
            forward("/WEB-INF/jsp/authn/login.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (postConsumer == null) {
            super.doGet(req, resp);
            return;
        }

        try {
            postConsumer.service(req, resp);
        } catch (ResourceException | AuthenticationException  e) {

            // on error, set the error attribute then render the page again
            req.setAttribute("error", e);
            doGet(req, resp);
        }
    }

    @FunctionalInterface
    interface AuthenticationServletHandler {
        void service(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException, AuthenticationException;
    }
}
