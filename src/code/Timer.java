package code;

public class Timer {
    private long timer;
    private long goal;

    public Timer(long g){
        goal = g;

    }

    public void update(long delta){
        timer += delta;


    }

    public boolean isDone(){
        return timer >= goal;
    }

    public void reset(){
        timer = 0;
    }




}
