package Hawkermanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Hawkermanager.MYSQLConnector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class hawkerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboHawkerName;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtAddress;

    @FXML
    private ComboBox<String> comboHawkerAreas;

    @FXML
    private ImageView imgHawkerAadhar;
    
    @FXML
    private Label lblPath;

    @FXML
    private TextField txtAllocatedAreas;

    Connection con;
    PreparedStatement pst;
   
    @FXML
    void doPutSelAreas(MouseEvent event) {
        String selectedArea = comboHawkerAreas.getSelectionModel().getSelectedItem();
        String currentAllocatedAreas = txtAllocatedAreas.getText().trim();

        // Check if the selected area is not null or empty
        if (selectedArea != null && !selectedArea.isEmpty()) {
            // Check if the "Allocated Areas" text field is not empty
            if (!currentAllocatedAreas.isEmpty()) {
                // Check if the selected area is not already present in the allocated areas
                if (!currentAllocatedAreas.contains(selectedArea)) {
                    // Add the selected area to the existing areas with a comma
                    currentAllocatedAreas += ", " + selectedArea;
                } else {
                    // Area already exists, show an error message or perform some other action as needed
                    System.out.println("Area already selected.");
                }
            } else {
                // If the "Allocated Areas" text field is empty, set it to the selected area
                currentAllocatedAreas = selectedArea;
            }
        }

        // Update the "Allocated Areas" text field
        txtAllocatedAreas.setText(currentAllocatedAreas);
    }

    @FXML
    void doEnroll(ActionEvent event) {
        String MSG = "Record enrolled successfully";
        String name = comboHawkerName.getValue();
        String mobile = txtMobile.getText();
        String address = txtAddress.getText();
        String allocatedAreas = txtAllocatedAreas.getText();

        try {
            String query = "INSERT INTO hawkers (hname, mobile, address, alloareas, picpath, doj) VALUES (?, ?, ?, ?, ?, current_date)";
            pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, mobile);
            pst.setString(3, address);
            pst.setString(4, allocatedAreas);
            pst.setString(5, lblPath.getText());
            pst.executeUpdate();
        } catch (SQLException exp) {
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
    void doFire(ActionEvent event) throws SQLException {
        String MSG2 = "Record fired in table";
        String MSG3 = "Invalid Record";
        String selName = comboHawkerName.getValue();

        pst = con.prepareStatement("DELETE FROM hawkers WHERE hname = ?");
        pst.setString(1, selName);
        int count = pst.executeUpdate();

        if (count != 0) {
            showMsg2(MSG2);
        } else {
            showMsg2(MSG3);
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
void doNew(ActionEvent event) throws SQLException {
    String selectedName = comboHawkerName.getValue();
    if (selectedName == null || selectedName.isEmpty()) {
        System.out.println("Please select a valid Hawker Name.");
        return;
    }

    try {
        pst = con.prepareStatement("DELETE * from hawkers");
        pst.setString(1, selectedName);
        int count = pst.executeUpdate();

        if (count != 0) {
            System.out.println("Record for '" + selectedName + "' deleted successfully.");
            showMsg3("Record for '" + selectedName + "' deleted successfully.");
            // Clear the fields after deleting the record
            comboHawkerName.getItems().remove(selectedName);
            comboHawkerName.getSelectionModel().clearSelection();
            txtMobile.clear();
            txtAddress.clear();
            comboHawkerAreas.setValue(null);
            txtAllocatedAreas.clear();
            lblPath.setText("");
            imgHawkerAadhar.setImage(null);
        } else {
            System.out.println("No records found for '" + selectedName + "'.");
            showMsg3("No records found for '" + selectedName + "'.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Deletion failed");
        showMsg3("Deletion failed");
    }
}
    
    void 	showMsg3(String msg3)
    {
      	Alert alert = new Alert(AlertType.INFORMATION);    			
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText("Look, an Information Dialog");
    			alert.setContentText(msg3);
    			alert.showAndWait();
    }

    @FXML
    void doSearch(ActionEvent event) throws FileNotFoundException {
        String selectedName = comboHawkerName.getValue();
        if (selectedName == null || selectedName.isEmpty()) {
            System.out.println("Please select a valid Hawker Name.");
            return;
        }

        try {
            pst = con.prepareStatement("SELECT * FROM hawkers WHERE hname = ?");
            pst.setString(1, selectedName);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("hname");
                String mobile = resultSet.getString("mobile");
                String address = resultSet.getString("address");
                String allocatedAreas = resultSet.getString("alloareas");
                String picPath = resultSet.getString("picpath");

                txtMobile.setText(mobile);
                txtAddress.setText(address);
                txtAllocatedAreas.setText(allocatedAreas);
                lblPath.setText(picPath);
                imgHawkerAadhar.setImage(new Image(new FileInputStream(picPath)));

                System.out.println("Name: " + name);
                System.out.println("Mobile: " + mobile);
                System.out.println("Address: " + address);
                System.out.println("Allocated Areas: " + allocatedAreas);
                System.out.println("Pic Path: " + picPath);
            } else {
                System.out.println("No records found");
                // Clear the fields if no records found
                txtMobile.clear();
                txtAddress.clear();
                txtAllocatedAreas.clear();
                lblPath.setText("");
                imgHawkerAadhar.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Search failed");
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        String MSG1 = "Record edited in table";
        String name = comboHawkerName.getValue();
        String mobile = txtMobile.getText();
        String address = txtAddress.getText();
        String allocatedAreas = txtAllocatedAreas.getText();

        try {
            String query = "UPDATE hawkers SET mobile = ?, address = ?, alloareas = ?, picpath = ? WHERE hname = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, mobile);
            pst.setString(2, address);
            pst.setString(3, allocatedAreas);
            pst.setString(4, lblPath.getText());
            pst.setString(5, name);
            pst.executeUpdate();
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
    void doUploadAadharpic(ActionEvent event) throws FileNotFoundException 
    {
    	FileChooser fileChooser = new FileChooser();
   	 fileChooser.setTitle("Open Resource File");
   	 fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif") );
   	 File selectedFile = fileChooser.showOpenDialog(null);
   	 
   	 if (selectedFile != null) {
   	    lblPath.setText(selectedFile.getPath());
   	    Image img=new Image(selectedFile.toURI().toString());
   	    System.out.println(selectedFile.toURI().toString());
   	 imgHawkerAadhar.setImage(new Image(new FileInputStream(selectedFile))); 
   	    
   	 }
   	 else
   	 {
   		 lblPath.setText("nopic.jpg");
   	 }

    }
    
       @FXML
    void initialize() 
    {
    	   ArrayList<String> names=new ArrayList<String>(Arrays.asList("Select","Nikhil","Raman","Shubham","Aryan","Gabbar"));
    	   comboHawkerName.getItems().addAll(names);
    	   ArrayList<String> areas=new ArrayList<String>(Arrays.asList("Select","Ajit Road","Model Town","Railway road","Parsaram nagar","Bus stand","Powerhouse road","Mall Road","Arya samajh chowk"));
    	   comboHawkerAreas.getItems().addAll(areas);
    	   lblPath.setText("nopic.jpg");
     	  con=MYSQLConnector.doConnect(); 
    	if (con==null)
    		System.out.println("Connection problem");
    	else
    		System.out.println("You are connected sucessfulyy........");
    	//========================================


    	

    }
}
