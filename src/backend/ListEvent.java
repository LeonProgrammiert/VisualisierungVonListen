package backend;

public class ListEvent<T> {

    public enum events {add, remove, undo, redo}

    private final ListElement<T> listCopy;
    private final events event;

    public ListEvent(ListElement<T> listCopy, events event) {
        this.event = event;
        this.listCopy = listCopy;
    }

    public ListElement<T> getCopy() {
        return listCopy;
    }

    public events getEvent() {
        return event;
    }
}
