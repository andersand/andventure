package net.andersand.andventure.model;

/**
 * @author asn
 */
public class Position {

    protected int x;
    protected int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new position relative to given position
     */
    public Position(Position position, int changeX, int changeY) {
        this.x = position.getX() + changeX;
        this.y = position.getY() + changeY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position p = (Position)obj;
            return p.getX() == x && p.getY() == y;
        }
        return obj != null && super.equals(obj);
    }

    public Position copy() {
        return new Position(this, 0, 0);
    }

    public void change(int xMod, int yMod) {
        x += xMod;
        y += yMod;
    }

    public static Position parsePosition(String positionStr) {
        String[] parts = positionStr.split("x");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new Position(x, y);
    }
}
