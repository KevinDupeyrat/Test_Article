package com.test.article.exemple.purchase.domain.purchase.error;

public class ShoppingHistoryException extends RuntimeException{

    public ShoppingHistoryException(final Exception exception) {
        super(exception);
    }
}
