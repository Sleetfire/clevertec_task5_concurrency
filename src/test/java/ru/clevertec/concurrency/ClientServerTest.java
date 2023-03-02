package ru.clevertec.concurrency;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class ClientServerTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest(name = "{index}: Requests count = {0}")
    @ValueSource(ints = {10, 100, 1000, 1000, 10_000, 100_000})
    @DisplayName("Checking sending requests to the server with using locking")
    void checkSendRequestWithLocking(int requestCount) {
        Client client = new Client(requestCount);
        client.sendWithLock();
        int responseCount = client.getResponseList().size();
        assertThat(responseCount).isEqualTo(requestCount);
    }

    @ParameterizedTest(name = "{index}: Requests count = {0}")
    @ValueSource(ints = {1000, 10_000, 100_000})
    @DisplayName("Checking sending requests to the server WITHOUT using locking")
    void checkSendRequestWithoutLocking(int requestCount) {
        Client client = new Client(requestCount);
        client.sendWithoutLock();
        int responseCount = client.getResponseList().size();
        assertThat(responseCount).isNotEqualTo(requestCount);
    }

}
