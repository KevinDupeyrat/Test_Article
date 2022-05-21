package com.test.article.exemple.purchase.domain.purchase.model;


import java.util.Objects;

public record History(int customerId, Product product, int amount) {

    public static final String COLON_SYMBOL = ":";
    public static final String EQUAL_SYMBOL = "=";

    public static History fromString(final String line) {
        String[] lineSplited = line.split(COLON_SYMBOL);
        String[] lineSplited2 = lineSplited[1].split(EQUAL_SYMBOL);

        return new History(Integer.parseInt(lineSplited[0].trim()), Product.valueOf(lineSplited2[0].trim()),
                Integer.parseInt(lineSplited2[1].trim()));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof History history)) {
            return false;
        }
        return history.customerId == this.customerId &&
                history.product == this.product &&
                history.amount == this.amount;
    }

    @Override
    public String toString() {
        return customerId + COLON_SYMBOL + product + EQUAL_SYMBOL + amount;
    }
}
