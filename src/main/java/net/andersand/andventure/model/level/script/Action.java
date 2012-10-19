package net.andersand.andventure.model.level.script;

import net.andersand.andventure.model.Position;

/**
 * @author asn
 */
public abstract class Action {

    protected Position position;

    public Action(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
