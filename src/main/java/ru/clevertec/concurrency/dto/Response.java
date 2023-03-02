package ru.clevertec.concurrency.dto;

public class Response {
    private int field;

    public Response(int field) {
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
        return "Response{" +
                "field=" + field +
                '}';
    }
}
