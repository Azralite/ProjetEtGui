package graphicalElements;

import gameCommons.Direction;
import gameCommons.IFrog;
import jaco.mp3.player.MP3Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;


public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
    private ArrayList<Element> elementsToDisplay;
    private int pixelByCase = 32;
    private int width;
    private int height;
    private IFrog frog;
    private JFrame frame;
    private JFrame endFrame;
    public boolean end = false;
    private Image image;
    private Image backG;
    private boolean infinity;
    private JLabel icon = new JLabel(new ImageIcon("src/ressource/titre.png"));
    private Menu menu;
    private MP3Player player;



    public FroggerGraphic(int width, int height, boolean musique, JFrame frame, Menu a, boolean inf) {
        this.width = width;
        this.height = height;
        elementsToDisplay = new ArrayList<>();
        this.menu = a;
        this.infinity = inf;

        if (musique){
            player = new MP3Player();

            player.setRepeat(true);

            player.addToPlayList(new File("src/ressource/music.mp3"));
            player.play();
        }



        ImageIcon ii = new ImageIcon("src/ressource/route3.png");
        this.image = ii.getImage();
        this.image = this.image.getScaledInstance(pixelByCase,pixelByCase,Image.SCALE_REPLICATE);

        this.backG = new ImageIcon("src/ressource/damier.png").getImage();
        this.backG = this.backG.getScaledInstance(pixelByCase,pixelByCase,Image.SCALE_REPLICATE);

        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dim.width/2) - (width*pixelByCase)/2, (dim.height/2)-(height*pixelByCase/2));
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(this);
        //drawBackground();
    }

    public boolean getInfinity(){return infinity;}

    public void drawBackground(Graphics g){
        if (!infinity) {
            for (int a = 0; a < width * pixelByCase; a += pixelByCase) {
                g.drawImage(backG, a, 0, this);
                //g.drawImage(backG,a,height-pixelByCase,this);
            }
            for (int i = pixelByCase; i < (height - 1) * pixelByCase; i += pixelByCase) {
                for (int j = 0; j < width * pixelByCase; j += pixelByCase) {
                    g.drawImage(image, j, i, this);
                }
            }
        }
        else {
            for (int i = 0; i < (height) * pixelByCase; i += pixelByCase) {
                for (int j = 0; j < width * pixelByCase; j += pixelByCase) {
                    g.drawImage(image, j, i, this);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        for (Element e : elementsToDisplay) {
//            g.setColor(e.color);
//            g.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);
            g.drawImage(e.image,pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord),this);
        }
    }

//    public int getWidth(){
//        return this.width;
//    }
//
//    public int getHeight(){
//        return this.height;
//    }
    public int getPixelByCase(){
        return this.pixelByCase;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (infinity){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    frog.moveInfinity(Direction.up);
                    break;
                case KeyEvent.VK_DOWN:
                    frog.moveInfinity(Direction.down);
                    break;
                case KeyEvent.VK_LEFT:
                    frog.moveInfinity(Direction.left);
                    break;
                case KeyEvent.VK_RIGHT:
                    frog.moveInfinity(Direction.right);
            }
        }
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    frog.move(Direction.up);
                    break;
                case KeyEvent.VK_DOWN:
                    frog.move(Direction.down);
                    break;
                case KeyEvent.VK_LEFT:
                    frog.move(Direction.left);
                    break;
                case KeyEvent.VK_RIGHT:
                    frog.move(Direction.right);
            }
        }
    }

    public void clear() {
        this.elementsToDisplay.clear();
    }

    public void add(Element e) {
        this.elementsToDisplay.add(e);
    }

    public void setFrog(IFrog frog) {
        this.frog = frog;
    }

    public void endGameScreen(String s, double temps, int score,  boolean inf, boolean win) {
        player.stop();
        Integer a = (int)temps;
        String time = a.toString();
        this.end = true;
        frame.remove(this);

        frame.setVisible(false);

        endFrame = new JFrame("Frogger");
        endFrame.setSize(width * 32, height*32);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        endFrame.setLocation((dim.width/2) - (width*32)/2, (dim.height/2)-(height*32/2));


        //Logo
        JPanel nord = new JPanel();
        //nord.setBackground(Color.GRAY);
        nord.add(icon, BorderLayout.CENTER);

        //Choix pour restart
        JPanel choix = new JPanel();
        choix.setLayout(new GridLayout(1,2,1,0));
        JButton replay = new JButton("Replay");
        JButton exit = new JButton("Exit");
        replay.addActionListener(new ButonReplayListener());
        exit.addActionListener(new ButonExitListener());
        choix.add(replay, BorderLayout.WEST);
        choix.add(exit,BorderLayout.EAST);

        //Credit
        JPanel sud = new JPanel();
        JLabel credit = new JLabel(" Merci d'avoir joué \n Fait par Gaëtan Serre et Paul Michel dit Ferrer");
        sud.add(credit);

        //Score
        JPanel scorePan = new JPanel();
        JLabel label = new JLabel(s);
        if (inf){
            label.setText("Score : " + score);
        }else {
            if (win){
                label.setText(s + " \n Vous avez mis :" + time + " s");
            }
            else {
                label.setText(s);
            }
        }
        label.setFont(new Font("Verdana", 1, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        scorePan.add(label, BorderLayout.CENTER);

        JPanel centre = new JPanel();
        centre.setLayout(new GridLayout(2,1,0,5));
        centre.setPreferredSize(new Dimension(width*32/2,height));
        centre.add(scorePan, BorderLayout.NORTH);
        centre.add(choix, BorderLayout.SOUTH);


        endFrame.setBackground(Color.BLUE);
        endFrame.getContentPane().add(nord, BorderLayout.NORTH);
        endFrame.getContentPane().add(centre, BorderLayout.CENTER);
        endFrame.getContentPane().add(sud,BorderLayout.SOUTH);
        endFrame.setVisible(true);
    }


    public boolean getEnd(){
        return this.end;
    }

    class ButonReplayListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            endFrame.dispose();
            menu.replay();
        }
    }

    class ButonExitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }


}