package backend;

public class CustomObject<T> {

    T previous;
    T current;
    T next;

    public CustomObject(T previous, T current, T next) {
        this.previous = previous;
        this.current = current;
        this.next = next;
    }
}
