package com.test.article.exemple.purchase.domain.purchase.usecase;

import com.test.article.exemple.purchase.domain.customer.error.CustomerNotLoggedException;
import com.test.article.exemple.purchase.domain.customer.error.InvalidCustomerException;
import com.test.article.exemple.purchase.domain.customer.model.Customer;
import com.test.article.exemple.purchase.domain.customer.service.CustomerService;
import com.test.article.exemple.purchase.domain.customer.service.CustomerServiceImpl;
import com.test.article.exemple.purchase.domain.purchase.model.History;
import com.test.article.exemple.purchase.domain.purchase.model.Product;
import com.test.article.exemple.purchase.domain.purchase.service.PurchaseService;
import com.test.article.exemple.purchase.domain.purchase.service.PurchaseServiceImpl;
import com.test.article.exemple.purchase.domain.purchase.service.ShoppingHistoryService;
import com.test.article.exemple.purchase.domain.purchase.service.ShoppingHistoryServiceImpl;
import com.test.article.exemple.purchase.domain.purchase.service.StoreService;
import com.test.article.exemple.purchase.domain.purchase.service.StoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PurchaseUseCaseTest {

    private StoreService storeService;

    private CustomerService customerService;

    private ShoppingHistoryService shoppingHistoryService;

    private PurchaseService sut; // sut = System Under Test

    @TempDir
    File tempDir;

    @BeforeEach
    public void init() {
        storeService = new StoreServiceImpl();
        shoppingHistoryService = new ShoppingHistoryServiceImpl(tempDir.getAbsolutePath(), "test.txt");
        customerService = new CustomerServiceImpl();
        sut = new PurchaseServiceImpl(storeService, customerService, shoppingHistoryService);
    }

    @Test
    void customer_purchase_successful_product() {
        // given
        final Customer customer = customerService.createAccount("name", "test@test.com");
        customerService.login(customer.name(), customer.email());
        // when
        final boolean result = sut.purchase(Product.SHOWER_GEL, 10, customer.id());
        // then
        assertTrue(result);

        // check side effect
        assertEquals(new History(customer.id(), Product.SHOWER_GEL, 10), shoppingHistoryService.getLast(customer.id()));
        assertEquals(10, storeService.getStock(Product.SHOWER_GEL));
    }

    @Test
    void customer_purchase_successful_all_product() {
        // given
        final Customer customer1 = customerService.createAccount("name", "test@test.com");
        customerService.createAccount("name2", "test2@test.com");
        customerService.createAccount("name3", "test3@test.com");
        customerService.login(customer1.name(), customer1.email());
        // when
        final boolean result = sut.purchase(Product.SHAMPOO, 10, customer1.id());
        // then
        assertTrue(result);

        // check side effect
        assertEquals(new History(customer1.id(), Product.SHAMPOO, 10), shoppingHistoryService.getLast(customer1.id()));
        assertEquals(0, storeService.getStock(Product.SHAMPOO));
    }

    @Test
    void customer_not_purchase_not_enought_product() {
        // given
        final Customer customer = customerService.createAccount("name", "test@test.com");
        customerService.login(customer.name(), customer.email());
        // when
        final boolean result = sut.purchase(Product.TOOTH_PASTE, 30, customer.id());
        // then
        assertFalse(result);

        // check side effect
        List<History> histories = shoppingHistoryService.getHistory(customer.id());
        assertTrue(histories.isEmpty());
        assertEquals(5, storeService.getStock(Product.TOOTH_PASTE));
    }

    @Test
    void customer_not_purchase_not_enought_product_after_decreased() {
        // given
        final Customer customer = customerService.createAccount("name", "test@test.com");
        customerService.login(customer.name(), customer.email());
        sut.purchase(Product.SHOWER_GEL, 10, customer.id());
        sut.purchase(Product.SHOWER_GEL, 5, customer.id());
        // when
        final boolean result = sut.purchase(Product.SHOWER_GEL, 10, customer.id());
        // then
        assertFalse(result);

        // check side effect
        assertEquals(new History(customer.id(), Product.SHOWER_GEL, 5), shoppingHistoryService.getLast(customer.id()));
        assertEquals(5, storeService.getStock(Product.SHOWER_GEL));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7})
    void unknown_customer_can_not_purchase_product(int unknownCustomerId) {
        // given
        customerService.createAccount("name", "test@test.com");
        // when - then
        assertThrows(
                InvalidCustomerException.class,
                () -> sut.purchase(Product.SHOWER_GEL, 10, unknownCustomerId)
        );

        // check side effect
        assertEquals(20, storeService.getStock(Product.SHOWER_GEL));
        assertEquals(new History(0, null, 0), shoppingHistoryService.getLast(unknownCustomerId));
    }

    @Test
    void customer_not_logged_can_not_purchase_product() {
        // given
        final Customer customer = customerService.createAccount("name", "test@test.com");
        // when - then
        assertThrows(
                CustomerNotLoggedException.class,
                () -> sut.purchase(Product.SHOWER_GEL, 10, customer.id())
        );
    }
}
