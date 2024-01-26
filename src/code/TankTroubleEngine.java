package code;

/*
 * Class TankTroubleEngine
 * @author Darren Li
 */

import code.actors.Bullet;
import code.actors.Tank;
import code.actors.Wall;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.sampled.Clip;


public class TankTroubleEngine extends AnimationPanel {

    //Constants
    //-------------------------------------------------------

    //Instance Variables
    //-------------------------------------------------------

    ArrayList<Projectile> laserArray = new ArrayList<Projectile>();
    ArrayList<Bullet> bulletArray = new ArrayList<Bullet>();
    ArrayList<Bullet> bulletArray1 = new ArrayList<Bullet>();
    ArrayList<Tank> tankArray = new ArrayList<Tank>();
    ArrayList<Integer> keys = new ArrayList<Integer>();
    ArrayList<Rectangle> bulletArrayHitboxes = new ArrayList<Rectangle>();
    ArrayList<Rectangle> bulletArray1Hitboxes = new ArrayList<Rectangle>();
    Timer deathTimer = new Timer(5000);
    ArrayList<Wall> walls = new ArrayList<Wall>();
    ArrayList<Rectangle> wallHitboxes = new ArrayList<Rectangle>();
    ArrayList<Shape> tankHitboxes = new ArrayList<Shape>();

    public int framesElapsed;
    public int framesElapsed1;
    public boolean bulletFired;
    public boolean bullet1Fired;
    public int blueDeaths;
    public int redDeaths;
    private long lastUpdate = System.currentTimeMillis();
    private int redWins;
    private int blueWins;
    private int draws;

    Rectangle hitbox;
    Rectangle hitbox1;
    boolean f3Pressed;

    //Constructor
    //-------------------------------------------------------
    public TankTroubleEngine() {   //Enter the name and width and height.
        super("ArcadeDemo", 1000, 1000);
        f3Pressed = false;
        Tank blueTank = new Tank(biggerTankCropped, 32, 32);
        tankArray.add(blueTank);
        Tank redTank = new Tank(redBiggerTankCropped, 672, 672);
        tankArray.add(redTank);

        //template for new walls

        // boundary walls

        //upper
        Wall w1 = new Wall(0, 0, 768, 10);
        Rectangle r1 = new Rectangle(w1.getX(),w1.getY(),w1.getWidth(),w1.getHeight());
        walls.add(w1);
        wallHitboxes.add(r1);

        //lower
        Wall w5 = new Wall(0, 768, 778, 10);
        Rectangle r5 = new Rectangle(w5.getX(),w5.getY(),w5.getWidth(),w5.getHeight());
        walls.add(w5);
        wallHitboxes.add(r5);

        //left
        Wall w9 = new Wall(0, 0, 10, 768);
        Rectangle r9 = new Rectangle(w9.getX(),w9.getY(),w9.getWidth(),w9.getHeight());
        walls.add(w9);
        wallHitboxes.add(r9);

        //right
        Wall w13 = new Wall(768, 0, 10, 768);
        Rectangle r13 = new Rectangle(w13.getX(),w13.getY(),w13.getWidth(),w13.getHeight());
        walls.add(w13);
        wallHitboxes.add(r13);

        //all other walls

        Wall w3 = new Wall(192, 384, 384, 10);
        Rectangle r3 = new Rectangle(w3.getX(),w3.getY(),w3.getWidth(),w3.getHeight());
        walls.add(w3);
        wallHitboxes.add(r3);

        Wall w17 = new Wall(384, 192, 10, 384);
        Rectangle r17 = new Rectangle(w17.getX(),w17.getY(),w17.getWidth(),w17.getHeight());
        walls.add(w17);
        wallHitboxes.add(r17);

        Wall w4 = new Wall(576, 0, 10, 192);
        Rectangle r4 = new Rectangle(w4.getX(),w4.getY(),w4.getWidth(),w4.getHeight());
        walls.add(w4);
        wallHitboxes.add(r4);


        Wall w6 = new Wall(192, 576, 10, 192);
        Rectangle r6 = new Rectangle(w6.getX(),w6.getY(),w6.getWidth(),w6.getHeight());
        walls.add(w6);
        wallHitboxes.add(r6);



    }

