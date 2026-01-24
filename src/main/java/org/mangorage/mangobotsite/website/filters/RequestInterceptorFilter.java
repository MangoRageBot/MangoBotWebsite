package org.mangorage.mangobotsite.website.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.mangorage.mangobotcore.api.util.log.LogHelper;


import java.io.IOException;

import static org.mangorage.mangobotsite.website.util.WebUtil.getOrCreateUserToken;


@WebFilter("/*") // Intercept all incoming requests
public final class RequestInterceptorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println(request.getClass());


        if (request instanceof Request main) {
            var ip = main.getHeader("X-Forwarded-For");
            LogHelper.info("Intercepted Request from %s for %s -> https://mangobot.mangorage.org%s".formatted(ip == null ? request.getRemoteAddr() : ip, main.getMethod(), main.getOriginalURI()));

        } else if (request instanceof HttpServletRequest http) {
            var ip = http.getHeader("X-Forwarded-For");
            LogHelper.info("Unknown Type (Class) From %s -> %s".formatted(ip == null ? http.getRemoteAddr() : ip, request.getClass()));
        } else {
            LogHelper.info("Unknown Type (Class) From %s -> %s".formatted(request.getRemoteAddr(), request.getClass()));
        }

        if (response instanceof HttpServletResponse resp && request instanceof HttpServletRequest req) {
            getOrCreateUserToken(req, resp); // Either Get it or create a new one!
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}
