package com.test.article.exemple.purchase.domain.customer.error;

public class CustomerNotLoggedException extends RuntimeException {

    public CustomerNotLoggedException(final String message) {
        super(message);
    }
}
