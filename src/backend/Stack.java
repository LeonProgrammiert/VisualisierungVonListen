package backend;

import java.util.ArrayList;

public class Stack <T>{

    ArrayList<ListEvent<T>> stack;

    StackManager<T> stackManager;

    public Stack(StackManager<T> stackManager) {
        stack = new ArrayList<>();
        this.stackManager = stackManager;
    }

    public void push(ListEvent<T> newEvent) {
        // Add another event to the stack.
        stack.add(newEvent);
    }

    private ListEvent<T> pop() {
        // Return the last event of the stack.
         if (!stack.isEmpty()) {
            return stack.removeLast();
         }
         return null;
    }

    public ListEvent<T> previousState() {
        if (isAvailable()) {
            return pop();
        }
        return null;
    }

    public boolean isAvailable() {
        // Stack is available, if it is not empty
        return !stack.isEmpty();
    }
}
