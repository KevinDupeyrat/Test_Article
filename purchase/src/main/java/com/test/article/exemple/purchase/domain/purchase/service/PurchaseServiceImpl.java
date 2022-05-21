package com.test.article.exemple.purchase.domain.purchase.service;

import com.test.article.exemple.purchase.domain.customer.error.InvalidCustomerException;
import com.test.article.exemple.purchase.domain.customer.service.CustomerService;
import com.test.article.exemple.purchase.domain.purchase.model.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final StoreService storeService;

    private final CustomerService customerService;

    private final ShoppingHistoryService shoppingHistoryService;

    @Override
    public boolean purchase(Product product, int amount, int customerId) {
        if (!customerService.customerExist(customerId)) {
            throw new InvalidCustomerException("Customer not found");
        }
        if (!storeService.storeIsEnought(product, amount)) {
            return false;
        }
        shoppingHistoryService.saveToHistory(customerId, product, amount);
        storeService.decreaseProduct(product, amount);
        return true;
    }
}
