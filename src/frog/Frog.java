package frog;

import gameCommons.Case;
import gameCommons.Direction;
import gameCommons.Game;
import gameCommons.IFrog;
import graphicalElements.Element;

import javax.swing.*;
import java.awt.*;

public class Frog implements IFrog {

    private Case position;
    private Direction direction;
    private Game game;
    private Image image;
    private int lane_pos = 0;


    private Image i1= new ImageIcon("src/ressource/frog.png").getImage();
    private Image i2= new ImageIcon("src/ressource/frog2.png").getImage();
    private Image i3= new ImageIcon("src/ressource/frog3.png").getImage();
    private Image i4= new ImageIcon("src/ressource/frog4.png").getImage();



    public Frog(Game game) {
        this.position = new Case(game.width / 2, 0);
        this.direction = Direction.up;
        this.game = game;
        i1 = i1.getScaledInstance(game.getGraphic().getPixelByCase(),game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
        i2 = i2.getScaledInstance(game.getGraphic().getPixelByCase(),game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
        i3 = i3.getScaledInstance(game.getGraphic().getPixelByCase(),game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
        i4 = i4.getScaledInstance(game.getGraphic().getPixelByCase(),game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
        changeImage(1);
    }

    public void changeImage(int i){
        switch (i){
            case 1:
                image = i1;
                break;
            case 2:
                image = i2;
                break;
            case 3:
                image = i3;
                break;
            case 4:
                image = i4;
                break;
        }
    }


    public Image getImage(){
        return this.image;
    }
    public Case getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return this.direction;
    }


    public void move(Direction key) {
        this.direction = key;
        if (key == Direction.up && this.position.ord < this.game.height - 1) {
            changeImage(1);
            this.position = new Case(this.position.absc, this.position.ord + 1);
        }

        if (key == Direction.down && this.position.ord > 0) {
            changeImage(3);
            this.position = new Case(this.position.absc, this.position.ord - 1);
        }

        if (key == Direction.right && this.position.absc < this.game.width - 1) {
            changeImage(2);
            this.position = new Case(this.position.absc + 1, this.position.ord);
        }

        if (key == Direction.left && this.position.absc > 0) {
            changeImage(4);
            this.position = new Case(this.position.absc - 1, this.position.ord);
        }

        this.game.getGraphic().add(new Element(this.position, this.image));
        this.game.testWin();
        this.game.testLose();
    }

    public void moveInfinity(Direction key) {
        this.direction = key;
        if (key == Direction.up) {
            changeImage(1);
            if(lane_pos == 0)
                this.position = new Case(this.position.absc, this.position.ord + 1);
            this.game.move_lanes(- 1);
            lane_pos++;

        }

        if (key == Direction.down) {
            changeImage(3);
            if(lane_pos == 1)
                this.position = new Case(this.position.absc, this.position.ord - 1);
            if(lane_pos > 0) {
                this.game.move_lanes(1);
                lane_pos--;
            }
        }

        if (key == Direction.right && this.position.absc < this.game.width - 1) {
            changeImage(2);
            this.position = new Case(this.position.absc + 1, this.position.ord);
        }

        if (key == Direction.left && this.position.absc > 0) {
            changeImage(4);
            this.position = new Case(this.position.absc - 1, this.position.ord);
        }

        this.game.getGraphic().add(new Element(this.position, Color.RED));
        this.game.testWin();
        this.game.testLose();
    }
    public int getLanePos() {return this.lane_pos;}
}