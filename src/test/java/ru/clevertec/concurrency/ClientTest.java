package ru.clevertec.concurrency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.concurrency.dto.Response;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ClientTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest(name = "{index}: Requests count = {0}")
    @ValueSource(ints = {10, 100, 1000})
    @DisplayName("Checking generating requests")
    void checkGenerateRequestShouldReturnRequestListSize(int requestCount) {
        Client client = new Client(requestCount);
        int count = client.getRequestList().size();
        assertThat(count).isEqualTo(requestCount);
    }

    @ParameterizedTest(name = "{index}: Requests count = {0}")
    @ValueSource(ints = {10, 100, 1000})
    @DisplayName("Checking adding responses to the response list")
    void checkAddResponseToListShouldReturnListSize(int limit) {
        Client client = new Client(1);
        Stream.generate(() -> new Response(10))
                .limit(limit)
                .forEach(client::addResponse);
        int responseListSize = client.getResponseList().size();
        assertThat(responseListSize).isEqualTo(limit);
    }
}