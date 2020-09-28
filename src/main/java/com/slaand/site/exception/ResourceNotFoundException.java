package com.slaand.site.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {

    public ResourceNotFoundException(final HttpStatus status, final String reason) {
        super(status, reason);
    }
}
