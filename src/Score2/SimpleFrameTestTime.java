package Score2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.*;


public class SimpleFrameTestTime extends JFrame {

    private static String PATH = "src/ressource/classementTime.json";
    private static String[] classement;

    public SimpleFrameTestTime(JLabel username, double score) {

        setSize(300, 300);
        setTitle("Classement");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    public void initComponents(JLabel username, double score) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        username.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton setNameButton = new JButton("Votre nom");
        setNameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setNameButton.addActionListener((ActionEvent e) -> {
            String usernameinput = JOptionPane.showInputDialog(setNameButton, "Entrer un nom", "Entrer un nom", JOptionPane.OK_CANCEL_OPTION);
            this.setVisible(false);
            if (usernameinput != null) {
                username.setText(String.valueOf(usernameinput));
                printJson(username.getText(), score);
            }

            this.remove(panel);
            showScore();

        });

        panel.add(Box.createRigidArea(new Dimension(5,10)));
        panel.add(username);
        panel.add(Box.createRigidArea(new Dimension(5,10)));
        panel.add(setNameButton);
        add(panel);

    }

    public static String[] printJson(String username, double score){

        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse((new FileReader(PATH)));

            JSONArray jsonArray = (JSONArray) obj;
            for(int i = 0; i<jsonArray.size(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if(Double.parseDouble(String.valueOf(jsonObject.get("score"))) >= score){
                    JSONObject temp = new JSONObject();
                    temp.put("username", username);
                    temp.put("score", score);
                    jsonArray.set(i, temp);
                    temp = jsonObject;
                    for(int j = i+1; j<jsonArray.size(); j++) {
                        JSONObject temp2 = (JSONObject) jsonArray.get(j);
                        jsonArray.set(j, temp);
                        temp = temp2;
                    }
                    break;
                }
            }

            String[] res = new String[jsonArray.size()];
            for (int i = 0; i<jsonArray.size(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                res[i] = (i+1) + ". " + jsonObject.get("username") + " : " + jsonObject.get("score") + '\n';
            }
            classement = res;


            try (FileWriter file = new FileWriter(PATH)) {
                file.write(jsonArray.toJSONString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return classement;
    }


    public static void showScore(){
        JFrame test = new JFrame();
        test.setSize(26*32, 20*32);
        test.setTitle("Classement");
        test.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        test.setLocationRelativeTo(null);
        test.setResizable(true);
        test.setVisible(true);


        JLabel highscore = new JLabel(new ImageIcon("src/ressource/highscore.png"));

        JPanel scorePan = new JPanel();
        scorePan.setBackground(Color.BLACK);
        scorePan.setLayout(new GridLayout(classement.length + 1, 1));
        scorePan.add(highscore);

        for (int i = 0; i< classement.length; i++){
            JLabel score = new JLabel();
            score.setText(classement[i]);
            score.setForeground(Color.WHITE);
            scorePan.add(score);
        }


        test.add(scorePan);
    }

}