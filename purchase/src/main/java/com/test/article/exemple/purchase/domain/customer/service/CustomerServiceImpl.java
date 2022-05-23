package com.test.article.exemple.purchase.domain.customer.service;

import com.test.article.exemple.purchase.domain.customer.error.InvalidCustomerException;
import com.test.article.exemple.purchase.domain.customer.model.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {

    private final Map<Customer, Boolean> customers;

    public CustomerServiceImpl() {
        customers = new HashMap<>();
    }

    @Override
    public Customer createAccount(final String name, final String email) {
        final var customer = new Customer(customers.size() + 1, name, email);
        customers.put(customer, false);
        return customer;
    }

    @Override
    public Customer login(final String name, final String email) {
        final Map.Entry<Customer, Boolean> customerEntry = customers.entrySet().stream()
                .filter(entry -> entry.getKey().name().equals(name)
                        && entry.getKey().email().equals(email))
                .findFirst()
                .orElseThrow(() -> new InvalidCustomerException("Customer not found"));
        customers.put(customerEntry.getKey(), true);
        return customerEntry.getKey();

    }

    @Override
    public boolean logout(final int customerId) {
        if (!customerIsLogged(customerId)) {
            return false;
        }
        final Customer customer = getCustomer(customerId);
        customers.put(customer, false);
        return true;
    }

    @Override
    public Customer getCustomer(final int customerId) {
        final Map.Entry<Customer, Boolean> customerEntry = customers.entrySet().stream()
                .filter(entry -> entry.getKey().id() == customerId)
                .findFirst()
                .orElseThrow(() -> new InvalidCustomerException("Customer not found"));
        return customerEntry.getKey();

    }

    @Override
    public boolean customerExist(final int customerId) {
        return customers.keySet().stream()
                .anyMatch(customer -> customer.id() == customerId);
    }

    @Override
    public boolean customerIsLogged(final int customerId) {
        final Customer customer = getCustomer(customerId);
        return Boolean.TRUE.equals(customers.get(customer));
    }
}
