package com.example.joy.servlet.login;

import com.okta.authn.sdk.resource.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserProfile", urlPatterns = {"/profile"})
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the currently logged in user
        User user = (User) request.getSession(true)
            .getAttribute(OktaFilter.USER_SESSION_KEY);

        // add the user to the request context and render the JSP
        request.setAttribute("user", user);
        // This has also been added the example JSPs directly using:
        // <c:set var="user" value="${sessionScope.get('com.okta.authn.sdk.resource.User')}"/>

        request.getRequestDispatcher("/WEB-INF/jsp/user-profile.jsp")
            .forward(request, response);
    }
}
