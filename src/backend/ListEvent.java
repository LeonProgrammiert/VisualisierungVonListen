package backend;

public record ListEvent<T>(ListElement<T> listCopy, backend.ListEvent.events event) {

    public enum events {add, remove, switchPrevious, switchNext, undo, redo}

    public ListEvent(ListElement<T> listCopy, events event) {
        this.event = event;
        this.listCopy = ListUtilities.deepCopy(listCopy);
    }
}
