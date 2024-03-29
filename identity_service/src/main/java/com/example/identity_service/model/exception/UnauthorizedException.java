package com.example.identity_service.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED,reason = "Wrong password or username")
public class UnauthorizedException extends RuntimeException {
}
