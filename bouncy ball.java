import java.util.ArrayList;
import java.util.Arrays;

class Ball {
    static public ArrayList<Ball> balls = new ArrayList<Ball>();
    int[] pos = { 0, 0 };
    double[] energy = { 0.0, 0.0 };
    int height = 1;
    int width = 1;
    boolean moved = false;

    public void giveEnergy(int x, int y) {
        energy[0] += x;
        energy[1] += y;
    }

    public int getPosBottom() {
        return pos[1] + height - 1;
    }

    public int getPosLeft() {
        return pos[0];
    }

    public int getPosRight() {
        return pos[0] + width - 1;
    }

    public int getPosTop() {
        return pos[1];
    }

    public boolean move() {
        int[] after = pos.clone();
        /*
         * energy= 0, 0
         * pos=0, 1
         * X X X X X
         * X X X X X
         * X X X X X
         * X X X X X
         * 
         */
        if (energy[0] > 0) {
            pos[0] += 1;
            energy[0] -= 1;
            moved = true;
        } else if (energy[0] < 0) {
            pos[0] -= 1;
            energy[0] += 1;
            moved = true;
        }

        if (energy[0] > -1 && energy[0] < 1) {
            energy[0] = 0.0;
        }
        if (energy[1] > -1 && energy[1] < 1) {
            energy[1] = 0.0;
        }

        if (after[0] == pos[0] && after[1] == pos[1]) {
            return false;
        } else {
            return true;
        }

    }

    public String describe() {
        return "pos: " + pos[0] + "," + pos[1] + "," + "\nenergy: " + energy[0]
                + "," + energy[1];
    }

    public Ball() {
        balls.add(this);
        System.out.println("balls array: " + balls.toString());
    }
}

class Board {
    Object[][] data = new Object[5][5];

    void checkForBounce(Ball ball) {

        System.out.println(ball.getPosLeft()+" "+ball.getPosRight()+" "+ball.getPosTop()+" "+ball.getPosBottom());

        if (ball.getPosRight() == data[0].length-1 && ball.energy[0] > 0) {
            ball.energy[0] *= -.5;
        } else if (ball.getPosLeft() == 0 && ball.energy[0] < 0) {
            ball.energy[0] *= -.5;
        }
        if (ball.getPosTop() == data.length-1 && ball.energy[1] > 0) {
            ball.energy[1] *= -.5;
        } else if (ball.getPosBottom() == 0 && ball.energy[1] < 0) {
            ball.energy[1] *= -.5;
        }
    }

    public void update() {
        for (int outerArray = 0; outerArray < data.length; outerArray++) {
            for (int itemIndex = 0; itemIndex < data[outerArray].length; itemIndex++) {
                Object item = data[outerArray][itemIndex];
                if (item != null) {
                    // System.out.println(""+outerArray+","+itemIndex+", "+item);
                    Ball obj = (Ball) item;

                    if (!obj.moved) {
                        checkForBounce(obj);
                        if (obj.move()) {
                            data[obj.pos[0]][obj.pos[1]] = obj;
                            data[outerArray][itemIndex] = null;
                        }
                    }

                }
            }
        }
        for (Ball ball : Ball.balls) {
            ball.moved = false;
        }

    }

    public String toString() {
        String result = "";
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                for (Ball ball : Ball.balls) {
                    int ballMaxY = ball.getPosBottom();
                    int ballMaxX = ball.getPosRight();
                    if (y >= ball.pos[1] &&
                            y <= ballMaxY &&
                            x >= ball.pos[0] &&
                            x <= ballMaxX) {
                        result += "0";
                    } else {
                        result += "X";
                    }
                }
            }
            result += "\n";
        }

        // for (int i = 0; i < data.length; i++) {
        // for (int o = 0; o < data[i].length; o++) {
        // if (data[i][o] == null) {
        // result += "0";
        // } else {
        // result += "X";
        // }
        // }
        // result += "\n";

        // }
        return result;
    }

    Board() {
    }
}

class Main {
    public static void main(String args[]) {
        boolean debug = true;

        Board board = new Board();
        Ball ball = new Ball();
        board.data[0][0] = ball;
        ball.giveEnergy(9, 7);
        System.out.println(board);
        while (true) {

            board.update();
            System.out.println(board);

            for (Ball item : Ball.balls) {
                if (item.pos[1] != board.data.length - 1) {
                    item.giveEnergy(0, 1);
                }
                if (item.pos[1] == board.data.length - 1) {
                    item.giveEnergy(0, -10);
                }
            }
            

            if (debug) {
                System.out.println(ball.describe());
                System.out.println("Ended");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    }
}