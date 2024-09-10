package com.example.gateway_service.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

/**
 * RouteValidator is responsible for checking if a given route is secured
 * (requires authentication) or open (does not require authentication).
 */
@Component
public class RouteValidator {

  // List of API endpoints that are open and do not require authentication
  public static final List<String> openApiEndpoints = List.of(
      "/auth/register",   // Endpoint for user registration
      "/auth/authenticate", // Endpoint for user authentication (login)
      "/eureka"  // Endpoint for service discovery (if using Eureka)
  );

  /**
   * Predicate to determine if the request route is secured.
   * The route is considered secured if it does not match any of the open API endpoints.
   */
  public Predicate<ServerHttpRequest> isSecured = request ->
      openApiEndpoints
          .stream()
          .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
