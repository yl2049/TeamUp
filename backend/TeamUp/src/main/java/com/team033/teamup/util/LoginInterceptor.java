package com.team033.teamup.util;

import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/*
    Handles login status
 */
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // whitelist pages that do not require logging in
        if ("/user/login".equals(request.getRequestURI())
            || "/user/register".equals(request.getRequestURI())
            || "/user/sendVerification".equals(request.getRequestURI())) {
            return true;
        }

        // retrieve JWT from header and parse JWT
        Claims claims = JwtUtil.parse(request.getHeader("Authorization"));
        // logged in
        if (claims != null) {
            return true;
        }

        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();

        out.write(request.getRequestURI() + "\nPlease log in!");
        out.flush();
        out.close();
        return false;
    }
}
