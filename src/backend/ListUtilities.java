package backend;

public class ListUtilities<T>{

    public static String[] getData(ListElement current) {
        ListElement previous = current.getPrevious();
        ListElement next = current.getNext();

        //Gibt Array mit drei Werten zurück: Knoten oder „N/A“
        String[] displayableData = new String[3];
        displayableData[0] = previous != null ? previous.getElement() : "N/A";
        displayableData[1] = current.getElement();
        displayableData[2] = next != null ? next.getElement() : "N/A";

        return displayableData;
    }

    public static ListElement deepCopy(ListElement current) {
        // Returns a deep copy of the list

        // Copy the first node
        ListElement original = current.getFirst();
        ListElement newFirst = new ListElement<>(original.getElement());
        newFirst.setPrevious(null);

        ListElement currOriginal = original.getNext();
        ListElement currCopy = newFirst;

        // Copy the rest of the nodes
        while (currOriginal != null) {
            ListElement newNode = new ListElement<>(currOriginal.getElement());
            currCopy.setNext(newNode);
            newNode.setPrevious(currCopy);

            currCopy = newNode;
            currOriginal = currOriginal.getNext();
        }

        return newFirst;
    }

}
