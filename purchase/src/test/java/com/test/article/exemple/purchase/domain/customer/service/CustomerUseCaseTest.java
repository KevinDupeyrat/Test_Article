package com.test.article.exemple.purchase.domain.customer.service;

import com.test.article.exemple.purchase.domain.customer.error.InvalidCustomerException;
import com.test.article.exemple.purchase.domain.customer.model.Customer;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerUseCaseTest {

    private CustomerService sut; // sut = System Under Test

    @BeforeEach
    void init() {
        sut = new CustomerServiceImpl();
    }

    @Test
    void customer_login_successful() {
        // given
        final var name = "name";
        final var email = "email@email.com";
        sut.createAccount(name, email);
        sut.createAccount("fakeName", "fake@fake.com");
        // when
        final Customer result = sut.login(name, email);
        // then
        assertEquals(new Customer(1, name, email), result);
    }

    @Test
    void unknown_customer_not_login() {
        // given
        final var name = "name";
        final var email = "email@email.com";
        sut.createAccount(name, "fake@fake.com");
        sut.createAccount("fakeName", email);
        // when - then
        assertThrows(
                InvalidCustomerException.class,
                () -> sut.login(name, email)
        );
    }

    @Test
    void customer_logout_successful() {
        // given
        final var name = "name";
        final var email = "email@email.com";
        final Customer customerLogged = sut.createAccount(name, email);
        sut.login(name, email);
        // when
        final boolean result = sut.logout(customerLogged.id());
        // then
        assertTrue(result);
    }

    @Test
    void customer_can_not_logout_before_login() {
        // given
        final var name = "name";
        final var email = "email@email.com";
        final Customer customerLogged = sut.createAccount(name, email);
        // when
        final boolean result = sut.logout(customerLogged.id());
        // then
        assertFalse(result);
    }

    @Test
    void unknow_customer_not_logout() {
        // when - then
        assertThrows(
                InvalidCustomerException.class,
                () -> sut.logout(1)
        );
    }

    @Test
    void get_customer_by_id() {
        // given
        final var name = "name";
        final var email = "email@email.com";
        sut.createAccount(name, email);
        sut.createAccount("fakeName", "fake@email.com");
        final Customer customer = sut.login(name, email);
        // when
        final Customer result = sut.getCustomer(customer.id());
        // then
        assertEquals(customer, result);
    }

    @Test
    void not_get_customer_by_id_unknown_customer() {
        // when - then
        assertThrows(
                InvalidCustomerException.class,
                () -> sut.getCustomer(1)
        );
    }

    @Test
    void customer_create_account_successful() {
        // given
        final var name = "name";
        final var email = "email@email.com";
        // when
        final Customer result = sut.createAccount(name, email);
        // then
        assertNotNull(result);
    }

    @Test
    void two_customer_with_same_name_and_email_create_account_successful_and_have_different_id() {
        // given
        final var name = "name";
        final var email = "email@email.com";
        // when
        final Customer customer1 = sut.createAccount(name, email);
        final Customer customer2 = sut.createAccount(name, email);
        // then
        assertNotEquals(customer1, customer2);
    }

    @ParameterizedTest
    @MethodSource("nameNullEmptyWhitespace")
    void customer_not_create_account_name_invalid(final String name, final String email) {
        // when - then
        assertThrows(
                IllegalArgumentException.class,
                () -> sut.createAccount(name, email)
        );
    }

    @ParameterizedTest
    @MethodSource("emailNullEmptyWhitespace")
    void customer_not_create_account_email_invalid(final String name, final String email) {
        // when - then
        assertThrows(
                IllegalArgumentException.class,
                () -> sut.createAccount(name, email)
        );
    }

    public static Stream<Arguments> nameNullEmptyWhitespace() {
        return Stream.of(
                Arguments.of(null, "email@email.com"),
                Arguments.of(StringUtils.EMPTY, "email@email.com"),
                Arguments.of(StringUtils.SPACE, "email@email.com"),
                Arguments.of("       ", "email@email.com")
        );
    }

    public static Stream<Arguments> emailNullEmptyWhitespace() {
        return Stream.of(
                Arguments.of("name", null),
                Arguments.of("name", StringUtils.EMPTY),
                Arguments.of("name", StringUtils.SPACE),
                Arguments.of("name", "       "),
                Arguments.of("name", "foo"),
                Arguments.of("name", "foo@bar")
        );
    }
}
