package aji.intern.core.rest.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MessageIdFilter extends OncePerRequestFilter {
    private static final String messageIdCtx = "messageId";

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        boolean isSoap = request.getRequestURI().startsWith("/ws");
        boolean isGetMethod = HttpMethod.GET.matches(request.getMethod());
        return isSoap || isGetMethod;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } finally {
            ThreadContext.remove(messageIdCtx);
        }
    }
}
