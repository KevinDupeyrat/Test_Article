package com.test.article.exemple.purchase.domain.purchase.service;

import com.test.article.exemple.purchase.domain.purchase.model.Product;

public interface PurchaseService {

    boolean purchase(final Product product, final int amount, final int customerId);
}
