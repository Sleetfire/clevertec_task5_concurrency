package ru.clevertec.concurrency;

import ru.clevertec.concurrency.dto.Request;
import ru.clevertec.concurrency.dto.Response;
import ru.clevertec.concurrency.supplier.RequestSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Client {

    private final List<Request> requestList;
    private final List<Response> responseList = new ArrayList<>();
    private static final ReentrantLock lock = new ReentrantLock();

    public Client(int requestCount) {
        this.requestList = Stream.generate(new RequestSupplier())
                .limit(requestCount)
                .toList();
    }

    public Client(List<Request> requestList) {
        this.requestList = requestList;
    }

    public void sendWithCallable() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        this.requestList.forEach(request -> {
            Callable<Response> job = () -> Server.receive(request);
            Future<Response> future = executorService.submit(job);
            try {
                responseList.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    public void sendWithLock() {
        Consumer<Response> lockConsumer = this::addResponseWithLock;
        this.send(lockConsumer);
    }

    public void sendWithoutLock() {
        Consumer<Response> withoutLockConsumer = this::addResponse;
        this.send(withoutLockConsumer);
    }

    private void send(Consumer<Response> responseConsumer) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        requestList.forEach(request -> CompletableFuture
                .supplyAsync(() -> Server.receive(request), executor)
                .thenAccept(responseConsumer));

        while (executor.getActiveCount() != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        executor.shutdown();
    }

    public void addResponseWithLock(Response response) {
        lock.lock();
        try {
            this.addResponse(response);
        } finally {
            lock.unlock();
        }
    }

    public void addResponse(Response response) {
        this.responseList.add(response);
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public List<Response> getResponseList() {
        return responseList;
    }
}



