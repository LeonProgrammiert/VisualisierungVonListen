package backend;

import controls.Controller;
//Datenstruktur (verkette Listen, speichert Daten und verknüpfungen)
public class ListElement<T> {

    private ListElement<T> previous;
    private ListElement<T> next;

    private T data;

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
        //Gibt Array mit drei Werten zurück: Knoten oder „N/A“
        String[] displayableData = new String[3];
        displayableData[0] = previous != null ? previous.getElement() : "N/A";
        displayableData[1] = data != null ? getElement() : "N/A";
        displayableData[2] = next != null ? next.getElement() : "N/A";

        return displayableData;
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

    public ListElement<T> deepCopy() {
        // Returns a deep copy of the list

        // Handle empty list
        if (getFirst() == null) {
            return null;
        }

        // Copy the first node
        ListElement<T> original = getFirst();
        ListElement<T> newFirst = new ListElement<>(original.data);
        newFirst.previous = null;

        ListElement<T> currOriginal = original.next;
        ListElement<T> currCopy = newFirst;

        // Copy the rest of the nodes
        while (currOriginal != null) {
            ListElement<T> newNode = new ListElement<>(currOriginal.data);
            currCopy.next = newNode;
            newNode.previous = currCopy;

            currCopy = newNode;
            currOriginal = currOriginal.next;
        }

        return newFirst;
    }

    public void setData(T data) {
        this.data = data;
    }

}
