package environment;


import gameCommons.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;
import gameCommons.IFrog;

import java.util.ArrayList;

public class Environment implements IEnvironment {

    private Game game;
    private ArrayList<Lane> voies;
    private ArrayList<River> rivers;
    private IFrog frog;

    public Environment(Game game, IFrog frog) {
        this.game = game;
        this.voies = new ArrayList<>();
        this.rivers = new ArrayList<>();
        this.frog = frog;
        boolean leftToRight = this.game.randomGen.nextBoolean();
        if (!game.getGraphic().getInfinity()){
            for(int i = 0; i<this.game.height; i++){
                if(i == 0 || i == this.game.height-1)
                    voies.add(new Lane(game, i, 0.0D));
                else if(i == (this.game.height/2) - 1 || i == (this.game.height/2)) {
                    rivers.add(new River(game, i, 0.2D, leftToRight));
                    leftToRight = !leftToRight;
                    voies.add(new Lane(game, i, 0.0D));
                }
                else
                    voies.add(new Lane(game, i, this.game.defaultDensity));
            }
        }
        else {
            for(int i = 0; i<this.game.height; i++){
                voies.add(new Lane(game, i, this.game.defaultDensity));
            }
        }
    }


    public boolean isSafe(Case c) {
        boolean safeRiver = true;
        for(River river : rivers){
            if(!river.isSafe(c)) {
                safeRiver = false;
                break;
            }
        }

        boolean safeLane = true;
        for (Lane lane:voies){
            if (!lane.isSafe(c)){
                safeLane = false;
                break;
            }
        }
        return safeLane && safeRiver;
        //return this.voies.get(c.ord).isSafe(c) && safeRiver;
    }

    public boolean isWinningPosition(Case c) {
        return c.ord == this.game.height - 1;
    }

    public boolean isOut(Case c){
        return !(c.absc <= this.game.width-1 && c.absc >= 0);
    }

    public void update() {

        for (Lane lane : this.voies) {
            lane.update();
        }

        for (River river : rivers) {
            river.setFrog(frog.getPosition());
            river.update();
        }

    }

    public ArrayList<Lane> getLanes(){
        return this.voies;
    }

}