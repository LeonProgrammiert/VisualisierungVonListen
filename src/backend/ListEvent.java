package backend;

public class ListEvent<T> {

    public enum events {add, remove, undo, redo}

    private final ListElement<T> listCopy;
    private final events event;

    // Konstruktor nur für undo/redo ohne Daten
    public ListEvent(events event) {
        this.event = event;
        this.listCopy = null;
    }
    public ListEvent(ListElement<T> listCopy, events event) {
        this.event = event;
        this.listCopy = listCopy;
    }

    public ListElement<T> getListCopy() {
        return listCopy;
    }

    public events getEvent() {
        return event;
    }
}
