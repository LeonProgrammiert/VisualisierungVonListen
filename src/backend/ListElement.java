package backend;

import controls.Controller;

//Datenstruktur (verkette Listen, speichert Daten und verknüpfungen)
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

    public ListElement<T> getFirst() {
        ListElement<T> first = this;
        while (first.getPrevious() != null) {
            first = first.getPrevious();
        }
        return first;
    }

    public ListElement<T> getTail() {
        ListElement<T> tail = this;
        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        return tail;
    }

    public String getElement() {
        try {
            return data.toString();
        } catch (Exception e) {
            Controller.displayMessage("Der Typ kann nicht in einen String umgewandelt werden!", "Fehlermeldung");
            return "Error";
        }
    }

    public int getIndex() {
        ListElement<T> first = getFirst();
        int counter = 0;
        while (first != this) {
            counter ++;
            first = first.getNext();
        }
        return counter;
    }

    private int getSize() {
        ListElement<T> first = getFirst();
        int counter = 0;
        while (first != null) {
            counter ++;
            first = first.getNext();
        }
        return counter;
    }

    public int getMaxIndex() {
        return getSize() - 1;
    }

}
