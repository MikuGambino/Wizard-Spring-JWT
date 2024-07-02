package com.university.wizard.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    private final String resourceName;
    private final String resourceValue;

    public ResourceAlreadyExistsException(String resourceName, String resourceValue) {
        super();
        this.resourceName = resourceName;
        this.resourceValue = resourceValue;
    }

    public String getMessage() {
        return String.format("%s '%s' already exists", resourceName, resourceValue);
    }
}
