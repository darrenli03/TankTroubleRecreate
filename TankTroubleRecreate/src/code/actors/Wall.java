package code.actors;

public class Wall {

    private int x;
    private int y;
    private int width;
    private int height;

    public Wall(int xx, int yy, int w, int h){
        x = xx;
        y = yy;
        width = w;
        height = h;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getMiddleX(){return (x + width) / 2;}
    public int getMiddleY(){return (y + height) / 2;}



}
