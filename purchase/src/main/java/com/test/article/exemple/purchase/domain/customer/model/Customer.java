package com.test.article.exemple.purchase.domain.customer.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public record Customer(int id, String name, String email) {

    public Customer {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Invalide name");
        }
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Invalide email");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalide email");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer customer)) {
            return false;
        }
        return customer.id == this.id &&
                customer.name.equals(this.name) &&
                customer.email.equals(this.email);
    }
}
