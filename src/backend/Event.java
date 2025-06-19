package backend;

public class Event <T> {

    public enum events {add, remove, undo, redo}

    private final ListElement<T> listCopy;
    private final events event;

    public Event(ListElement<T> listCopy, events event) {
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
