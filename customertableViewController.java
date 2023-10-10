package customerDashtv;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class customertableViewController {

    Connection con;
    PreparedStatement pst;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboCusAreas;

    @FXML
    private ComboBox<String> comboCusPapers;

    @FXML
    private TableView<CustomerBean> tableCusName;

    @FXML
    void doExportAndPrint(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Data to CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        Stage stage = (Stage) tableCusName.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                // Write column headers
                fileWriter.write("Customer Name,Mobile,Area,Email,Selected Papers\n");

                // Write data rows
                for (CustomerBean item : tableCusName.getItems()) {
                    fileWriter.write(item.getName() + "," + item.getMobile() + "," + item.getArea() + "," + item.getEmail() + "," + item.getSpapers() + "\n");
                }
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ObservableList<CustomerBean> list;

    @FXML
    void doFetch(ActionEvent event) {
        String ppr = comboCusPapers.getValue();

        if (ppr == null || ppr.isEmpty()) {
            return;
        }

        ObservableList<CustomerBean> customers = FetchAllCustomersByPaper(ppr);

        if (customers.isEmpty()) {
            System.out.println("No customers found for the selected paper.");
            return;
        }

        // Clear table columns and items
        tableCusName.getColumns().clear();
        tableCusName.getItems().clear();

        // Add table columns
        TableColumn<CustomerBean, String> nameColumn = new TableColumn<>("Customer Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(150);
        tableCusName.getColumns().add(nameColumn);

        TableColumn<CustomerBean, String> mobileColumn = new TableColumn<>("Mobile");
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tableCusName.getColumns().add(mobileColumn);

        TableColumn<CustomerBean, String> areaColumn = new TableColumn<>("Area");
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        tableCusName.getColumns().add(areaColumn);

        TableColumn<CustomerBean, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableCusName.getColumns().add(emailColumn);

        TableColumn<CustomerBean, String> spapersColumn = new TableColumn<>("Selected Papers");
        spapersColumn.setCellValueFactory(new PropertyValueFactory<>("spapers"));
        tableCusName.getColumns().add(spapersColumn);

        // Set table items
        tableCusName.setItems(customers);
    }

    ObservableList<CustomerBean> FetchAllCustomersByPaper(String ppr) {
        ObservableList<CustomerBean> customers = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT name, mobile, area, email, spapers FROM customers WHERE spapers LIKE ?");
            pst.setString(1, "%" + ppr + "%");

            ResultSet resultSet1 = pst.executeQuery();
            while (resultSet1.next()) {
                String name = resultSet1.getString("name");
                String mobile = resultSet1.getString("mobile");
                String area = resultSet1.getString("area");
                String email = resultSet1.getString("email");
                String spapers = resultSet1.getString("spapers");
                CustomerBean customer = new CustomerBean(name, mobile, area, email, spapers);
                customers.add(customer);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @FXML
    void doFilter(ActionEvent event) {
        String selectedArea = comboCusAreas.getValue();

        if (selectedArea == null || selectedArea.isEmpty()) {
            return;
        }

        tableCusName.getColumns().clear();

        TableColumn<CustomerBean, String> name = new TableColumn<>("Customer Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(150);
        tableCusName.getColumns().add(name);

        TableColumn<CustomerBean, String> mobile = new TableColumn<>("Mobile");
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tableCusName.getColumns().add(mobile);

        TableColumn<CustomerBean, String> area = new TableColumn<>("Area");
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        tableCusName.getColumns().add(area);

        TableColumn<CustomerBean, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableCusName.getColumns().add(email);

        TableColumn<CustomerBean, String> spapers = new TableColumn<>("Selected Papers");
        spapers.setCellValueFactory(new PropertyValueFactory<>("spapers"));
        tableCusName.getColumns().add(spapers);

        ObservableList<CustomerBean> customers = FetchAllCustomersByArea(selectedArea);
        tableCusName.setItems(customers);
    }

    ObservableList<CustomerBean> FetchAllCustomersByArea(String selectedArea) {
        ObservableList<CustomerBean> ary = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT name, mobile, area, email, spapers FROM customers WHERE area = ?");
            pst.setString(1, selectedArea);

            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String mobile = resultSet.getString("mobile");
                String area = resultSet.getString("area");
                String email = resultSet.getString("email");
                String spapers = resultSet.getString("spapers");
                CustomerBean ref = new CustomerBean(name, mobile, area, email, spapers);
                ary.add(ref);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ary;
    }

    void doFillRolls() {
        try {
            pst = con.prepareStatement("SELECT DISTINCT area FROM customers");
            ResultSet table = pst.executeQuery();

            while (table.next()) {
                String area = table.getString("area");
                System.out.println(area);
                comboCusAreas.getItems().add(area);
            }

            pst = con.prepareStatement("SELECT DISTINCT paper FROM papermaster");
            ResultSet table1 = pst.executeQuery();

            while (table1.next()) {
                String paper = table1.getString("paper");
                System.out.println(paper);
                comboCusPapers.getItems().add(paper);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
    }

    @FXML
    void initialize() {
        con = Papermaster.MYSQLConnector.doConnect();
        if (con == null)
            System.out.println("Invalid Connection");
        else
            System.out.println("Connected.................");
        doFillRolls();
    }
}
