package net.andersand.andventure.model;

import net.andersand.andventure.engine.Mapper;
import net.andersand.andventure.model.elements.Object;
import net.andersand.andventure.model.elements.Weapon;
import net.andersand.andventure.model.elements.Wearable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author asn
 */
public class Inventory {

    protected List<Object> objects;

    public Inventory() {
        this.objects = new ArrayList<Object>();
    }

    public boolean has(String objectString) {
        List<Class<? extends Object>> queryObjects = objectStringsToClass(objectString);
        return has(queryObjects);
    }

    public boolean has(Class<? extends Object>... queryObjects) {
        return has(Arrays.asList(queryObjects));
    }
    
    private boolean has(List<Class<? extends Object>> queryObjects) {
        for (Object object : objects) {
            if (queryObjects.contains(object.getClass())) {
                return true;
            }
        }
        return false;
    }

    private List<Class<? extends Object>> objectStringsToClass(String objectString) {
        List<Class<? extends Object>> queryObjects = new ArrayList<Class<? extends Object>>();
        for (int i = 0; i < objectString.length(); i++) {
            queryObjects.add(Mapper.lookupObject(String.valueOf(objectString.charAt(i))));
        }
        return queryObjects;
    }

    public void addObject(Object object) {
        objects.add(object);
    }

    public void removeObject(Object object) {
        objects.remove(object);
    }

    public void addObjectsFromString(String objectString) {
        List<Class<? extends Object>> objectClasses = objectStringsToClass(objectString);
        try {
            for (Class<? extends Object> objectClass : objectClasses) {
                addObject(objectClass.newInstance());
            }
        }
        catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public List<Wearable> getWearables() {
        List<Wearable> wearables = new ArrayList<Wearable>();
        for (Object object : objects) {
            if (object instanceof Wearable) {
                wearables.add((Wearable)object);
            }
        }
        return wearables;
    }

    public List<Weapon> getWeapons() {
        List<Weapon> weapons = new ArrayList<Weapon>();
        for (Object object : objects) {
            if (object instanceof Weapon) {
                weapons.add((Weapon) object);
            }
        }
        return weapons;
    }
    
    public Weapon getBestWeapon() {
        List<Weapon> allWeapons = getWeapons();
        if (allWeapons.isEmpty()) {
            return null;
        }
        Iterator<Weapon> it = getWeapons().iterator();
        Weapon best = it.next();
        while (it.hasNext()) {
            Weapon weapon = it.next();
            if (weapon.getAttackValue() > best.getAttackValue()) {
                best = weapon;
            }
        }
        return best;
    }
}
