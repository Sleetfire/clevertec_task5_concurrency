package ru.clevertec.concurrency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.concurrency.dto.Request;
import ru.clevertec.concurrency.dto.Response;

import static org.assertj.core.api.Assertions.*;

class ServerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest(name = "{index}: Requests count = {0}")
    @ValueSource(ints = {10, 42, 77})
    @DisplayName("Checking receiving request to the server and checking response with result")
    void checkReceiveShouldReturnResponse(int field) {
        Request request = new Request(field);
        Response response = Server.receive(request);
        int result = 100 - field;
        assertThat(response.getField()).isEqualTo(result);
    }

}