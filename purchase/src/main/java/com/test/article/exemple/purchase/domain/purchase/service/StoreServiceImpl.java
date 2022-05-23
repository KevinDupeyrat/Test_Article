package com.test.article.exemple.purchase.domain.purchase.service;

import com.test.article.exemple.purchase.domain.purchase.model.Product;

import java.util.EnumMap;

public class StoreServiceImpl implements StoreService {

    private final EnumMap<Product, Integer> stockInMemory;

    public StoreServiceImpl() {
        stockInMemory = new EnumMap<>(Product.class);
        stockInMemory.put(Product.SHAMPOO, 10);
        stockInMemory.put(Product.SHOWER_GEL, 20);
        stockInMemory.put(Product.TOOTH_PASTE, 5);
    }

    @Override
    public void decreaseProduct(final Product product, final int amount) {
        stockInMemory.put(product, stockInMemory.get(product) - amount);
    }

    @Override
    public boolean storeIsEnought(final Product product, final int amount) {
        return amount <= stockInMemory.get(product);
    }

    @Override
    public int getStock(final Product product) {
        return stockInMemory.get(product);
    }
}
