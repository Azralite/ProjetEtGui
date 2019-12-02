package environment;

import gameCommons.Case;
import gameCommons.Game;

import java.util.ArrayList;

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


    public void update() {

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

    /**
     * Renvoie la première case
     * @return 0 si l'on va de la gauche vers la droite
     * @return game.width -1 sinon
     */
    private Case getFirstCase() {
        if (leftToRight) {
            return new Case(0, ord);
        } else
            return new Case(game.width - 1, ord);
    }

    /**
     *Renvoie la case avant la première cases
     * @return -1 si l'on va de la gauche vers la droite
     * @return game.width sinon
     */
    private Case getBeforeFirstCase() {
        if (leftToRight) {
            return new Case(-1, ord);
        } else
            return new Case(game.width, ord);
    }

    /**
     * Renvoie l'ordonné de la ligne
     * @return this.ord
     */
    public int getOrd(){
        return this.ord;
    }

    /**
     * Defini l'ordonné de la ligne
     * @param n le nouvel indice de ligne
     */
    public void setOrd(int n){this.ord = n;}

}