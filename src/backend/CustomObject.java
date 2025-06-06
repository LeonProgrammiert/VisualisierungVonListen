package backend;

import controls.Controller;

public class CustomObject<T> {

    private CustomObject<T> previous;
    private CustomObject<T> next;

    private final T data;

    public CustomObject(T data) {
        this.data = data;
    }

    public void setPrevious(CustomObject<T> previous) {
        this.previous = previous;
    }

    public void setNext(CustomObject<T> next) {
        this.next = next;
    }

    public CustomObject<T> getPrevious() {
        return previous;
    }

    public CustomObject<T> getNext() {
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
        displayableData[0] = previous != null ? previous.getElement() : "NaN";
        displayableData[1] = data != null ? getElement() : "NaN";
        displayableData[2] = next != null ? next.getElement() : "NaN";

        return displayableData;
    }
}
