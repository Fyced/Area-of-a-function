import javax.swing.*;

public class AreaRunner {

    public static void main(String[] args){
        final int height = 600;
        final int width = 600;
        
        JFrame window = new JFrame();
        window.setSize(width, height);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Screen mousePanel = new Screen();
        window.add(mousePanel);  // Añadimos el panel a la ventana
        
        window.setVisible(true);
    }
}
