package com.test.article.exemple.purchase.domain.customer.error;

public class InvalidCustomerException extends RuntimeException {

    public InvalidCustomerException(final String message) {
        super(message);
    }
}
