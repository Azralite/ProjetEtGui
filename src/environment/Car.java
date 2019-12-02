package environment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;

import javax.swing.*;
import java.awt.*;

public class Car {
    private Game game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private Image image;

    public Car(Game game, Case frontPosition, boolean leftToRight) {
        this.game = game;
        this.length = game.randomGen.nextInt(3) + 1;
        this.leftToRight = leftToRight;
        this.leftPosition = new Case(leftToRight ? frontPosition.absc - this.length : frontPosition.absc, frontPosition.ord);
        if (this.length == 3){
            if (leftToRight){
                this.image = new ImageIcon("src/ressource/camion2.png").getImage();
            }
            else {
                this.image = new ImageIcon("src/ressource/camion.png").getImage();
            }
            this.image = this.image.getScaledInstance(game.getGraphic().getPixelByCase()*3,game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
        }
        else if(this.length == 2){
            if (leftToRight){
                this.image = new ImageIcon("src/ressource/voiture2.png").getImage();
            }
            else {
                this.image = new ImageIcon("src/ressource/voiture.png").getImage();
            }
            this.image = this.image.getScaledInstance(game.getGraphic().getPixelByCase()*2,game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
        }
        else if(this.length == 1) {
            if (leftToRight){
                this.image = new ImageIcon("src/ressource/moto2.png").getImage();
            }
            else {
                this.image = new ImageIcon("src/ressource/moto.png").getImage();
            }
            this.image = this.image.getScaledInstance(game.getGraphic().getPixelByCase(),game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
        }
    }


    public Image getImage(){
        return this.image;
    }

    //TODO : ajout de methodes


    /* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
    private void addToGraphics() {
        game.getGraphic().add(new Element(leftPosition.absc , leftPosition.ord, this.image));
    }

    public void move(boolean b) {
        if (b) {
            this.leftPosition.absc += this.leftToRight ? 1 : -1;
        }

        this.addToGraphics();
    }


    public boolean appearsInBounds() {
        return this.leftPosition.absc + this.length > 0 || this.leftPosition.absc < this.game.width;
    }

    public boolean coversCase(Case pos) {
        if (pos.ord != this.leftPosition.ord) {
            return false;
        } else {
            return pos.absc >= this.leftPosition.absc && pos.absc < this.leftPosition.absc + this.length;
        }
    }

    public void setOrd(int n){
        this.leftPosition = new Case(this.leftPosition.absc, n);
    }

    public int getOrd(){return leftPosition.ord;}

    public int getAbs(){return leftPosition.absc;}



}