package net.andersand.andventure.model.level.script;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class Script {
    List<Action> actions;

    public Script() {
        this.actions = new ArrayList<Action>();
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }
}
