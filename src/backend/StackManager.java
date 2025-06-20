package backend;

import controls.Controller;

public class StackManager<T> {

    private Stack<T> undoStack;
    private Stack<T> redoStack;
    private static StackManager instance;

    private final Controller controller;

    public StackManager(Controller controller) {
        this.controller = controller;
        initialize();
    }

    public void initialize() {
        undoStack = new Stack<>(this);
        redoStack = new Stack<>(this);
    }

    public void push(ListEvent<T> event) {
        // Method for add and remove

        // Gets the eventType
        ListEvent.events eventType = event.getEvent();

        // Checks for unwanted eventTypes
        if (eventType == ListEvent.events.undo || eventType == ListEvent.events.redo) {
            throw new RuntimeException("Wrong event type given to push");
        }

        // Pushes the event to the undoStack
        undoStack.push(event);

        // Updates the availability of the buttons
        updateButtons();
    }

    public ListElement<T> pull(ListEvent<T> event) {
        // Method for redo and undo

        ListEvent<T> prevState;
        switch (event.getEvent()) {
            case undo:
                // Gets the previous state and deletes it from the undo-Stack
                prevState = undoStack.previousState();
                // Pushes the previous state to the redo-Stack
                if (prevState != null)
                    redoStack.push(new ListEvent<>(prevState.getCopy(), ListEvent.events.redo));
                break;
            case redo:
                // Gets the previous state and deletes it from the redo-Stack
                prevState = redoStack.previousState();
                // Pushes the previous state to the undo-Stack
                if (prevState != null)
                    undoStack.push(new ListEvent<>(prevState.getCopy(), ListEvent.events.undo)); // dito
                break;
            default:
                throw new RuntimeException("Wrong event type given to pull");
        }

        // Updates the availability of the buttons
        updateButtons();
        // Returns the previous state or null
        return (prevState != null) ? prevState.getCopy() : null;

    }

    private void updateButtons() {
        // Sets the availability of the undo-/redo-Button
        controller.getListEditor().setUndoRedoButtonAvailability(
                undoStack.isAvailable(),
                redoStack.isAvailable()
        );
    }

    public static StackManager getInstance() {
        if (instance == null) {
            instance = new StackManager(Controller.getController());
        }
        return instance;
    }
}
