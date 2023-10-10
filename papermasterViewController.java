package Papermaster;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class papermasterViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboPaper;

    @FXML
    private TextField txtPaperPrice;

    @FXML
    private ImageView imgPapermaster;
    
    Connection con;
    PreparedStatement pst; 

    @FXML
    void doAvail(ActionEvent event) 
    {
    	String MSG =("Record availed in table");
        float pri=Float.parseFloat(txtPaperPrice.getText());
        try
        {
        	pst=con.prepareStatement("insert into papermaster values(?,?)");
        	pst.setString(1,comboPaper.getValue());
        	pst.setFloat(2,pri);
        	pst.executeUpdate();
        }
        catch(Exception exp)
        {
        	exp.printStackTrace();
        }
        showMsg(MSG);
    }
    
    void 	showMsg(String msg)
    {
    	Alert alert = new Alert(AlertType.INFORMATION);    			
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText("Look, an Information Dialog");
    			alert.setContentText(msg);
    			alert.showAndWait();
    }
    
    @FXML
    void doEdit(ActionEvent event) 
    {
        String MSG1 = ("Record edited in table");
        float pri = Float.parseFloat(txtPaperPrice.getText());
        try {
            pst = con.prepareStatement("update papermaster set price = ? where paper = ?");
            pst.setFloat(1, pri);
            pst.setString(2, comboPaper.getValue());
            int count = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showMsg1(MSG1);
    }

    void 	showMsg1(String msg1)
    {
    	Alert alert = new Alert(AlertType.INFORMATION);    			
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText("Look, an Information Dialog");
    			alert.setContentText(msg1);
    			alert.showAndWait();
    }


    @FXML
    void doSearch(ActionEvent event) 
    {
        String selectedPaper = comboPaper.getValue();
            try 
            {
                pst = con.prepareStatement("select * from papermaster where paper = ?");
                pst.setString(1, selectedPaper);
                ResultSet table = pst.executeQuery();
                while(table.next())
                {
                    float price = table.getFloat("price");
                    txtPaperPrice.setText(String.valueOf(price));
                }
              
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }     
    }
    
    @FXML
    void doWithdraw(ActionEvent event) 
    {
    	 try 
    	 {
    	        String MSG2 = ("Record withdrawn in table");
    	        String MSG3 = ("Invalid Record");
    	        String selectedPaper = comboPaper.getValue();
    	        
    	        pst = con.prepareStatement("delete from papermaster where paper = ?");
    	        pst.setString(1, selectedPaper);
    	        int count = pst.executeUpdate();
    	        
    	        if (count != 0)
    	            showMsg2(MSG2);
    	        else
    	            showMsg2(MSG3);
    	    } 
    	 catch (SQLException exp) 
    	 {
    	        exp.printStackTrace();
    	    }
    }
  
    void 	showMsg2(String msg2)
    {
    	Alert alert = new Alert(AlertType.INFORMATION);    			
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText("Look, an Information Dialog");
    			alert.setContentText(msg2);
    			alert.showAndWait();
    }

	@FXML
    void initialize() 
    {
    	ArrayList<String> papersname=new ArrayList<String>(Arrays.asList("Select","The Economics Time","Danik Bhaskar","Punjab Kesari","The Tribune","Daily Punjab Times","The Hindustan Times"));
    	comboPaper.getItems().addAll(papersname);
    	con=MYSQLConnector.doConnect(); 
    	if (con==null)
    		System.out.println("Connection problem");
    	else
    		System.out.println("You are connected sucessfulyy........");
    	
    }
}
