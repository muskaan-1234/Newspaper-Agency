package billGenerator;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import Hawkermanager.MYSQLConnector;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class billgenViewController {
	
	Connection con;
	PreparedStatement pst;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMobNumbers;

    @FXML
    private TextField txtPapers;

    @FXML
    private TextField txtPrices;

    @FXML
    private TextField txtTotalPrices;
    
    @FXML
    private Label lblTotalBill;
    
    @FXML
    private TextField txtMissingDays;
     
    @FXML
    private DatePicker dobLast;

    @FXML
    private DatePicker dobEnd;
    
    @FXML
    void doshowAllfields(ActionEvent event) {
        String selectedMobile = comboMobNumbers.getValue();
        if (selectedMobile != null) {
            try {
                pst = con.prepareStatement("SELECT spapers, sprices, dos FROM customers WHERE mobile = ?");
                pst.setString(1, selectedMobile);
                ResultSet table = pst.executeQuery();

                if (table.next()) {
                    String papers = table.getString("spapers");
                    String prices = table.getString("sprices");
                    String lastBillDate = table.getString("dos");

                    txtPapers.setText(papers);
                    txtPrices.setText(prices);
                    dobLast.setValue(LocalDate.parse(lastBillDate));

                    // Calculate total prices and update the text field
                    docalculateTotalPrices();
                } else {
                    System.out.println("No data found for the selected mobile number.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void doGeneratebillAndsave(ActionEvent event) {
        String selectedMobile = comboMobNumbers.getValue();
        LocalDate lastDate = dobLast.getValue();
        LocalDate billUpto = dobEnd.getValue();

        // Validate input fields
        if (selectedMobile != null && lastDate != null && billUpto != null && !txtMissingDays.getText().isEmpty() && !txtTotalPrices.getText().isEmpty()) {
            try {
                int missingDays = Integer.parseInt(txtMissingDays.getText());
                float totalPrice = Float.parseFloat(txtTotalPrices.getText());

                long daysBetween = billUpto.toEpochDay() - lastDate.toEpochDay() + 1; // Add 1 to include both the start and end dates

                float netBill = totalPrice * daysBetween - missingDays;
                lblTotalBill.setText(String.valueOf(netBill));

                // Update last bill date to the end date
                pst = con.prepareStatement("UPDATE customers SET dos = ? WHERE mobile = ?");
                pst.setDate(1, Date.valueOf(billUpto));
                pst.setString(2, selectedMobile);
                pst.executeUpdate();

                // Insert bill record
                pst = con.prepareStatement("INSERT INTO bills (mobile, datefrom, dateend, missingdays, bill, billstatus) VALUES (?, ?, ?, ?, ?, 0)");
                pst.setString(1, selectedMobile);
                pst.setDate(2, Date.valueOf(lastDate));
                pst.setDate(3, Date.valueOf(billUpto));
                pst.setInt(4, missingDays);
                pst.setFloat(5, netBill);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Data added successfully.");
                } else {
                    System.out.println("Failed to add data.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid missing days and total prices.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please fill in all the required fields.");
        }
    }

    
        
    @FXML
    void docomboMobnumbers(ActionEvent event) {
        String selectedMobNumbers = comboMobNumbers.getSelectionModel().getSelectedItem();
        if (selectedMobNumbers != null) {
            dofilldata(selectedMobNumbers);
            docalculateTotalPrices();
        }
    }
    
    void dofilldata(String selectedMobNumbers) {
        try {
            pst = con.prepareStatement("SELECT spapers, sprices, dos, missingdays FROM customers LEFT JOIN bills ON customers.mobile = bills.mobile WHERE customers.mobile = ? ORDER BY dos DESC");
            pst.setString(1, selectedMobNumbers);
            ResultSet table = pst.executeQuery();

            // Assuming you want to display the most recent record (the first record retrieved)
            if (table.next()) {
                String Papers = table.getString("spapers");
                String Prices = table.getString("sprices");
                String LastBillDate = table.getString("dos");
                int missingDays = table.getInt("missingdays"); // Retrieve missing days from the "bills" table

                txtPapers.setText(Papers);
                txtPrices.setText(Prices);
                LocalDate lastBillDate = LocalDate.parse(LastBillDate);
                dobLast.setValue(lastBillDate);
                txtMissingDays.setText(String.valueOf(missingDays)); // Set the missing days value in the text field
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void docalculateTotalPrices() {
        String pricesText = txtPrices.getText();
        if (!pricesText.isEmpty()) {
            String[] pricesArray = pricesText.split(",");
            Double total = 0.0;
            for (String price : pricesArray) {
                total += Double.parseDouble(price);
            }
            txtTotalPrices.setText(String.valueOf(total));
        } else {
            txtTotalPrices.setText("");
        }
    }

    void doFillMobNos()
    {
    	try
    	{
    		pst=con.prepareStatement("select distinct mobile from customers");
    		ResultSet table=pst.executeQuery();
    		while(table.next())
    		{
    			String mobilenumbers=table.getString("mobile");
    			System.out.println(mobilenumbers);
    			comboMobNumbers.getItems().add(mobilenumbers);
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
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
         //===================================
         doFillMobNos();
       
    }
}