    //The renderFrame method is the one which is called each time a frame is drawn.
    //-------------------------------------------------------
    protected void renderFrame(Graphics g) {

        for(Wall w : walls){
            g.fillRect(w.getX(), w.getY(), w.getWidth(), w.getHeight());

        }

        long delta = System.currentTimeMillis() - lastUpdate;
        lastUpdate = System.currentTimeMillis();
        //click f3 to toggle hitboxes
        if (keys.contains(KeyEvent.VK_F3)) {
            f3Pressed = !f3Pressed;
            keys.remove((Integer)KeyEvent.VK_F3);
        }
        bulletArrayHitboxes.clear();
        bulletArray1Hitboxes.clear();
        tankHitboxes.clear();

        Iterator<Bullet> iterator = bulletArray.iterator();

        while (iterator.hasNext()) {
            Bullet b = iterator.next();
            //Drawing a circle for each bullet from bulletArray
            if (b.getLifetime() > 0) {
                g.fillOval((int) b.getX(), (int) b.getY(), 10, 10);
                bulletArrayHitboxes.add(new Rectangle((int) b.getX(),(int) b.getY(), 10, 10));

                if(f3Pressed){
                    g.setColor(Color.BLACK);
                    g.drawRect((int) b.getX(), (int) b.getY(), 10, 10);
                }


                b.animate();
            } else {
                iterator.remove();
            }

        }

        Iterator<Bullet> iterator1 = bulletArray1.iterator();

        while (iterator1.hasNext()) {
            Bullet b = iterator1.next();
            //Drawing a circle for each bullet from bulletArray1
            if (b.getLifetime() > 0) {
                g.fillOval((int) b.getX(), (int) b.getY(), 10, 10);
                bulletArray1Hitboxes.add(new Rectangle((int) b.getX(),(int) b.getY(), 10, 10));
                if(f3Pressed){
                    g.setColor(Color.BLACK);
                    g.drawRect((int) b.getX(), (int) b.getY(), 10, 10);
                }

                b.animate();
            } else {
                iterator1.remove();
            }

        }

        Iterator<Tank> tankIterator = tankArray.iterator();

        while (tankIterator.hasNext()) {
            Tank t = tankIterator.next();

            if (!t.getDestroyed()) {
                Graphics2D g1 = (Graphics2D) g.create();
                g1.rotate(Math.toRadians(t.getAngle()), t.getX() + 18, t.getY() + 32);
                g1.drawImage(t.getTankImage(), (int) t.getX(), (int) t.getY(), this);
                if(f3Pressed){
                    g1.drawRect((int) t.getX(), (int) t.getY() + 9, 36, 55);
                }
                g1.dispose();

            }

            Rectangle2D myRect = new Rectangle2D.Double(t.getX(), t.getY() + 9, 36, 55);
            AffineTransform at = AffineTransform.getRotateInstance(0, t.getX() + 18, t.getY() + 32);
            Shape rotatedRect = at.createTransformedShape(myRect);
            tankHitboxes.add(rotatedRect);

        }

        hitbox = new Rectangle((int) tankArray.get(0).getX(), (int) tankArray.get(0).getY() + 9, 36, 55);
        hitbox1 = new Rectangle((int) tankArray.get(1).getX(), (int) tankArray.get(1).getY() + 9, 36, 55);

        //collision check for bullets and tanks
        for(Rectangle r : bulletArrayHitboxes){
            if(hitbox.intersects(r) && !tankArray.get(0).getDestroyed()){
                blueDeaths++;
                tankArray.get(0).setDestroyed(true);
            }
            if(hitbox1.intersects(r) && !tankArray.get(1).getDestroyed()){
                redDeaths++;
                tankArray.get(1).setDestroyed(true);
            }
        }

        for(Rectangle r : bulletArray1Hitboxes){
            if(hitbox.intersects(r) && !tankArray.get(0).getDestroyed()){
                blueDeaths++;
                tankArray.get(0).setDestroyed(true);
            }
            if(hitbox1.intersects(r) && !tankArray.get(1).getDestroyed()){
                redDeaths++;
                tankArray.get(1).setDestroyed(true);
            }
        }

        //collision check for bullets and walls (blue tank)
        for(Rectangle r : wallHitboxes){
            for(Rectangle b : bulletArrayHitboxes){
                if(b.intersects(r)){
                    //left edge of wall rectangle
                    if(b.getX() < r.getX() + 10){
                        if(b.getY() > r.getY() && b.getY() < r.getY() + r.getHeight()){
                            bulletArray.get(bulletArrayHitboxes.indexOf(b)).setX((int) r.getX() - 10);
                            bulletArray.get(bulletArrayHitboxes.indexOf(b)).setXVel(bulletArray.get(bulletArrayHitboxes.indexOf(b)).getXVel() * -1);
                        }
                    }
                    //upper or lower edge of wall rectangle
                    if(b.getX() > r.getX() && b.getX() < r.getX() + r.getWidth()){
                        //lower, then upper edge
                        if(b.getY() >= r.getY() + r.getHeight() - 5){
                            bulletArray.get(bulletArrayHitboxes.indexOf(b)).setY((int) (r.getY() + r.getHeight() + 5));
                            bulletArray.get(bulletArrayHitboxes.indexOf(b)).setYVel(bulletArray.get(bulletArrayHitboxes.indexOf(b)).getYVel() * -1);
                        } else if(b.getY() <= r.getY() + 10){
                            bulletArray.get(bulletArrayHitboxes.indexOf(b)).setY((int) (r.getY() - 10));
                            bulletArray.get(bulletArrayHitboxes.indexOf(b)).setYVel(bulletArray.get(bulletArrayHitboxes.indexOf(b)).getYVel() * -1);
                        }
                    }
                    //right edge of wall rectangle
                    if(b.getX() > r.getX() + r.getWidth() - 5){
                        if(b.getY() > r.getY() && b.getY() < r.getY() + r.getHeight()){
                            bulletArray.get(bulletArrayHitboxes.indexOf(b)).setX((int) (r.getX() + r.getWidth() + 5));
                            bulletArray.get(bulletArrayHitboxes.indexOf(b)).setXVel(bulletArray.get(bulletArrayHitboxes.indexOf(b)).getXVel());
                        }
                    }
                }
            }
        }

        for(Rectangle r : wallHitboxes){
            for(Rectangle b : bulletArray1Hitboxes){
                if(b.intersects(r)){
                    //left edge of wall rectangle
                    if(b.getX() < r.getX() + 10){
                        if(b.getY() > r.getY() && b.getY() < r.getY() + r.getHeight()){
                            bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).setX((int) r.getX() - 10);
                            bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).setXVel(bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).getXVel() * -1);
                        }
                    }
                    //upper or lower edge of wall rectangle
                    if(b.getX() > r.getX() && b.getX() < r.getX() + r.getWidth()){
                        //lower, then upper edge
                        if(b.getY() >= r.getY() + r.getHeight() - 5){
                            bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).setY((int) (r.getY() + r.getHeight() + 10));
                            bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).setYVel(bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).getYVel() * -1);
                        } else if(b.getY() <= r.getY() + 10){
                            bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).setY((int) (r.getY() - 10));
                            bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).setYVel(bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).getYVel() * -1);
                        }
                    }
                    //right edge of wall rectangle
                    if(b.getX() > r.getX() + r.getWidth() - 5){
                        if(b.getY() > r.getY() && b.getY() < r.getY() + r.getHeight()){
                            bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).setX((int) (r.getX() + r.getWidth() + 5));
                            bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).setXVel(bulletArray1.get(bulletArray1Hitboxes.indexOf(b)).getXVel());
                        }
                    }
                }
            }
        }

        for(Rectangle r : wallHitboxes){
            for(Tank t : tankArray){
                int currentIndex = tankArray.indexOf(t);
                if(currentIndex < tankHitboxes.size() && tankHitboxes.get(currentIndex).intersects(r)){
                    //left edge of wall rectangle
                    if(t.getX() < r.getX() + 5){
                        if(t.getY() > r.getY() && t.getY() < r.getY() + r.getHeight()){
                            t.setX((int) (r.getX() - 36));
                            System.out.println("left");

                        }
                    }
                    //upper or lower edge of wall rectangle
                    if(t.getX() > r.getX() && t.getX() < r.getX() + r.getWidth()){
                        //lower, then upper edge
                        if(t.getY() >= r.getY() + r.getHeight() - 15){
                            t.setY((int) (r.getY() + r.getHeight() - 8));
                            System.out.println("lower");
                        } else if(t.getY() <= r.getY() + 1){
                            t.setY((int) (r.getY() - 65));
                            System.out.println("upper");

                        }
                    }
                    //right edge of wall rectangle
                    if(t.getX() > r.getX() + r.getWidth() - 10){
                        if(t.getY() > r.getY() && t.getY() < r.getY() + r.getHeight()){
                            t.setX((int) (r.getX() + r.getWidth()));
                            System.out.println("right");

                        }
                    }
                    System.out.println(tankArray.get(currentIndex).getX());
                    System.out.println(tankArray.get(currentIndex).getY());

                }
            }
        }

        double angle1 = tankArray.get(0).getAngle();
        double angle2 = tankArray.get(1).getAngle();

        if (keys.contains(KeyEvent.VK_W))
            tankArray.get(0).forward();
        if (keys.contains(KeyEvent.VK_A)) {
            if (Math.abs(angle1) >= 360) {
                angle1 = 0;
            }
            angle1 -= 4;
            tankArray.get(0).setAngle(angle1);

        }
        if (keys.contains(KeyEvent.VK_S))
            tankArray.get(0).backward();
        if (keys.contains(KeyEvent.VK_D)) {
            if (Math.abs(angle1) >= 360) {
                angle1 = 0;
            }
            angle1 += 4;
            tankArray.get(0).setAngle(angle1);
        }

        if (keys.contains(KeyEvent.VK_UP)) {
            tankArray.get(1).forward();
        }
        if (keys.contains(KeyEvent.VK_LEFT)) {
            if (Math.abs(angle2) >= 360) {
                angle2 = 0;
            }
            angle2 -= 5;
            tankArray.get(1).setAngle(angle2);
        }
        if (keys.contains(KeyEvent.VK_RIGHT)) {
            if (Math.abs(angle2) >= 360) {
                angle2 = 0;
            }
            angle2 += 5;
            tankArray.get(1).setAngle(angle2);
        }
        if (keys.contains(KeyEvent.VK_DOWN)) {
            tankArray.get(1).backward();

        }
        if (keys.contains(KeyEvent.VK_SPACE)) //Fire a Bullet from blue tank when spacebar is pressed.
        {
            if (bulletArray.size() <= 10 && !bulletFired && !tankArray.get(0).getDestroyed()) {

                Bullet temp = new Bullet(5, (int) ((tankArray.get(0).getX() + 15) + 40 * Math.sin(Math.toRadians(tankArray.get(0).getAngle()))) , (int) ((tankArray.get(0).getY() + 25) - 50 * Math.cos(Math.toRadians(tankArray.get(0).getAngle()))), -tankArray.get(0).getAngle() + 90);
                bulletArray.add(temp);
                bulletFired = true;

            }

        }

        if (keys.contains(KeyEvent.VK_PERIOD)) //Fire a Bullet from red tank when period pressed.
        {
            if (bulletArray1.size() <= 10 && !bullet1Fired && !tankArray.get(1).getDestroyed()) {

                Bullet temp = new Bullet(5, (int) ((tankArray.get(1).getX() + 15) + 40 * Math.sin(Math.toRadians(tankArray.get(1).getAngle()))) , (int) ((tankArray.get(1).getY() + 25) - 50 * Math.cos(Math.toRadians(tankArray.get(1).getAngle()))), -tankArray.get(1).getAngle() + 90);
                bulletArray1.add(temp);
                bullet1Fired = true;

            }

        }

        if (bulletFired && framesElapsed <= 5) {
            framesElapsed++;
        } else {
            bulletFired = false;
            framesElapsed = 0;
        }

        if (bullet1Fired && framesElapsed1 <= 5) {
            framesElapsed1++;
        } else {
            bullet1Fired = false;
            framesElapsed1 = 0;
        }


        if(tankArray.get(0).getDestroyed() || tankArray.get(1).getDestroyed()){

            deathTimer.update(delta);

            if(deathTimer.isDone()){
                deathTimer.reset();

                if(tankArray.get(0).getDestroyed() && tankArray.get(1).getDestroyed()){
                    draws++;
                } else if(tankArray.get(0).getDestroyed()){
                    redWins++;
                } else {
                    blueWins++;
                }

                for(Tank t : tankArray){
                    t.setDestroyed(false);
                }
                tankArray.get(0).setX(32);
                tankArray.get(0).setY(32);
                tankArray.get(1).setX(672);
                tankArray.get(1).setY(672);


                bulletArray.clear();
                bulletArray1.clear();
                bulletArrayHitboxes.clear();
                bulletArray1Hitboxes.clear();

            }
        }




        //General Text (Draw this last to make sure it's on top.)
        g.setColor(Color.BLACK);

        g.drawString("Controls: ", 800, 60);
        g.drawString("Blue: WASD + Space",800,80);
        g.drawString("Red: Arrow Keys + Period",800,100);
        g.drawString("Frame Number: " + frameNumber, 800, 160);

        if (f3Pressed){
            g.drawString("Hitboxes Shown (F3)", 800, 200);
        } else {
            g.drawString("Hitboxes Not Shown (F3)", 800, 200);
        }
        g.setFont(new Font(g.getFont().getFontName(),Font.BOLD, 20));
        g.drawString("TroubledTanks 2021", 780, 24);
        g.drawString("Blue Deaths: " + blueDeaths, 192, 900);
        g.drawString("Red Deaths: " + redDeaths, 576, 900);
        g.drawString("Blue Wins: " + blueWins, 192, 950);
        g.drawString("Red Wins: " + redWins, 576, 950);
        g.drawString("Draws: " + draws, 384, 925);





    }//--end of renderFrame method--

    //-------------------------------------------------------
    //Respond to Mouse Events
    //-------------------------------------------------------
    public void mouseClicked(MouseEvent e) {
    }

    //-------------------------------------------------------
    //Respond to Keyboard Events
    //-------------------------------------------------------
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        if(c == KeyEvent.VK_F3){
            f3Pressed = true;
        }

    }


    public void keyPressed(KeyEvent e) {
        int v = e.getKeyCode();

        if (!keys.contains(e.getKeyCode())) {
            keys.add(e.getKeyCode());
        }


    }

    public void keyReleased(KeyEvent e) {
        int v = e.getKeyCode();

        if (keys.contains(e.getKeyCode())) {
            keys.remove(keys.indexOf(e.getKeyCode()));
        }

    }


    //-------------------------------------------------------
    //Initialize Graphics
    //-------------------------------------------------------
