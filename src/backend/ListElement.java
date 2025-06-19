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
    public void setData(T data) {
        this.data = data;
    }

    public void remove() {
        if (previous != null) {
            previous.setNext(next);
        }
        if (next != null) {
            next.setPrevious(previous);
        }
        this.previous = null;
        this.next = null;
    }

}
