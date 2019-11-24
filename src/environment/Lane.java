package environment;

import java.util.ArrayList;
import gameCommons.Case;
import gameCommons.Game;

public class Lane {
    private Game game;
    private int ord;
    private int speed;
    private ArrayList<Car> cars = new ArrayList<>();
    private boolean leftToRight;
    private double density;
    private int timer = 0;

    public Lane(Game game, int ord, double density) {
        this.game = game;
        this.ord = ord;
        this.density = density;
        this.speed = this.game.randomGen.nextInt(this.game.minSpeedInTimerLoops) + 1;
        this.leftToRight = this.game.randomGen.nextBoolean();


        //Permet d'avoir des voitures deja sur le jeu
        for(int i = 0; i < game.width; ++i) {
            this.moveCars(true);
            this.mayAddCar();
        }

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

        this.timer++;
        if (this.timer <= this.speed) {
            this.moveCars(false);
        } else {
            this.moveCars(true);
            this.mayAddCar();
            this.timer = 0;
        }
        for (Car c : cars){
            c.setOrd(ord);
        }

    }

    // TODO : ajout de methodes


    private void moveCars(boolean b) {

        for (Car car : this.cars) {
            car.move(b);
        }

        this.removeOldCars();
    }


    private void removeOldCars() {
        for (Car c : cars) {
            if (!c.appearsInBounds()) {
                this.cars.remove(c);
            }
        }
    }


    public boolean isSafe(Case pos) {
        for (Car car : this.cars) {
            if (car.coversCase(pos)) {
                return false;
            }
        }

        return true;
    }



    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Ajoute une voiture au début de la voie avec probabilité égale à la
     * densité, si la première case de la voie est vide
     */
    private void mayAddCar() {
        if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
            if (game.randomGen.nextDouble() < density) {
                cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
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

    public int getOrd(){return this.ord;}

    public void setOrd(int n){this.ord = n;}

}