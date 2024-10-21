//JPanel:
import java.awt.*;//package contains classes for creating user interfaces and for painting graphics
import java.awt.event.*;//Event Handling
import java.util.ArrayList;//store all the pipes
import java.util.Random;// position the pipes
import javax.swing.*;
// flappy bird class is inheriting JPanel class
public class FB extends JPanel implements ActionListener, KeyListener{
    int BWidth = 864;
    int BHeight = 768;

    //Variable for Images:
    Image bgImage;
    Image birdImage;
    Image bottomImage;
    Image topImage;
    //Bird:
    int Xbird = BWidth/8;
    int Ybird = BHeight/2;
    int birdWidth = 51;
    int birdHeight = 36;

    //pipe
    int xpipe = BWidth;
    int ypipe = 0;
    int pipwidth = 78;
    int pipeheight = 560;

    class Pipe{
        int x = xpipe;
        int y = ypipe;
        int width = pipwidth;
        int height = pipeheight;
        Image img;
        boolean passed = false; //it will check if the bird pass the pipe or not

        Pipe(Image img) {
            this.img = img;

        }
    }


    class Bird{
        int x = Xbird;
        int y =  Ybird;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img =  img;
        }
    }
    //Game Logic
    Bird bird;
    int Xvelocity = -4; //pipe will move left -4 pex
    int Yvelocity = 0; //bird will 6 pex upward
    int gravity = 1; //bird will go 1 pex downward

    ArrayList<Pipe> pipes;
    Random random = new Random();//randomly placeing the pipes

    Timer gameLoop;
    Timer pipeplacesTimer;
    
    boolean restart = false;

    double score = 0;

    FB() {
        setPreferredSize(new Dimension(BWidth, BHeight));//canvas
        //setBackground(Color.gray);// bg color
        setFocusable(true);
        addKeyListener(this);// it will check the key function
        //load the image:
        //getClass = Flappy bird class
        //getResource = the src folder
        bgImage = new ImageIcon(getClass().getResource("./bg.png")).getImage(); 
        birdImage = new ImageIcon(getClass().getResource("./bird.png")).getImage();
        bottomImage = new ImageIcon(getClass().getResource("./bottom.png")).getImage();
        topImage = new ImageIcon(getClass().getResource("./top.png")).getImage();

        bird = new Bird(birdImage);
        pipes = new ArrayList<Pipe>(); 

        pipeplacesTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                pipeplace();
            }
        });
        pipeplacesTimer.start(); 

        gameLoop = new Timer(1000/60, this); //1000/60 = 60 frames per sec, 1000ms = 1s 
        gameLoop.start();
    }
    //pipe placement
    public void pipeplace(){
        int randomYpipe = (int) (ypipe - pipeheight/3 - Math.random()*(pipeheight/2));
        int space = BHeight/3;
        Pipe toppipe = new Pipe(topImage);
        toppipe.y = randomYpipe;
        pipes.add(toppipe); 

        Pipe bottompipe = new Pipe(bottomImage);
        bottompipe.y = toppipe.y + pipeheight + space;
        pipes.add(bottompipe);
    }
    //you want to perform custom drawing, such as drawing shapes, text, or images.
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        //background Image
        g.drawImage(bgImage, 0, 0, BWidth, BHeight, null);
        
        //bird 
        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);

        //pipe
        for (int i = 0; i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.red);
        g.setFont(new Font("Cabin", Font.PLAIN, 40));
        if (restart){
            g.drawString("GAME OVER  " + String.valueOf((int)score), 20,40);
        }

        else {
            g.drawString(String.valueOf((int)score), 20,40);
        }



    }
    //bird
    public void move() {
        Yvelocity += gravity;
        bird.y += Yvelocity;
        bird.y = Math.max(bird.y, 0); //bird will stop at the top

    //pipe
    for (int i = 0; i< pipes.size(); i++){
        Pipe pipe = pipes.get(i);
        pipe.x += Xvelocity;

        if (!pipe.passed && bird.x>pipe.x + pipe.width) {
            pipe.passed = true;
            score += 1;
        }

        if (collision(bird, pipe)){
            restart = true;
        }
        }

        if (bird.y > BHeight) {
            restart = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
         return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (restart) {
            pipeplacesTimer.stop();
            gameLoop.stop(); 
        }
    }
    
    @Override//any kind of key
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            Yvelocity = -6;
            if (restart) {
                bird.y = Ybird;
                Yvelocity = 0;
                pipes.clear();
                score = 0;
                restart = false;
                gameLoop.start();
                pipeplacesTimer.start(); 
            }
        }

        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override //type on a key that has character
    public void keyTyped(KeyEvent e) {}
}
    

