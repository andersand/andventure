package net.andersand.andventure.model.level;

import net.andersand.andventure.model.LevelListener;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.elements.Creature;
import net.andersand.andventure.model.elements.Element;
import net.andersand.andventure.model.elements.Foe;
import net.andersand.andventure.model.elements.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class Level implements LevelListener, Renderable {

    private List<Element> elements;
    private Meta meta;

    private List<Creature> creaturesAI = new ArrayList<Creature>();
    private Player player;

    public void setElements(List<Element> elements) {
        this.elements = elements;
        for (Element element : elements) {
            if (element instanceof Player) {
                player = (Player)element;
            }
            if (element instanceof Foe) {
                creaturesAI.add((Foe)element);
            }
        }
    }

    public void render() {
        for (Element element : elements) {
            element.render();
        }
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * todo LOW consider a double array for indexed lookup, to improve performance if necessary.
     * @return element at relative position, null if floor
     */
    public Element look(Position position) {
        if (position == null) {
            throw new IllegalStateException("Cannot look without a position");
        }
        for (Element element : elements) {
            if (element.getPosition().equals(position)) {
                return element;
            }
        }
        return null;
    }

    public List<Creature> getCreaturesAI() {
        return creaturesAI;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }
}
