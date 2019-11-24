package gameCommons;

import graphicalElements.Menu;


public class Main {

    public static void main(String[] args) {

        //Caractéristiques du jeu
        int width = 26;
        int height = 20;
        int tempo = 100;
        int minSpeedInTimerLoops = 3;
        double defaultDensity = 0.04;
        double temps = 0;

        //Creation du menu
        Menu menu = new Menu(width,height, tempo, minSpeedInTimerLoops, defaultDensity);


//

    }


}