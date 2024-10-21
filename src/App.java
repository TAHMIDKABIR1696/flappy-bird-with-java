import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int BWidth = 864;
        int BHeight = 768;
        //create the frame for the game:
        JFrame frame = new JFrame("Flappy Bird");
        //frame.setVisible(true);
        frame.setSize(BWidth, BHeight);//frame size
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);//frame is unsizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit button

        FB fb = new FB();
        frame.add(fb);
        frame.pack();//not to include title bar
        fb.requestFocus();
        frame.setVisible(true);

    
    }
}
