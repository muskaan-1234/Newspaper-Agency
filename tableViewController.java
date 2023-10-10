package tablesview;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import java.util.ArrayList;

public class tableViewController 
{
	Connection con;
	PreparedStatement pst;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<HawkerBean> tableviewHawker;
    
    @FXML
    void doShowAll(ActionEvent event) 
    {
    	TableColumn<HawkerBean, String> name=new TableColumn<HawkerBean, String>("Hawker Name");
    	name.setCellValueFactory(new PropertyValueFactory<>("hname")); 
    	//name.setMinWidth(150);
    	
    	TableColumn<HawkerBean, String> mobile=new TableColumn<HawkerBean, String>("Hawker Mobile No");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(50);
    	
    	 TableColumn<HawkerBean, String> alloareas = new TableColumn<HawkerBean, String>("Allocated Areas");
         alloareas.setCellValueFactory(new PropertyValueFactory<>("allo_areas"));
         alloareas.setMinWidth(50);
    	
    	TableColumn<HawkerBean, String> doj=new TableColumn<HawkerBean, String>("Date of joining");//any thing
    	doj.setCellValueFactory(new PropertyValueFactory<>("doj"));
    	doj.setMinWidth(50);
    	
       	tableviewHawker.getColumns().addAll(new ArrayList<>(Arrays.asList(name, mobile, alloareas, doj)));
    	tableviewHawker.setItems(FetchAllHawkers());
    	
    }
    ObservableList<HawkerBean> FetchAllHawkers() 
    {
    	ObservableList<HawkerBean>	ary=FXCollections.observableArrayList();
    	try {
    	   	
    		pst = con.prepareStatement("select * from hawkers");
    		ResultSet table=pst.executeQuery();
    	
    		while(table.next()) {
	    		String mno=table.getString("mobile");
	    		String name = table.getString("hname");
	    		String DOJ = String.valueOf(table.getDate("doj").toLocalDate());
	    		String alloareas=table.getString("alloareas");
	    		
	    		HawkerBean ref=new HawkerBean(name, mno, alloareas, DOJ);
	    		ary.add(ref);
    		}
    	
    	}
    	catch(Exception ex) { ex.printStackTrace(); }
    		return ary;
    }



    @FXML
    void initialize()
    {
    	con = Papermaster.MYSQLConnector.doConnect();
    	if(con==null) { System.out.println("Invalid Connection"); }
    	else { System.out.println("Connected");	}

       
    }
}
