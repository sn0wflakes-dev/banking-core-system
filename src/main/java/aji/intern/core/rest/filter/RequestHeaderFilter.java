package aji.intern.core.rest.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestHeaderFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean isSoap = request.getRequestURI().startsWith("/ws");
        boolean isNotGetMethod = !HttpMethod.GET.matches(request.getMethod());
        return isSoap || isNotGetMethod;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String messageId = request.getHeader("X-Message-ID");

        if (messageId != null) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = "{"
                + "\"status\":400,"
                + "\"error\":\"Bad Request\","
                + "\"message\":\"Invalid header payload\""
                + "}";

        response.getWriter().write(jsonResponse);
    }
}
