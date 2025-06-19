package backend;

import java.util.ArrayList;

public class Stack <T>{

    ArrayList<Event<T>> stack;

    StackManager<T> stackManager;

    public Stack(StackManager<T> stackManager) {
        stack = new ArrayList<>();
        this.stackManager = stackManager;
    }

    public void push(Event<T> newEvent) {
        // Add another event to the stack.
        stack.add(newEvent);
    }

    private Event<T> pop() {
        // Return the last event of the stack.
         if (!stack.isEmpty()) {
            return stack.removeLast();
         }
         return null;
    }

    public Event<T> previousState() {
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
