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
                    System.out.println(x + "\t" + y);
                    repaint();
                }
            }
        }, 0, 1); // Ejecuta cada 1 segundo
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);

        if(clearScreen){
            points.clear();
            clearScreen = false;
        } else{
            for(int[] point: points){
            g.fillRect(point[0], point[1], 3, 3);
            }
        }
    }


}
