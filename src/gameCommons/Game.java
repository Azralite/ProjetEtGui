package gameCommons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import environment.Lane;
import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;

public class Game {

    public final Random randomGen = new Random();

    // Caracteristique de la partie
    public final int width;
    public final int height;
    public final int minSpeedInTimerLoops;
    public final double defaultDensity;

    public double temps = 0;

    // Lien aux objets utilisés
    private IEnvironment environment;
    private IFrog frog;
    private IFroggerGraphics graphic;

    /**
     *
     * @param graphic
     *            l'interface graphique
     * @param width
     *            largeur en cases
     * @param height
     *            hauteur en cases
     * @param minSpeedInTimerLoop
     *            Vitesse minimale, en nombre de tour de timer avant déplacement
     * @param defaultDensity
     *            densite de voiture utilisee par defaut pour les routes
     */
    public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
        this.graphic = graphic;
        this.width = width;
        this.height = height;
        this.minSpeedInTimerLoops = minSpeedInTimerLoop;
        this.defaultDensity = defaultDensity;
    }

    /**
     * Lie l'objet frog à la partie
     *
     * @param frog
     */
    public void setFrog(IFrog frog) {
        this.frog = frog;
    }

    /**
     * Lie l'objet environment a la partie
     *
     * @param environment
     */
    public void setEnvironment(IEnvironment environment) {
        this.environment = environment;
    }

    /**
     *
     * @return l'interface graphique
     */
    public IFroggerGraphics getGraphic() {
        return this.graphic;
    }



    /**
     * Teste si la partie est gagnee et lance un écran de fin approprié si tel
     * est le cas
     *
     * @return true si la partie est gagnée
     */
    public boolean testWin() {
        if (this.environment.isWinningPosition(this.frog.getPosition())) {
            this.graphic.endGameScreen("You Win", temps, false);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Teste si la partie est perdue et lance un écran de fin approprié si tel
     * est le cas
     *
     * @return true si le partie est perdue
     */
    public boolean testLose() {
        if (!this.environment.isSafe(this.frog.getPosition()) || this.environment.isOut(this.frog.getPosition())) {
            this.graphic.endGameScreen("You Lose. Score = " + (this.frog.getLanePos() -1 ),0, false);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Actualise l'environnement, affiche la grenouille et verifie la fin de
     * partie.
     */
    public void update() {
        this.graphic.clear();
        this.environment.update();
        this.graphic.add(new Element(this.frog.getPosition(), this.frog.getImage()));
        this.testLose();
        this.testWin();
        temps += 0.1;
    }

    public void move_lanes(int n){
        ArrayList<Lane> voies = this.environment.getLanes();
        for(Lane l : voies){
            l.setOrd(l.getOrd() + n);
        }


        /*for(int i = 0; i<voies.size(); i++){
            Lane l = voies.get(i);
            l.setOrd(l.getOrd() + n);
            voies.set(i, l);
        }*/
        voies.add(new Lane(this, this.height-1, this.defaultDensity));
    }



}