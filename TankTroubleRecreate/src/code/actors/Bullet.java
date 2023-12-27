package code.actors;

import java.awt.*;
import java.util.Random;

public class Bullet {

    private double x;  //The horizontal X position of the (left side of the) bullet
    private double y;  //The vertical Y position of the (top of the) bullet
    private double xVel;   //The horizontal velocity of the bullet
    private double yVel;   //The vertical velocity of the bullet
    private int radius;  //The radius of the bullet
    private int lifetime;
    private double totalVel;

//    public Bullet()
//    {
//        //Initialize the instance variables...
//        Random randy = new Random();
//        x = randy.nextInt(300);     //Start near the middle of the width.
//        y = randy.nextInt(200);     //Start near the middle.
//        xVel = randy.nextInt(9)-4;  //Choose a random starting velocity
//        yVel = 0;
//
//    }

    public Bullet(int r, int startX, int startY, double angleDegrees) {
        //Initialize the instance variables...
        Random randy = new Random();
        totalVel = 5.5;
        radius = r;
        x = startX;     //Start near the middle of the width.
        y = startY;     //Start near the middle.
        xVel = (totalVel * Math.cos(Math.toRadians(angleDegrees)));
        yVel = -(totalVel * Math.sin(Math.toRadians(angleDegrees)));
        lifetime = 600;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getLifetime() {
        return lifetime;
    }

    public Point getCenter() {
        return new Point((int) (x + radius / 2), (int) (y + radius / 2));
    }

    public int getSize() {
        return radius;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, radius, radius);
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public void setX(int in) {
        x = in;
    }

    public void setY(int in) {
        y = in;
    }

    public void setXVel(double in) {
        xVel = in;
    }

    public void setYVel(double in) {
        yVel = in;
    }

    public void animate() {
        x += xVel;
        y += yVel;
        lifetime--;

    }

}
