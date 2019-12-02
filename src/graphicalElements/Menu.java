package graphicalElements;

import environment.Environment;
import frog.Frog;
import gameCommons.Game;
import gameCommons.IEnvironment;
import gameCommons.IFrog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements IMenu  {
    private int width;
    private int height;
    private JPanel panMenu = new JPanel();
    private JLabel icon = new JLabel(new ImageIcon("src/ressource/titre.png"));
    private int tempo;
    private int minSpeedInTimerLoops;
    private double defaultDensity;
    public Timer timer;



    public Menu(int width, int height, int tempo, int minSpeedInTimerLoops, double defaultDensity){
        this.width = width;
        this.height = height;
        this.tempo = tempo;
        this.minSpeedInTimerLoops = minSpeedInTimerLoops;
        this.defaultDensity = defaultDensity;

        this.setTitle("Frogger");
        this.setResizable(false);
        this.setSize(width * 32, height*32);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2) - (width*32)/2, (dim.height/2)-(height*32/2));
        menuPanel();
    }


    public JPanel nord(){
        JPanel res = new JPanel();
        res.add(icon,BorderLayout.CENTER);
        return res;
    }

    public JPanel centre(JButton classique, JButton infini){
        JPanel res = new JPanel();
        res.setLayout(new GridLayout(2,1,0,5));
        res.setPreferredSize(new Dimension(width*32/2,height));
        res.add(classique, BorderLayout.NORTH);
        res.add(infini, BorderLayout.SOUTH);
        return res;
    }

    public JPanel centreFin(JButton replay, JButton exit){
        JPanel res = new JPanel();
        res.setLayout(new GridLayout(2,1,0,5));
        res.add(replay,BorderLayout.NORTH);
        res.add(exit, BorderLayout.SOUTH);
        return res;
    }

    public JPanel sud(){
        JPanel res = new JPanel();
        JLabel credit =new JLabel("Fait par Gaëtan Serre et Paul Michel dit Ferrer");
        res.add(credit, BorderLayout.CENTER);
//        res.add(son,BorderLayout.EAST);
        return res;

    }



    public void menuPanel(){

        JButton classique = new JButton("Mode Classique");
        JButton infini = new JButton("Mode Infini");
        JButton son = new JButton("Couper le son");

        classique.addActionListener(new BoutonPlayListener());
        infini.addActionListener(new BoutonPlayInfListener());

        JPanel nord = nord();
        JPanel centre = centre(classique,infini);
        JPanel sud = sud();


        panMenu.setBackground(Color.GRAY);
        panMenu.setLayout(new BorderLayout());
        panMenu.add(nord,BorderLayout.NORTH);
        panMenu.add(centre, BorderLayout.CENTER);
        panMenu.add(sud,BorderLayout.SOUTH);

        this.getContentPane().add(panMenu);
        this.setVisible(true);
    }


    public void start(boolean infinity){
        //Création de l'interface graphique
        this.remove(panMenu);
        this.repaint();

        IFroggerGraphics graphic = new  FroggerGraphic(width,height,true, this, this,  infinity);
        //Création de la partie
        Game game = new Game(graphic, width, height, minSpeedInTimerLoops, defaultDensity, this);
        //Création et liason de la grenouille
        IFrog frog = new Frog(game);
        game.setFrog(frog);
        graphic.setFrog(frog);
        //Création et liaison de l'environnement
        IEnvironment env = new Environment(game, frog);
        game.setEnvironment(env);
        //Boucle principale : l'environnement s'acturalise tous les tempo milisecondes
        this.timer = new Timer(tempo, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.update();
                graphic.repaint();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }


    public void replay(){
        new Menu(width, height, tempo, minSpeedInTimerLoops, defaultDensity);
    }


    class BoutonPlayListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setVisible(false);
            start(false);
        }
    }

    class BoutonPlayInfListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setVisible(false);
            start(true);
        }
    }


}

