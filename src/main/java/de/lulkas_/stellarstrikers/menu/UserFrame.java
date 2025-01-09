package de.lulkas_.stellarstrikers.menu;

import de.lulkas_.stellarstrikers.Main;
import org.gamejolt.GameJoltAPI;

import javax.swing.*;

public class UserFrame extends JFrame {
    private JTextField usernameField;
    private JPanel panel;
    private JTextField usertokenField;
    private JButton confirmButton;
    private JLabel errorMessages;

    private String username;
    private String usertoken;
    public boolean entered = false;

    public UserFrame(GameJoltAPI api) {
        setContentPane(panel);
        setTitle("Login");
        setIconImage(Main.icon);
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        confirmButton.addActionListener(e -> {
            username = usernameField.getText();
            usertoken = usertokenField.getText();

            if(usertoken.equals("")) {
                errorMessages.setText("You need to enter your gametoken.");
            } else if(username.equals("")) {
                errorMessages.setText("You need to enter your username.");
            } else {
                if(api.verifyUser(username, usertoken)) {
                    entered = true;
                    this.dispose();
                } else {
                    errorMessages.setText("Username or gametoken incorrect");
                }
            }
        });
    }
}
