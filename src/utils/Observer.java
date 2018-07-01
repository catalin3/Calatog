package utils;

public interface Observer<T> {
    void notyfyEvent(ListEvent<T> e);
}
