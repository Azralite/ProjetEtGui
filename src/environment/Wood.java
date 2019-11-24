package environment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;

import javax.swing.*;
import java.awt.*;

public class Wood {
    private Game game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private Image image;

    public Wood(Game game, Case frontPosition, boolean leftToRight) {
        this.game = game;
        this.length = game.randomGen.nextInt(3) + 2;
        this.leftToRight = leftToRight;
        this.leftPosition = new Case(leftToRight ? frontPosition.absc - this.length : frontPosition.absc, frontPosition.ord);
        ImageIcon ii = new ImageIcon("src/ressource/tronc.png");
        this.image = ii.getImage();
        this.image = this.image.getScaledInstance(game.getGraphic().getPixelByCase(),game.getGraphic().getPixelByCase(),Image.SCALE_REPLICATE);
    }


    //TODO : ajout de methodes



    /* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
    private void addToGraphics() {
        for (int i = 0; i < length; i++) {
            game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord, image));
        }
    }

    public void move(boolean b, Case pos) {
        if (b) {
            if(moveFrog(pos))
                pos.absc += this.leftToRight ? 1 : -1;
            this.leftPosition.absc += this.leftToRight ? 1 : -1;
        }

        this.addToGraphics();
    }


    public boolean appearsInBounds() {
        return this.leftPosition.absc + this.length > 0 || this.leftPosition.absc < this.game.width;
    }

    public boolean isOn(Case pos) {
        if (pos.ord != this.leftPosition.ord) {
            return false;
        } else {
            return pos.absc >= this.leftPosition.absc && pos.absc < this.leftPosition.absc + this.length;
        }
    }

    public boolean moveFrog(Case pos){
        return pos.ord == this.leftPosition.ord && pos.absc >= this.leftPosition.absc && pos.absc < this.leftPosition.absc + this.length;
    }


}
