package environment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class River {
    private Game game;
    private int ord;
    private int speed;
    private ArrayList<Wood> woods = new ArrayList<>();
    private boolean leftToRight;
    private double density;
    private int timer = 0;
    private Case frog;
    private Image image;

    public River(Game game, int ord, double density, boolean leftToRight) {
        this.game = game;
        this.ord = ord;
        this.density = density;
        this.speed = this.game.randomGen.nextInt(this.game.minSpeedInTimerLoops) + 1;
        this.leftToRight = leftToRight;
        ImageIcon ii = new ImageIcon("src/ressource/eau.png");
        this.image = ii.getImage();
        this.image = this.image.getScaledInstance(game.getGraphic().getPixelByCase(),game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
    }

    // TODO : Constructeur(s)

    public void update() {

        // TODO

        // Toutes les voitures se déplacent d'une case au bout d'un nombre "tic
        // d'horloge" égal à leur vitesse
        // Notez que cette méthode est appelée à chaque tic d'horloge

        // Les voitures doivent etre ajoutes a l interface graphique meme quand
        // elle ne bougent pas

        // A chaque tic d'horloge, une voiture peut être ajoutée


        for(int i = 0; i<this.game.width; i++){
            this.game.getGraphic().add(new Element(i, this.ord, this.image));
        }

        this.timer++;
        if (this.timer <= this.speed) {
            this.moveWoods(false);
        } else {
            this.moveWoods(true);
            this.mayAddWood();
            this.timer = 0;
        }


    }

    // TODO : ajout de methodes


    public void setFrog(Case frog){ this.frog = frog; }

    private void moveWoods(boolean b) {

        for (Wood wood : this.woods) {
            wood.move(b, frog);
        }

        this.removeOldWood();
    }


    private void removeOldWood() {
        for(Wood c : woods){
            if (!c.appearsInBounds()) {
                this.woods.remove(c);
            }
        }
    }


    public boolean isSafe(Case pos) {
        for (Wood wood : this.woods) {
            if (wood.isOn(pos)) {
                return true;
            }
        }
        if(pos.ord == this.ord)
            return false;
        else
            return true;
    }




    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Ajoute une voiture au début de la voie avec probabilité égale à la
     * densité, si la première case de la voie est vide
     */
    private void mayAddWood() {
        if (!isSafe(getFirstCase()) && !isSafe(getBeforeFirstCase())) {
            if (game.randomGen.nextDouble() < density) {
                woods.add(new Wood(game, getBeforeFirstCase(), leftToRight));
            }
        }
    }

    private Case getFirstCase() {
        if (leftToRight) {
            return new Case(0, ord);
        } else
            return new Case(game.width - 1, ord);
    }

    private Case getBeforeFirstCase() {
        if (leftToRight) {
            return new Case(-1, ord);
        } else
            return new Case(game.width, ord);
    }
}