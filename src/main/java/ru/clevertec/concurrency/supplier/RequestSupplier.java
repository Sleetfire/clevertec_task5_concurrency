package ru.clevertec.concurrency.supplier;

import ru.clevertec.concurrency.dto.Request;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class RequestSupplier implements Supplier<Request> {

    @Override
    public Request get() {
        int result = ThreadLocalRandom.current().nextInt(100);
        return new Request(result);
    }
}
