package Adminlog;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class adminlogViewController {

    Connection con;
    PreparedStatement pst;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblResp;

    @FXML
    private PasswordField passAdmin;

     void playSound() {
        URL url = getClass().getResource("machine-gun.mp3");
        Media media = new Media(url.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
    }

    @FXML
    void doLogin(ActionEvent event) {
        String enteredPassword = passAdmin.getText().trim();
        String correctPassword = "JaiJava";
        
        if (enteredPassword.equals(correctPassword)) {
            playSound();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Admindesk/admindeskView.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                Scene scene1 = (Scene) lblResp.getScene();
                scene1.getWindow().hide();

            } catch (IOException e) {
                System.err.println("Error loading the FXML file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid password");
            showMsg("Incorrect password. Please try again.");
        }
    }

    void showMsg(String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, Warning for password");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        con = Papermaster.MYSQLConnector.doConnect();
        if (con == null)
            System.out.println("Connection Problem");
        else
            System.out.println("Connected Successfully......");

        //passAdmin.setText("JaiJava");
    }
}
