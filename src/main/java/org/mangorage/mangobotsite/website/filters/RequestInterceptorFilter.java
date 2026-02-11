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
import org.mangorage.bootstrap.api.logging.IDeferredMangoLogger;
import org.mangorage.bootstrap.api.logging.ILoggerFactory;

import java.io.IOException;


@WebFilter("/*") // Intercept all incoming requests
public final class RequestInterceptorFilter implements Filter {

    private static final IDeferredMangoLogger LOGGER = ILoggerFactory.getDefault().getWrappedProvider("slf4j", RequestInterceptorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {

        if (request instanceof Request main) {
            var ip = main.getHeader("X-Forwarded-For");
            LOGGER.get().info("Intercepted Request from %s for %s -> https://mangobot.mangorage.org%s".formatted(ip == null ? request.getRemoteAddr() : ip, main.getMethod(), main.getOriginalURI()));

        } else if (request instanceof HttpServletRequest http) {
            var ip = http.getHeader("X-Forwarded-For");
            LOGGER.get().info("Unknown Type (Class) From %s -> %s".formatted(ip == null ? http.getRemoteAddr() : ip, request.getClass()));
        } else {
            LOGGER.get().info("Unknown Type (Class) From %s -> %s".formatted(request.getRemoteAddr(), request.getClass()));
        }

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            if (response instanceof HttpServletResponse r)
                r.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        e.getLocalizedMessage()
                );
        }
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}
