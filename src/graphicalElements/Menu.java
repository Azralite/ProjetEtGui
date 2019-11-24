package graphicalElements;

import javax.swing.*;

import environment.Environment;
import frog.Frog;
import gameCommons.*;

import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements IMenu  {
    private int width;
    private int height;
    private JPanel panMenu = new JPanel();
    private JPanel panFin = new JPanel();
    private JPanel panJeu;
    private JLabel icon = new JLabel(new ImageIcon("src/ressource/titre.png"));
    private int tempo;
    private int minSpeedInTimerLoops;
    private double defaultDensity;
    private double temps = 0;

    private IFroggerGraphics graphic;


    public Menu(int width, int height, int tempo, int minSpeedInTimerLoops, double defaultDensity){
        this.width = width;
        this.height = height;
        this.tempo = tempo;
        this.minSpeedInTimerLoops = minSpeedInTimerLoops;
        this.defaultDensity = defaultDensity;

        this.setTitle("Frogger");
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

        classique.addActionListener(new BoutonListener());
        infini.addActionListener(new Bouton2Listener());

        JPanel nord = nord();
        JPanel centre = centre(classique,infini);
        JPanel sud = sud();


        panMenu.setBackground(Color.GRAY);
        panMenu.setLayout(new BorderLayout());
        panMenu.add(nord,BorderLayout.NORTH);
        panMenu.add(centre, BorderLayout.CENTER);
        panMenu.add(sud,BorderLayout.SOUTH);

        this.getContentPane().add(panMenu);
//        this.pack();
        this.setVisible(true);
//        this.addKeyListener(this);
    }
    public void start(boolean infinity){
        //Création de l'interface graphique
        //IFroggerGraphics graphic = new FroggerGraphic(width, height,true);
        this.remove(panMenu);
        this.repaint();

        this.graphic = new  FroggerGraphic(width,height,true, this, this,  infinity);
        //Création de la partie
        Game game = new Game(graphic, width, height, minSpeedInTimerLoops, defaultDensity);
        //Création et liason de la grenouille
        IFrog frog = new Frog(game);
        game.setFrog(frog);
        graphic.setFrog(frog);
        //Création et liaison de l'environnement
        IEnvironment env = new Environment(game, frog);
        game.setEnvironment(env);
        //Boucle principale : l'environnement s'acturalise tous les tempo milisecondes
        Timer timer = new Timer(tempo, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.update();
                graphic.repaint();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }



    public void end(){
        System.out.println(0);
//        graphic.clear();
//        graphic.repaint();
        this.remove(this.panJeu);
        this.repaint();

        JButton replay = new JButton("Replay");
        JButton exit = new JButton("Exit");
        JButton son = new JButton("Couper le son");

        replay.addActionListener(new BoutonReplayListener());
        exit.addActionListener(new BoutonExitListener());

        panFin.setBackground(Color.GRAY);
        panFin.setLayout(new BorderLayout());

        JPanel nord = nord();
        JPanel centre = centreFin(replay,exit);
        JPanel sud = sud();


        panMenu.setBackground(Color.GRAY);
        panMenu.setLayout(new BorderLayout());
        panMenu.add(nord,BorderLayout.NORTH);
        panMenu.add(centre, BorderLayout.CENTER);
        panMenu.add(sud,BorderLayout.SOUTH);

        this.getContentPane().add(panMenu);
//        this.pack();
        this.setVisible(true);
//        this.addKeyListener(this);
    }

    class BoutonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setVisible(false);
            start(false);
        }
    }

    class Bouton2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setVisible(false);
            start(true);
        }
    }

    class BoutonReplayListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            menuPanel();
        }
    }

    class BoutonExitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setVisible(false);
        }
    }

}

