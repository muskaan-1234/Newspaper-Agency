package Billingcollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class BillingcollectorViewController {
	
	Connection con;
	PreparedStatement pst;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Combocollectormobnos;

    @FXML
    private TextField txtFromDate;

    @FXML
    private TextField txtToDate;

    @FXML
    private TextField txtAmount;

    @FXML
    private Label lblMessage;

    @FXML
    private ImageView collectorImgView;

    @FXML
    void doCombocollectmobnos(ActionEvent event) {
    	String selectedMobNumbers = Combocollectormobnos.getSelectionModel().getSelectedItem();
    }

    @FXML
    void doGetBillDetails(ActionEvent event) {
        String mobileNumber = Combocollectormobnos.getValue(); // Use selected mobile number from combo box

        if (mobileNumber != null && !mobileNumber.isEmpty()) {
            try {
                pst = con.prepareStatement("SELECT bill,datefrom,dateend FROM bills WHERE mobile = ?");
                pst.setString(1, mobileNumber);
                ResultSet resultSet = pst.executeQuery();

                if (resultSet.next()) {
                    double amount = resultSet.getDouble("bill");
                    String fromDateStr = resultSet.getString("datefrom");
                    String toDateStr = resultSet.getString("dateend");

                    DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fromDate = LocalDate.parse(fromDateStr, dbFormatter);
                    LocalDate toDate = LocalDate.parse(toDateStr, dbFormatter);

                    DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String fromDateFormatted = fromDate.format(displayFormatter);
                    String toDateFormatted = toDate.format(displayFormatter);

                    txtAmount.setText(String.valueOf(amount));
                    txtFromDate.setText(fromDateFormatted); // Set the correct date in the from date text field
                    txtToDate.setText(toDateFormatted); // Set the correct date in the to date text field

                    pst = con.prepareStatement("UPDATE bills SET billstatus = 1 WHERE mobile = ?");
                    pst.setString(1, mobileNumber);
                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Bill status updated to Payment Done.");
                    } else {
                        System.out.println("Failed to update bill status.");
                    }
                } else {
                    lblMessage.setText("No bill details found for the provided mobile number.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblMessage.setText("Please select a mobile number from the combo box.");
        }
    }
    
    void doFillMobNos()
    {
    	try
    	{
    		pst=con.prepareStatement("select distinct mobile from bills");
    		ResultSet table=pst.executeQuery();
    		while(table.next())
    		{
    			String mobilenumbers=table.getString("mobile");
    			System.out.println(mobilenumbers);
    			Combocollectormobnos.getItems().add(mobilenumbers);
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void doGetPaymentmessage(ActionEvent event) {
    	String mobileNumber = Combocollectormobnos.getValue(); // Use selected mobile number from combo box

        if (mobileNumber != null && !mobileNumber.isEmpty()) {
            try {
                pst = con.prepareStatement("SELECT billstatus FROM bills WHERE mobile = ?");
                pst.setString(1, mobileNumber);
                ResultSet resultSet = pst.executeQuery();

                if (resultSet.next()) {
                    int billStatus = resultSet.getInt("billstatus");

                    if (billStatus == 1) {
                        lblMessage.setText("Payment Done");
                    } else {
                        lblMessage.setText("Payment Pending");
                    }
                } else {
                    lblMessage.setText("No bill details found for the provided mobile number.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblMessage.setText("Please select a mobile number from the combo box.");
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

        doFillMobNos();
    }
}
