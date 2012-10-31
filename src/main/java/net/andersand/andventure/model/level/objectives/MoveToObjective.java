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
        return false;  // this objective type needs access to player.position
    }
}
