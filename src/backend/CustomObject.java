package backend;

public class CustomObject<T> {

    CustomObject previous;
    CustomObject next;

    T data;

    public CustomObject(T data) {
        this.data = data;
    }

    public void setPrevious(CustomObject previous) {
        this.previous = previous;
    }
    public void setNext(CustomObject next) {
        this.next = next;
    }

    public CustomObject getPrevious() {
        return previous;
    }
    public CustomObject getNext() {
        return next;
    }

    public T getData() {
        return data;
    }
}
