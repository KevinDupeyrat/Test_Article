package com.test.article.exemple.purchase.domain.customer.service;

import com.test.article.exemple.purchase.domain.customer.error.InvalidCustomerException;
import com.test.article.exemple.purchase.domain.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final List<Customer> customers;

    public CustomerServiceImpl() {
        customers = new ArrayList<>();
    }

    @Override
    public Customer createAccount(final String name, final String email) {
        final var customer = new Customer(customers.size() + 1, name, email);
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer login(final String name, final String email) {
        return customers.stream()
                .filter(customer -> customer.name().equals(name)
                        && customer.email().equals(email))
                .findFirst()
                .orElseThrow(() -> new InvalidCustomerException("Customer not found"));
    }

    @Override
    public Customer getCustomer(final int customerId) {
        return customers.stream()
                .filter(customer -> customer.id() == customerId)
                .findFirst()
                .orElseThrow(() -> new InvalidCustomerException("Customer not found"));

    }

    @Override
    public boolean customerExist(final int customerId) {
        return customers.stream()
                .anyMatch(customer -> customer.id() == customerId);
    }
}
