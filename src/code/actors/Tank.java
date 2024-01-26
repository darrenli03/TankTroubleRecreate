package code.actors;

import java.awt.*;

public class Tank {

    private double x;
    private double y;
    private double xVel;
    private double yVel;
    private int radius = 64;
    private double angle;
    private boolean isDestroyed;
    private Image tankImage;
    private double totalVel;


    public Tank(Image tankPic, int xx, int yy) {
        x = xx;
        y = yy;
        xVel = 0;
        yVel = 0;
        isDestroyed = false;
        tankImage = tankPic;
        angle = (int) (Math.random() * 360);
        totalVel = 3.5;

    }

    public void forward() {
        if(isDestroyed){
            return;
        }

        xVel =  (totalVel * Math.cos(Math.toRadians(angle - 90)));
        yVel =  (totalVel * Math.sin(Math.toRadians(angle - 90)));

        x += xVel;
        y += yVel;


    }

    public void backward() {
        if(isDestroyed){
            return;
        }

        xVel = (0.75 * totalVel * Math.cos(Math.toRadians(angle - 90)));
        yVel = (0.75 * totalVel * Math.sin(Math.toRadians(angle - 90)));

        x -= xVel;
        y -= yVel;


    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean getDestroyed() {
        return isDestroyed;
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

    public Image getTankImage() {
        return tankImage;
    }

    public double getAngle() {
        return angle;
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

    public void setAngle(double in) {
        angle = in;
    }

    public void setDestroyed(boolean in) {
        isDestroyed = in;
    }


}
