package backend;

import backend.enumerations.SwapPositions;
import ui.legos.CustomListElementPanel;

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

    public static boolean switchAvailable(ListElement listElement, SwapPositions swapPositions) {
        if (swapPositions == SwapPositions.previous) {
            return !listElement.isFirst();
        }
        else if (swapPositions == SwapPositions.next) {
            return !listElement.isLast();
        }
        return false;
    }

    public static ListElement switchElements(ListElement current, SwapPositions position) {
        if (position == SwapPositions.previous) {
            ListElement prev = current.getPrevious();
            if (prev == null) return current; // Can't switch if no previous

            ListElement before = prev.getPrevious();
            ListElement next = current.getNext();

            // Re-link surrounding nodes
            if (before != null) before.setNext(current);
            current.setPrevious(before);
            current.setNext(prev);

            prev.setPrevious(current);
            prev.setNext(next);

            if (next != null) next.setPrevious(prev);

            // Return new "current"
            return current;
        } else {
            ListElement next = current.getNext();
            if (next == null) return current; // Can't switch if no next

            ListElement after = next.getNext();
            ListElement prev = current.getPrevious();

            // Re-link surrounding nodes
            if (prev != null) prev.setNext(next);
            next.setPrevious(prev);
            next.setNext(current);

            current.setPrevious(next);
            current.setNext(after);

            if (after != null) after.setPrevious(current);

            // Return new "current"
            return next;
        }
    }
}
