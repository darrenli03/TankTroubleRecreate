package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 *
 * @author Darren Li
 */
public class GameObject
{
    //instance variables
    private double x;
    private double y;
    private int width;
    private int height;

    //Constructor
    public GameObject(double xx, double yy, int h, int w)
    {
        //Initialize the variables
        x=xx; y=yy;
        width = w;
        height = h;
    }

    //accessors
    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Rectangle getHitBox() { return new Rectangle((int)x,(int)y,width, height); }

    //modifiers
    public void setX(double xx) { x = xx; }
    public void setY(double yy) { y = yy; }
    public void setSize(int w, int h) { width = w; height = h; }

    //methods
    public boolean intersects(GameObject go)
    {
        return (this.getHitBox().intersects(go.getHitBox()));
    }

    public void draw(Graphics g, int offset, ImageObserver io)
    {   //This method should be overridden!!!============
        g.setColor(Color.BLUE);
        g.fillRect((int)x-offset, (int)y, width, height);
    }
}

