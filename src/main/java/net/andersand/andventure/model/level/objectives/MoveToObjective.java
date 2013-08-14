package net.andersand.andventure.model.level.objectives;

import net.andersand.andventure.interactions.ObjectiveLevelInteraction;
import net.andersand.andventure.model.Position;

/**
 * @author asn
 */
public class MoveToObjective extends Objective {

    protected Position target;
    
    @Override
    protected boolean isObjectiveCompleted(ObjectiveLevelInteraction objectiveLevelInteraction) {
        return objectiveLevelInteraction.getPlayerPosition().equals(getTarget());
    }

    private Position getTarget() {
        if (target == null) {
            target = Position.parsePosition(value);
        }
        return target;
    }
}
