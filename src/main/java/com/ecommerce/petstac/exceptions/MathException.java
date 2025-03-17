package com.ecommerce.petstac.exceptions;

public class MathException extends RuntimeException {
    public MathException(String message) {
        super(message + " is greater than 0.");
    }
}
