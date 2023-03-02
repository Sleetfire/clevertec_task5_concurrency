package ru.clevertec.concurrency.dto;

public class Request {

    private int field;

    public Request(int field) {
        this.field = field;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "Request{" +
                "field=" + field +
                '}';
    }
}
