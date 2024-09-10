package com.example.gateway_service.filter;

import com.example.gateway_service.exception.UnauthorizedException;
import com.example.gateway_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * AuthenticationFilter class is used to filter and validate incoming requests
 * based on their authorization tokens before they are routed to downstream services.
 */
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

  // Autowiring the RouteValidator to check if a route requires authentication
  @Autowired
  private RouteValidator validator;

  // Autowiring the JwtUtil to handle token validation
  @Autowired
  private JwtUtil jwtUtil;

  /**
   * Constructor to initialize the filter with the Config class.
   */
  public AuthenticationFilter() {
    super(Config.class);
  }

  /**
   * This method applies the filter logic.
   * It checks if the request requires authentication, and if so,
   * validates the JWT token present in the Authorization header.
   *
   * @param config Configuration settings for the filter (if any).
   * @return GatewayFilter that processes the request and validates JWT token if required.
   */
  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      // Check if the request is for a secured route
      if (validator.isSecured.test(exchange.getRequest())) {
        // Check if the Authorization header is present
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
          // If no Authorization header is found, throw an UnauthorizedException
          throw new UnauthorizedException();
        }

        // Extract the token from the Authorization header
        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

        // If the token starts with "Bearer ", remove the prefix to get the actual token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          authHeader = authHeader.substring(7);
        }

        try {
          // Validate the JWT token using JwtUtil
          jwtUtil.validateToken(authHeader);
        } catch (Exception e) {
          // If token validation fails, throw an UnauthorizedException
          throw new UnauthorizedException();
        }
      }
      // Continue with the request processing chain if authentication succeeds
      return chain.filter(exchange);
    });
  }

  /**
   * Configuration class for the filter. Can be used to pass additional settings if needed.
   */
  public static class Config {
    // No custom configuration required for this filter at this time
  }
}
