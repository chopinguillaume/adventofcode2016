package util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class State {
    private SubState object;
    private List<State> children = new ArrayList<>();
    private State parent;
    private int depth;

    public State(State parent, SubState object) {
        this.object = object;
        this.parent = parent;
        if (parent != null) {
            this.depth = parent.depth + 1;
        } else {
            this.depth = 0;
        }
    }

    public int getDepth() {
        return depth;
    }

    State computeChild(Function<SubState, SubState> modifier) {
        SubState newSubState = modifier.apply(object);
        if (newSubState != null && newSubState.isValid()) {
            State newState = new State(this, newSubState);
            children.add(newState);
            return newState;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof State && object.equals(((State) obj).object);
    }

    @Override
    public String toString() {
        if (children.isEmpty()) {
            return object.toString();
        }
        return "State{" + object +
                ", " + children +
                '}';
    }

    public SubState getObject() {
        return object;
    }

    public State getParent() {
        return parent;
    }

    public void addChild(State state) {
        children.add(state);
    }
}