package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.interactions.ComplexInteraction;
import net.andersand.andventure.model.interactions.DialogInteraction;
import net.andersand.andventure.model.interactions.Interaction;
import net.andersand.andventure.model.interactions.SimpleInteraction;
import net.andersand.andventure.model.level.script.DialogStatement;
import net.andersand.andventure.model.level.script.Script;
import net.andersand.andventure.model.level.script.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Neutrals can be either passive or NPCs
 * 
 * @author asn
 */
public class Neutral extends Creature implements Interactable {

    protected List<Statement> npcStatements;
    protected List<DialogStatement> dialogStatements;

    @Override
    public void init(char levelDataChar, Script script) {
        initNPC(script);
        bodyImage = Util.loadElementImage("n");
    }

    private void initNPC(Script script) {
        this.npcStatements = script.initNPC(this);
        dialogStatements = new ArrayList<DialogStatement>();
        for (Statement stat : npcStatements) {
            if (stat instanceof DialogStatement) {
                dialogStatements.add((DialogStatement) stat);
            }
        }
        // the npcStatements list should contain non-dialog statements only
        for (DialogStatement statement : dialogStatements) {
            npcStatements.remove(statement);
        }
    }

    @Override
    protected boolean preventMove() {
        return true; // For now, NPCs do not move
    }

    @Override
    protected String getDeadImage() {
        return "nd"; // dead npc in spritesheet looks better ;-)
    }

    @Override
    protected String getBodyImage() {
        return "n";
    }

    /**
     * @param actor the Creature that interacts with this Neutral.
     */
    @Override
    public Interaction interact(Creature actor) {
        if (actor instanceof Player) {
            if (!dialogStatements.isEmpty()) {
                ComplexInteraction complexInteraction = new DialogInteraction(dialogStatements.remove(0));
                complexInteraction.setStatements(npcStatements);
                npcStatements.clear(); // making sure only the first dialog interaction yields chained statements
                return complexInteraction;
            }
        }
        return SimpleInteraction.INTERACTION_PERFORMED;
    }
}
