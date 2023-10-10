package Admindesk;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class admindeskViewController {
	
	Connection con;
	PreparedStatement pst;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void doOpenBillCollector(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/Billingcollector/BillingcollectorView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
       } catch (IOException e) {
           System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
           e.printStackTrace();
       }
    }

    @FXML
    void doOpenBillGenerator(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/billGenerator/billgenView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
       } catch (IOException e) {
           System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
           e.printStackTrace();
       }
    }

    @FXML
    void doOpenCustomerGoog(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/customerDashtv/customertableView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
       } catch (IOException e) {
           System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
           e.printStackTrace();
       }

    }

    @FXML
    void doOpenCustomerMas(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/Customermanager/customerView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
       } catch (IOException e) {
           System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
           e.printStackTrace();
       }

    }

    @FXML
    void doOpenHawkerMan(ActionEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/Hawkermanager/hawkerView.fxml"));
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.show();
        } catch (IOException e) {
            System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void doOpenMeetthedevelopers(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/dashBoard/dashView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
       } catch (IOException e) {
           System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
           e.printStackTrace();
       }

    }

    @FXML
    void doOpenPaperMaster(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/Papermaster/papermasterView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
       } catch (IOException e) {
           System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
           e.printStackTrace();
       }


    }

    @FXML
    void doOpenTableBillStatus(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/billBoard/billboardView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
       } catch (IOException e) {
           System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
           e.printStackTrace();
       }

    }

    @FXML
    void doOpentableHawkers(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/tablesview/tableView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
       } catch (IOException e) {
           System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
           e.printStackTrace();
       }

    }
    
    @FXML
    void doopenhawkermanwithimg(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Hawkermanager/hawkerView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    void doopenbillcollwithimg(MouseEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/Billingcollector/BillingcollectorView.fxml"));
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
             e.printStackTrace();
         }

    }

    @FXML
    void doopenbillgenwithimg(MouseEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/billGenerator/billgenView.fxml"));
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
             e.printStackTrace();
         }

    }

    @FXML
    void doopenbillstatuswithimg(MouseEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/billBoard/billboardView.fxml"));
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
             e.printStackTrace();
         }

    }

    @FXML
    void doopencustomergoogwithimg(MouseEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/customerDashtv/customertableView.fxml"));
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
             e.printStackTrace();
         }

    }

    @FXML
    void doopencustomermasterwithimg(MouseEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/Customermanager/customerView.fxml"));
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
             e.printStackTrace();
         }

    }

    @FXML
    void doopendisplayhawkerwithimg(MouseEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/tablesview/tableView.fxml"));
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
             e.printStackTrace();
         }

    }
    
    @FXML
    void doopenpapermasterwithimg(MouseEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/Papermaster/papermasterView.fxml"));
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             System.err.println("Error loading the Hawker Manager FXML file: " + e.getMessage());
             e.printStackTrace();
         }

    }
    
    @FXML
    void initialize() 
    {
    	con=Papermaster.MYSQLConnector.doConnect();
    	if(con==null)
    		System.out.println("Connection Problem");
    	else
    		System.out.println("Connected Successfully......");
    	

    }
}
