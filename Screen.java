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
    private static boolean isMousePressed = false;
    private static boolean clearScreen = false;
    private static int x;
    private static int y;
    private List<int[]> points = new ArrayList<>();
    private int startingX = 0;
    private int startingY = 0;
    private double area = 0;

    public Screen(){

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isMousePressed = true;
                clearScreen = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isMousePressed = false;
            }
        });

        this.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                x = e.getX();
                y = e.getY();
                if(isMousePressed){
                    points.add(new int[] {x,y});
                }
            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                if(isMousePressed){
                    //System.out.println(x + "\t" + y);
                    repaint();
                }
            }
        }, 0, 1); // Ejecuta cada 0,001 segundo
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);

        if(clearScreen){
            points.clear();
            clearScreen = false;
            System.out.println("1:"+area);
            System.out.println("2:"+Area);
        } else{
            int localX = 0;
            int localY = 0;
            for(int[] point: points){
                if(localX == localY && localX == 0){
                    g.fillRect(point[0], point[1], 3, 3);
                    startingX = point[0];
                    startingY = point[1];
                    area = 0;
                } else {
                    g.drawLine(localX, localY, point[0], point[1]);
                    //g.fillRect(point[0], point[1], 3, 3);
                    area += calculateArea(localX, localY, point[0], point[1]);
                }
                localX = point[0];
                localY = point[1];
            }
        }
    }

    public double calculateArea(int absoluteWidth1,int absoluteHeight1,int absoluteWidth2,int absoluteHeight2){
        int Area1 = (absoluteWidth2 - absoluteWidth1) * (startingY - absoluteHeight1);
        int Area2 = (absoluteWidth2 - absoluteWidth1) * (startingY - absoluteHeight2);
        double differentialOfArea = (Area1 + Area2)/2.0;
        return differentialOfArea;

    }

}
