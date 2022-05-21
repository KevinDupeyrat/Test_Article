package com.test.article.exemple.purchase.domain.purchase.service;

import com.test.article.exemple.purchase.domain.purchase.model.Product;

public interface StoreService {

    void decreaseProduct(final Product product, final int amount);

    boolean storeIsEnought(final Product product, final int amount);

    int getStock(final Product product);
}
