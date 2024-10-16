import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;


public class Screen extends JPanel {
    private static boolean isMousePressed = false;  //Variable that checks if the user is still pressing the click, used only to call the paint method
    private static boolean clearScreen = false;     // Variable that checks if it is needed to clear the screen, used only by that method
    private static int x;                           //Variable that stores the x coordinate of the mouse
    private static int y;                           //Variable that stores the y coordinate of the mouse
    private List<int[]> points = new ArrayList<>(); //Variable that stores all the points that are going to be used to calculate the area
    private int startingY = 0;                      //Variable that stores the coordinate on y of the first point of the function
    private double area = 0;                        // Total of area of the function. It keeps increasing due to the addition of each differential of areas

    //Constructor of the class that creates the mouse listener for when it is pressed and released, and when it moves.
    //It also creates the timer that is constantly running in order to paint the screen
    public Screen(){
        this.addMouseListener(new MouseAdapter() {
            @Override        //Method for when the mouse is pressed 
            public void mousePressed(MouseEvent e) {
                isMousePressed = true;    //It states that the mouse is being pressed
                clearScreen = true;       //It is needed to clear the screen before painting a new function
            }

            @Override       //Method for when the mouse is pressed 
            public void mouseReleased(MouseEvent e) {
                isMousePressed = false;
                if(area != 0){
                    System.out.printf("Area: %.4f cm^2\n", area/(37.8*37.8) );    //It prints the value of the area in cm^2 and only showing 4 decimals
                    area = 0;
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter(){
            @Override        //Method for when the mouse is moved 
            public void mouseDragged(MouseEvent e){
                x = e.getX();    //It stores the y coordinate of the mouse
                y = e.getY();    //It stores the x coordinate of the mouse
                if(isMousePressed){
                    if(points.isEmpty() || (points.get(points.size()-1))[0] <= x){
                        points.add(new int[] {x,y});    //It creates a new point when the mouse is pressed and when the x coordinate 
                                                        //is bigger than the x in the last point. To prevent having two y in one x
                    }
                }
            }
        });

        Timer timer = new Timer();        //Creation of the timer that is always running
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                if(isMousePressed){
                    //System.out.println(x + "\t" + y);
                    repaint();        //If the mouse is pressed, it calls method that paints the function
                }
            }
        }, 0, 1); // Executes every 0,001 seconds and without delay
    }

    @Override
    public void paintComponent(Graphics g){        //Method that paints the function
        super.paintComponent(g);
        g.setColor(Color.black);    //Color of the function in black

        if(clearScreen){
            points.clear();        //Removes all the previous points of the last function
            clearScreen = false;   //It changes the value because the screen has been cleared
        } else{
            int localX = 0;   //Stores the coordinates of the last point 
            int localY = 0;
            //Loop that draws every point stored previously
            for(int[] point: points){
                if(localX == localY && localX == 0){
                    //If it's the first point
                    g.fillRect(point[0], point[1], 3, 3);
                    startingY = point[1];    //States that this point is the point 0 of the reference system
                    area = 0;                //Eliminates the value of the area of the previous function
                } else {
                    //If its any point other than the first one
                    g.drawLine(localX, localY, point[0], point[1]);
                    //g.fillRect(point[0], point[1], 3, 3);
                    area += calculateArea(localX, localY, point[0], point[1]);    //It calls the method that calcultates the area for that differential of 
                                                                                  //the function
                }
                localX = point[0];
                localY = point[1];
            }
        }
    }

    //Method to calculate the area of every differential of area of the function
    public double calculateArea(int absoluteWidth1,int absoluteHeight1,int absoluteWidth2,int absoluteHeight2){
        int Area1 = (absoluteWidth2 - absoluteWidth1) * (startingY - absoluteHeight1);    //Area of the first height
        int Area2 = (absoluteWidth2 - absoluteWidth1) * (startingY - absoluteHeight2);    //Area of the second height
        double differentialOfArea = (Area1 + Area2)/2.0;           //Total of area of this differential
        return differentialOfArea;

    }

}
