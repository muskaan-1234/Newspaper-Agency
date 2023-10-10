package dashBoard;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class dashViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgDeveloped;

    @FXML
    private ImageView imgUnder;
 
    Connection con;
    @FXML
    void initialize() 
    {
    	con=Papermaster.MYSQLConnector.doConnect();
    	if(con==null)
    		System.out.println("Invalid Connector");
    	else
    		System.out.println("Connected.......");
           
    }
}
