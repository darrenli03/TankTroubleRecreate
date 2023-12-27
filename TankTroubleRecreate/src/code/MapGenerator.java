package code;

import java.util.*;

//Note: This code wasn't written by me, I just used it to test random map generation. It wasn't used in the game
public class MapGenerator {
    private static final String DEBUGTAG = "MapGenerator";

    private Random random = new Random();

    public static final int MAX_WIDTH = 10;
    public static final int MIN_WIDTH = 5;
    public static final int MAX_HEIGHT = 7;
    public static final int MIN_HEIGHT = 5;

    private static final int[] dirs = {8, 2, 1, 4};

    /*
    An integer array representing the map, where 0,0 is the top left corner.
     */
    private int[][] map;

    public void generate() {
        log("Generating new map! Seed: " + initRandom());

        int height = rangeInt(MIN_HEIGHT, MAX_HEIGHT);
        int width = rangeInt(MIN_WIDTH, MAX_WIDTH);
        log("Map width: " + width + ", height: " + height);

        map = new int[height][width];

        dfs(0, 0);
        System.out.println(dispMapStr());
    }

    /**
     * Map generation algorithm using randomized depth first search
     * @param x
     * @param y
     */
    private void dfs(int x, int y) {
        Deque<Cell> cells = new LinkedList<>();
        dfs(x, y, cells);
    }

    private void dfs(int x, int y, Deque<Cell> cells) {
        int randValid = randomValidDirection(x, y);
        if(randValid != -1) {
            map[y][x] = map[y][x] | 1 | randValid << 1;
            cells.addFirst(new Cell(x, y));
            switch(randValid) {
                case 1: // north
                    y -= 1;
                    break;
                case 2: // east
                    x += 1;
                    break;
                case 4: // south
                    y += 1;
                    break;
                case 8: // west
                    x -= 1;
                    break;
            }

            dfs(x, y, cells);
        } else {
            // backtrack
            map[y][x] = map[y][x] | 1;
            Cell c = cells.pollFirst();
            while(c != null && randomValidDirection(c.x, c.y) == -1)
                c = cells.pollFirst();
            if(c != null) {
                dfs(c.x, c.y, cells);
            }
        }
    }

    private void randomWalker(int x, int y) {
        int[] valid;

        while((valid = validDirections(x, y)).length > 0) {
            map[y][x] = map[y][x] | 1;
            int i = random.nextInt(valid.length);
            map[y][x] = map[y][x] | valid[i] << 1;
            switch(valid[i]) {
                case 1: // north
                    y -= 1;
                    break;
                case 2: // east
                    x += 1;
                    break;
                case 4: // south
                    y += 1;
                    break;
                case 8: // west
                    x -= 1;
                    break;
            }
        }
    }

    /**
     * Direction legend
     * 1 - North
     * 2 - East
     * 4 - South
     * 8 - WEST
     * @param x curPos[x]
     * @param y curPos[y]
     * @return
     */
    private int[] validDirections(int x, int y) {
        int[] a = new int[4];
        int cnt = 0;
        for(int r = 0; r < 4; r++) {
            int tX = x;
            int tY = y;
            if(r > 1) tY += (r % 2) == 0 ? -1 : 1;
            else tX += (r % 2) == 0 ? -1 : 1;

            if(!inBounds(tX, tY)) continue;
            if(isPassed(tX, tY)) continue;
            a[cnt++] = dirs[r];
        }

        int[] dest = new int[cnt];

        System.arraycopy(a, 0, dest, 0, cnt);
        return dest;
    }

    private int randomValidDirection(int x, int y) {
        int[] a = validDirections(x, y);
        if(a.length == 0) {
            return -1;
        }
        return a[random.nextInt(a.length)];
    }

    private boolean isPassed(int x, int y) {
        return (map[y][x] & 1) == 1;
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < map[0].length && y >= 0 && y < map.length;
    }

    public long initRandom() {
        long seed = random.nextLong();
        random = new Random(seed);
        return seed;
    }

    private int rangeInt(int min, int max) {
        min = Math.min(min, max);
        max = Math.max(min, max) + 1;

        return random.nextInt(max - min) + min;
    }

    private void log(String msg) {

        System.out.println(DEBUGTAG + ": " + msg);

    }

    private String dispMapStr() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                int a = map[i][j];
                switch(a) {
                    case 3:
                        stringBuilder.append("↑");
                        break;
                    case 5:
                        stringBuilder.append("→");
                        break;
                    case 9:
                        stringBuilder.append("↓");
                        break;
                    case 17:
                        stringBuilder.append("←");
                        break;
                    default:
                        if((a | 1) == 1)
                            stringBuilder.append("+");
                        else
                            stringBuilder.append("·");
                        break;
                }

                stringBuilder.append(" ");
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        MapGenerator generator = new MapGenerator();
        generator.generate();
        System.out.println(generator.dispMapStr());
    }
}
