import java.util.ArrayList;
import java.util.Arrays;

class Ball {
    static public ArrayList<Ball> balls = new ArrayList<Ball>();
    int[] pos = { 0, 0 };
    int[] energy = { 0, 0 };
    boolean moved=false;

    public void giveEnergy(int x, int y) {
        energy[0] += y;
        energy[1] += x;

        


        
    }

    public boolean move() {
            int[] after = pos.clone();
            /*
            energy= 0, 0
            pos=0, 1
             0 X X X X 
             X X X X X
             X X X X X
             X X X X X
             X X X X X 
              
             */
        if (energy[0] > 0) {
            pos[0] += 1;
            energy[0] -= 1;
            moved=true;
        }
        else if(energy[0] < 0){
            pos[0]-=1;
            energy[0]+=1;
            moved=true;
        }

        if (energy[1] > 0) {
            energy[1] -= 1;
            pos[1] += 1;
            moved=true;
        }
        else if(energy[1]<0){
            pos[1]-=1;
            energy[1]+=1;
            moved=true;
        }
        if (after[0] == pos[0] && after[1] == pos[1]){
            return false;
        }
        else{
            return true;
        }
        
    }
    
    

    public String describe() {
        return "pos: " + pos[0] + "," + pos[1] + "," + "\nenergy: " + energy[0]
                + "," + energy[1];
    }

    public Ball() {
        balls.add(this);
        System.out.println("balls array: "+balls.toString());
    }
}

class Board {
    Object[][] data = new Object[5][5];

    void checkForBounce(Ball ball){
        if (ball.pos[0]==data[0].length-1 && ball.energy[0]>0){
            ball.energy[0]*=-1;
        }
        else if(ball.pos[0]==0 && ball.energy[0]<0){
            ball.energy[0]*=-1; 
        }
        if (ball.pos[1]==data.length-1 && ball.energy[1]>0){
            ball.energy[1]*=-1;
        }
        else if(ball.pos[1]==0 && ball.energy[1]<0){
            ball.energy[1]*=-1; 
        }
    }


    public void update() {
        for (int innerArray=0; innerArray < data.length; innerArray++) {
            for (int itemIndex=0; itemIndex < data[innerArray].length; itemIndex++) {
                Object item =data[innerArray][itemIndex];
                
                
                if (item != null) {
                    System.out.println(""+innerArray+","+itemIndex+", "+item);

                    Ball obj = (Ball) item;
                    
                    if (!obj.moved){
                    checkForBounce(obj);
                    if (obj.move()){
                    data[obj.pos[0]][obj.pos[1]]=obj;
                    data[innerArray][itemIndex]=null;
                }
                    }

                    
                }
            }
        }
        for (Ball ball : Ball.balls){
            ball.moved=false;
        }

       
        
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < data.length; i++) {
            for (int o = 0; o < data[i].length; o++) {
                if (data[i][o] == null) {
                    result += "0";
                } else {
                    result += "X";
                }
            }
            result += "\n";
        }
        return result;
    }

    Board() {
    }
}

class Main {
    
    public static void main(String args[]) {
        boolean debug= false;
        


        Board board = new Board();
        Ball ball = new Ball();
        board.data[0][0] = ball;
        ball.giveEnergy(9, 7);

        System.out.println(board);
        while (true) {
            
            
            board.update();
            System.out.println(board);
            for (Ball item : Ball.balls){
                item.giveEnergy(0, 1);
            }


if (debug){
            System.out.println(ball.describe());
            System.out.println("Ended");

        }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}