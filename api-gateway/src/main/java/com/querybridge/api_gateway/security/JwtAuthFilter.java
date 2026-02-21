package com.querybridge.api_gateway.security;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        
        // Open endpoints that don't need auth
        if (exchange.getRequest().getMethod().matches("OPTIONS") ||
            path.startsWith("/auth-service/auth/") || 
            path.startsWith("/auth/") || 
            path.equals("/auth-service/auth/login") || 
            path.equals("/auth-service/auth/register") || 
            path.startsWith("/eureka")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
             exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
             return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        try {
            // Validate and extract
            String email = JwtUtil.extractEmail(token);
            String role = JwtUtil.extractRole(token);
            // Ideally also check expiration inside extractEmail or separate method

            // Mutate request to add user info
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(r -> r.header("X-User-Email", email)
                                   .header("X-User-Role", role))
                    .build();
            
            return chain.filter(mutatedExchange);

        } catch (Exception e) {
            System.err.println("JWT Validation Failed: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
