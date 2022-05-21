package com.test.article.exemple.purchase.domain.purchase.service;

import com.test.article.exemple.purchase.domain.purchase.error.ShoppingHistoryException;
import com.test.article.exemple.purchase.domain.purchase.model.History;
import com.test.article.exemple.purchase.domain.purchase.model.Product;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;


public class ShoppingHistoryServiceImpl implements ShoppingHistoryService {

    private final String rootPath;
    private final String fileName;

    public ShoppingHistoryServiceImpl(final String rootPath, final String fileName) {
        this.rootPath = rootPath;
        this.fileName = fileName;
    }

    @Override
    public void saveToHistory(final int customerId, final Product product, final int amount) {
        try {
            final String line = createLine(customerId, product, amount);
            FileUtils.writeStringToFile(new File(rootPath, fileName), line + "\n", StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            throw new ShoppingHistoryException(e);
        }
    }

    @Override
    public List<History> getHistory(final int customerId) {
        return getFullHistory()
                .filter(history -> history.customerId() == customerId)
                .toList();
    }

    @Override
    public History getLast(int customerId) {
        final List<History> histories = getHistory(customerId);
        if (histories.isEmpty()) {
            return new History(0, null, 0);
        }
        return histories.get(histories.size() - 1);
    }

    private String createLine(final int customerId, final Product product, final int amount) {
        return new History(customerId, product, amount).toString();
    }

    private History fromLine(final String line) {
        return History.fromString(line);
    }

    private Stream<History> getFullHistory() {
        try {
            final File file = new File(rootPath, fileName);
            if (!file.exists()) {
                return Stream.empty();
            }
            List<String> strings = FileUtils.readLines(file, StandardCharsets.UTF_8);
            return strings.stream()
                    .map(this::fromLine);
        } catch (IOException e) {
            throw new ShoppingHistoryException(e);
        }
    }

}