//-----------------------------------------------------------------------
/*  Image section... 
 *  To add a new image to the program, do three things.
 *  1.  Make a declaration of the Image by name ...  Image imagename;
 *  2.  Actually make the image and store it in the same directory as the code.
 *  3.  Add a line into the initGraphics() function to load the file. 
//-----------------------------------------------------------------------*/
//    Image ballImage;
//    Image starImage;
//    Image smallTank;
//    Image biggerTank;
//    Image redBiggerTank;
    Image biggerTankCropped;
    Image redBiggerTankCropped;


    public void initGraphics() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

//        ballImage = toolkit.getImage("src/images/ball.gif");
//        starImage = toolkit.getImage("src/images/star.gif");
//        smallTank = toolkit.getImage("src/images/Tank.png");
//        biggerTank = toolkit.getImage("src/images/TankBigger.png");
//        redBiggerTank = toolkit.getImage("src/images/RedTankBigger.png");
        biggerTankCropped = toolkit.getImage(getClass().getClassLoader().getResource("TankBiggerCropped.png"));
        redBiggerTankCropped = toolkit.getImage(getClass().getClassLoader().getResource("redTankBiggerCropped.png"));

    } //--end of initGraphics()--

    //-------------------------------------------------------
    //Initialize Sounds
    //-------------------------------------------------------
//-----------------------------------------------------------------------
/*  Music section... 
 *  To add music clips to the program, do four things.
 *  1.  Make a declaration of the AudioClip by name ...  AudioClip clipname;
 *  2.  Actually make/get the .wav file and store it in the same directory as the code.
 *  3.  Add a line into the initMusic() function to load the clip. 
 *  4.  Use the play(), stop() and loop() functions as needed in your code.
//-----------------------------------------------------------------------*/
    Clip themeMusic;
    Clip bellSound;

//    public void initMusic()
//    {
//        themeMusic = loadClip("src/audio/under.wav");
//        bellSound = loadClip("src/audio/ding.wav");
//        if(themeMusic != null)
////            themeMusic.start(); //This would make it play once!
//            themeMusic.loop(2); //This would make it loop 2 times.
//    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}//--end of ArcadeDemo class--

