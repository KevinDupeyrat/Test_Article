package com.test.article.exemple.purchase.domain.purchase.service;

import com.test.article.exemple.purchase.domain.purchase.model.History;
import com.test.article.exemple.purchase.domain.purchase.model.Product;

import java.util.List;

public interface ShoppingHistoryService {

    void saveToHistory(final int customerId, final Product product, final int amount);

    List<History> getHistory(final int customerId);

    History getLast(final int customerId);
}
