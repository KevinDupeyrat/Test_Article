package com.test.article.exemple.purchase.domain.customer.service;

import com.test.article.exemple.purchase.domain.customer.model.Customer;

public interface CustomerService {

    Customer createAccount(final String name, final String email);

    Customer login(final String name, final String email);

    boolean logout(final int customerId);

    Customer getCustomer(final int customerId);

    boolean customerExist(final int customerId);

    boolean customerIsLogged(final int customerId);
}
