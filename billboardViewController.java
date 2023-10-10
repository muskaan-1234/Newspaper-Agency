package billBoard;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.sql.Date;

public class billboardViewController {
    Connection con;
    PreparedStatement pst;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton radPending;

    @FXML
    private ToggleGroup dopending;

    @FXML
    private RadioButton radPaid;

    @FXML
    private TableView<BillgenBean> tableBillBoard;
    
    @FXML
    private ComboBox<String> combomobbill;

    @FXML
    private TextField txtTotAmount;

    @FXML
    void doExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Data to CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        Stage stage = (Stage) tableBillBoard.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                // Write column headers
                fileWriter.write("Mobile,Bill,Status\n");
                
                // Write data rows
                for (BillgenBean item : tableBillBoard.getItems()) {
                    fileWriter.write(item.getMobile() + "," + item.getBill() + "," + item.getBillStatus() + "\n");
                }
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void doSearchCus(ActionEvent event) {
        String status = "";
        if (radPaid.isSelected()) {
            status = "1";
        } else if (radPending.isSelected()) {
            status = "0";
        }
        try {
            String sql = "SELECT * FROM bills WHERE billstatus = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, status);

            ResultSet rs = pst.executeQuery();

            ObservableList<BillgenBean> data = FXCollections.observableArrayList();

            // Check if columns are already added before adding them again
            if (tableBillBoard.getColumns().isEmpty()) {
                TableColumn<BillgenBean, String> mobileCol = new TableColumn<>("Mobile");
                mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));

                TableColumn<BillgenBean, String> billCol = new TableColumn<>("Bill");
                billCol.setCellValueFactory(new PropertyValueFactory<>("bill"));

                TableColumn<BillgenBean, String> statusCol = new TableColumn<>("Status");
                statusCol.setCellValueFactory(new PropertyValueFactory<>("billStatus"));

                // Add new columns for datefrom, dateend, and missingdays
                TableColumn<BillgenBean, Date> datefromCol = new TableColumn<>("Date From");
                datefromCol.setCellValueFactory(new PropertyValueFactory<>("datefrom"));

                TableColumn<BillgenBean, Date> dateendCol = new TableColumn<>("Date End");
                dateendCol.setCellValueFactory(new PropertyValueFactory<>("dateend"));

                TableColumn<BillgenBean, Integer> missingdaysCol = new TableColumn<>("Missing Days");
                missingdaysCol.setCellValueFactory(new PropertyValueFactory<>("missingdays"));

                tableBillBoard.getColumns().addAll(mobileCol, billCol, statusCol, datefromCol, dateendCol, missingdaysCol);
            }

            double totalAmount = 0.0;
            while (rs.next()) {
                String mobile = rs.getString("mobile");
                String bill = rs.getString("bill");
                String billStatus = rs.getString("billstatus");
                Date datefrom = rs.getDate("datefrom");
                Date dateend = rs.getDate("dateend");
                int missingdays = rs.getInt("missingdays");
                double billAmount = Double.parseDouble(bill);

                data.add(new BillgenBean(mobile, bill, billStatus, datefrom, dateend, missingdays));

                totalAmount += billAmount;
            }
            tableBillBoard.setItems(data);
            rs.close();
            pst.close();
            txtTotAmount.setText(String.valueOf(totalAmount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doshowBillHistory(ActionEvent event) {
        String mobile = combomobbill.getSelectionModel().getSelectedItem();

        if (mobile != null && !mobile.isEmpty()) {
            try {
                String sql = "SELECT * FROM bills WHERE mobile = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, mobile);

                ResultSet rs = pst.executeQuery();

                ObservableList<BillgenBean> data = FXCollections.observableArrayList();

                TableColumn<BillgenBean, String> mobileCol1 = new TableColumn<>("Mobile");
                mobileCol1.setCellValueFactory(new PropertyValueFactory<>("mobile"));

                TableColumn<BillgenBean, String> billCol1 = new TableColumn<>("Bill");
                billCol1.setCellValueFactory(new PropertyValueFactory<>("bill"));

                TableColumn<BillgenBean, String> statusCol1 = new TableColumn<>("Status");
                statusCol1.setCellValueFactory(new PropertyValueFactory<>("billStatus"));

                // Add new columns for datefrom, dateend, and missingdays
                TableColumn<BillgenBean, Date> datefromCol1 = new TableColumn<>("Date From");
                datefromCol1.setCellValueFactory(new PropertyValueFactory<>("datefrom"));

                TableColumn<BillgenBean, Date> dateendCol1 = new TableColumn<>("Date End");
                dateendCol1.setCellValueFactory(new PropertyValueFactory<>("dateend"));

                TableColumn<BillgenBean, Integer> missingdaysCol1 = new TableColumn<>("Missing Days");
                missingdaysCol1.setCellValueFactory(new PropertyValueFactory<>("missingdays"));

                tableBillBoard.getColumns().clear();
                tableBillBoard.getColumns().addAll(mobileCol1, billCol1, statusCol1, datefromCol1, dateendCol1, missingdaysCol1);

                double totalAmount = 0.0;

                while (rs.next()) {
                    String bill = rs.getString("bill");
                    String billStatus = rs.getString("billstatus");
                    Date datefrom = rs.getDate("datefrom");
                    Date dateend = rs.getDate("dateend");
                    int missingdays = rs.getInt("missingdays");
                    double billAmount = Double.parseDouble(bill);

                    data.add(new BillgenBean(mobile, bill, billStatus, datefrom, dateend, missingdays));

                    totalAmount += billAmount;
                }

                tableBillBoard.setItems(data);
                rs.close();
                pst.close();

                txtTotAmount.setText(String.valueOf(totalAmount));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case where no mobile number is selected in the ComboBox
            // You can display an error message or perform any other required action.
        }
    }

    void doFillMobNos() {
        try {
            pst = con.prepareStatement("select distinct mobile from bills");
            ResultSet table = pst.executeQuery();
            while (table.next()) {
                String mobilenumbers = table.getString("mobile");
                System.out.println(mobilenumbers);
                combomobbill.getItems().add(mobilenumbers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    void initialize() {
        con = Papermaster.MYSQLConnector.doConnect();
        if (con == null)
            System.out.println("Invalid Connection");
        else
            System.out.println("Connected.................");
        //==========================
        doFillMobNos();
    }
}
