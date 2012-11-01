package net.andersand.andventure.model;

import org.newdawn.slick.SlickException;

/**
 * Consider the purpose of this interface. AFAICT it's only useful if there will be a general renderering of Renderables,
 * At the moment, render() is called from various methods, which doesn't make this interface come to its right.
 * 
 * @author asn
 */
public interface Renderable {
    
    public void render() throws SlickException;
    
}
