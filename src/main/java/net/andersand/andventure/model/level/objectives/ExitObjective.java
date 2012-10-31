package net.andersand.andventure.model.level.objectives;

import net.andersand.andventure.interactions.ObjectiveLevelInteraction;

/**
 * @author asn
 */
public class ExitObjective extends Objective {
    @Override
    protected boolean isObjectiveCompleted(ObjectiveLevelInteraction player) {
        return false;
    }
}
