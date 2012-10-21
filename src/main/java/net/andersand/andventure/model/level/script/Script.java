package net.andersand.andventure.model.level.script;

import net.andersand.andventure.model.Position;

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

    /**
     * @param position specified position or null (meaning player)
     * @return assigned equipmentString for position or player
     */
    public String getAssignedEquipment(Position position) {
        for (Action action : actions) {
            if (action instanceof EquipAction
                    && (
                        (position == null && action.getPosition() == null) ||
                        position.equals(action.getPosition())
                    )) {

                return removeQuotes(((EquipAction) action).getEquipment());
            }
        }
        return null;
    }

    private String removeQuotes(String equipment) {
        return equipment.replace("\"", "");
    }
}
