package ru.clevertec.concurrency;

import ru.clevertec.concurrency.dto.Request;
import ru.clevertec.concurrency.dto.Response;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Server {

    public static Response receive(Request request) {

        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        int calculate = calculate(request.getField());
        return new Response(calculate);
    }

    private static int calculate(int value) {
        return 100 - value;
    }

}
