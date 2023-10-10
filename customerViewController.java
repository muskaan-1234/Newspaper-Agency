package Customermanager;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Hawkermanager.MYSQLConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class customerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCusName;

    @FXML
    private ListView<String> listPaper;

    @FXML
    private ListView<String> listPaperPrice;

    @FXML
    private ListView<String> listSelPaper;

    @FXML
    private ListView<String> listSelPrice;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private TextField txtHawker;

    @FXML
    private TextField txtCusMobNo;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private DatePicker dobStart;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doFetch(ActionEvent event) {
        String mobileNumber = txtCusMobNo.getText();
        try {
            pst = con.prepareStatement("SELECT * FROM customers WHERE mobile = ?");
            pst.setString(1, mobileNumber);
            ResultSet table = pst.executeQuery();
            
            while(table.next()) {
                String cusName = table.getString("name");
                String selectedPapers = table.getString("spapers");
                String selectedPrices = table.getString("sprices");
                String area = table.getString("area");
                String hawkerName = table.getString("hawker");
                String email = table.getString("email");
                String address = table.getString("address");
                LocalDate dob = table.getDate("dos").toLocalDate();
                //===========================
                txtCusName.setText(cusName);
                listSelPaper.getItems().addAll(selectedPapers.split(","));
                listSelPrice.getItems().addAll(selectedPrices.split(","));
                comboArea.getSelectionModel().select(area);
                txtHawker.setText(hawkerName);
                txtEmail.setText(email);
                txtCusAddress.setText(address);
                dobStart.setValue(dob);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void doSubscribe(ActionEvent event) {
        String MSG = "New subscriber subscribed successfully";

        String selectedPapers = String.join(",", listSelPaper.getItems());
        String selectedPrices = String.join(",", listSelPrice.getItems());

        LocalDate ld = dobStart.getValue();
        java.sql.Date dt = java.sql.Date.valueOf(ld);

        try {
            pst = con.prepareStatement("INSERT INTO customers VALUES(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, txtCusMobNo.getText());
            pst.setString(2, txtCusName.getText());
            pst.setString(3, selectedPapers);
            pst.setString(4, selectedPrices); // Set the concatenated prices directly

            pst.setString(5, comboArea.getValue());

            PreparedStatement hawkerPst = con.prepareStatement("SELECT hname FROM hawkers WHERE alloareas = ?");
            hawkerPst.setString(1, comboArea.getValue());
            ResultSet hawkerResult = hawkerPst.executeQuery();

            String hawkerName = "";
            if (hawkerResult.next()) {
                hawkerName = hawkerResult.getString("hname");
            }
            pst.setString(6, hawkerName);

            pst.setString(7, txtEmail.getText());
            pst.setString(8, txtCusAddress.getText());
            pst.setDate(9, dt);

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
    void doUnsubscribe(ActionEvent event) 
    {
    	String MSG2=("Subscriber unsubscried from table");
    	String MSG3=("Invalid subscriber");
        String mobileNumber = txtCusMobNo.getText();
        try 
        {
            pst = con.prepareStatement("DELETE FROM customers WHERE mobile = ?");
            pst.setString(1, mobileNumber);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) 
            {
                showMsg(MSG2);
            }
            else 
            {
                showMsg(MSG3);
            }
        } 
        catch (SQLException exp)
        {
            exp.printStackTrace();
        }
    }
    
    void 	showMsg2(String msg)
    {
    	Alert alert = new Alert(AlertType.INFORMATION);    			
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText("Look, an Information Dialog");
    			alert.setContentText(msg);
    			alert.showAndWait();
    }

    @FXML
    void doUpdate(ActionEvent event) 
    {
        String MSG1 = "Record updated in table";
        String mobileNumber = txtCusMobNo.getText();
        try {
            pst = con.prepareStatement("UPDATE customers SET name = ?, spapers = ?, sprices = ?, area = ?, hawker = ?, email = ?, address = ?, dos = ? WHERE mobile = ?");
            pst.setString(1, txtCusName.getText());
            pst.setString(2, String.join(",", listSelPaper.getItems()));
            pst.setString(3, String.join(",", listSelPrice.getItems()));
            pst.setString(4, comboArea.getValue());
            pst.setString(5, txtHawker.getText());
            pst.setString(6, txtEmail.getText());
            pst.setString(7, txtCusAddress.getText());
            pst.setDate(8, java.sql.Date.valueOf(dobStart.getValue()));
            pst.setString(9, mobileNumber);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) 
            {
                showMsg1(MSG1);
            } else 
            {
                showMsg1("Failed to update record");
            }
        } 
        catch (SQLException exp) 
        {
            exp.printStackTrace();
        }
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
    void doComboAreaSelected(ActionEvent event)  
    {
        String selectedArea = comboArea.getSelectionModel().getSelectedItem();
        if (selectedArea != null) 
        {
            try {
                pst = con.prepareStatement("select hname from hawkers where alloareas = ?");
                pst.setString(1, selectedArea);
                ResultSet hawkerResult = pst.executeQuery();

                if (hawkerResult.next()) 
                {
                    String hawkerName = hawkerResult.getString("hname");
                    txtHawker.setText(hawkerName);
                }

                pst.close();
                hawkerResult.close();
            } 
            catch (SQLException e) 
            {
                System.out.println("Error executing SQL query: " + e.getMessage());
            }
        }
    }
    
    @FXML
    void doshifttoselpaper(MouseEvent event) 
    { 
        String selectedItem = listPaper.getSelectionModel().getSelectedItem();
        int selectedIndex = listPaper.getSelectionModel().getSelectedIndex();

        if (selectedItem != null && selectedIndex != -1 && !listSelPaper.getItems().contains(selectedItem)) {
            listSelPaper.getItems().add(selectedItem);
            String price = listPaperPrice.getItems().get(selectedIndex);
            listSelPrice.getItems().add(price);
        }
    }
    
    @FXML
    void doDeletespaperandsprice(MouseEvent event) 
    {
    	 if (event.getClickCount() == 2) {
             String selectedItem = listSelPaper.getSelectionModel().getSelectedItem();

              int selectedIndex = listSelPaper.getSelectionModel().getSelectedIndex();

              if (selectedItem != null && selectedIndex != -1) {
                  listSelPaper.getItems().remove(selectedIndex);
                  listSelPrice.getItems().remove(selectedIndex);
              }
          }
    }
    
    void doSelectfromPaper()
    {
    	  try 
          {
              pst = con.prepareStatement("SELECT * FROM papermaster");
              ResultSet rs = pst.executeQuery();

              while (rs.next()) 
              {
                  try 
                  {
                      String paperName = rs.getString("paper");
                      String paperPrice = rs.getString("price");

                      listPaper.getItems().add(paperName);
                      listPaperPrice.getItems().add(paperPrice);
                  } 
                  catch (SQLException e) 
                  {
                      System.out.println(e);
                  }
              }

              pst.close();
              rs.close();
          } 
          catch (SQLException e) 
          {
              System.out.println("Error executing SQL query: " + e.getMessage());
          }
    }
    
    void doSelAreafromhawker()
    {

        try {
            pst = con.prepareStatement("select alloareas from hawkers");
            ResultSet areaResult = pst.executeQuery();

            while (areaResult.next()) 
            {
                String area = areaResult.getString("alloareas");
                comboArea.getItems().add(area);
            }

            pst.close();
            areaResult.close();
        }
        catch (SQLException e) 
        {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }
    
    @FXML
    void initialize() 
    {
        con = MYSQLConnector.doConnect();
        if (con == null) 
            System.out.println("Connection problem"); 
        else 
        
            System.out.println("You are connected successfully........");
              //=================================
            doSelectfromPaper();
          //==================================
            doSelAreafromhawker();
    }
}



