package backend;

import controls.Controller;

public class ListElement<T> {

    private ListElement<T> previous;
    private ListElement<T> next;

    private final T data;

    public ListElement(T data) {
        this.data = data;
    }

    public void setPrevious(ListElement<T> previous) {
        this.previous = previous;
    }

    public void setNext(ListElement<T> next) {
        this.next = next;
    }

    public ListElement<T> getPrevious() {
        return previous;
    }

    public ListElement<T> getNext() {
        return next;
    }

    public String getElement() {
        try {
            return data.toString();
        } catch (Exception e) {
            Controller.handleError("Der Typ kann nicht in einen String umgewandelt werden!");
            return "Error";
        }
    }

    public String[] getData() {
        String[] displayableData = new String[3];
        displayableData[0] = previous != null ? previous.getElement() : "N/A";
        displayableData[1] = data != null ? getElement() : "N/A";
        displayableData[2] = next != null ? next.getElement() : "N/A";

        return displayableData;
    }
}
