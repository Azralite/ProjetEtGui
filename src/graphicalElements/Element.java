package graphicalElements;

import java.awt.*;

import gameCommons.Case;


public class Element extends Case {
    public  Color color ;
    public  Image image;


    public Element(int absc, int ord, Color color) {
        super(absc, ord);
        this.color = color;
    }

    public Element(Case c, Color color) {
        super(c.absc, c.ord);
        this.color = color;
    }

    public Element(int absc, int ord, Image image) {
        super(absc, ord);
        this.image = image;
    }

    public Element(Case c, Image image) {
        super(c.absc, c.ord);
        this.image = image;
    }

}