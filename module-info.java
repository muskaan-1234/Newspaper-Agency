module NewspaperAgency {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires javafx.media;
	
	opens application to javafx.graphics, javafx.fxml;
	opens Papermaster to javafx.graphics, javafx.fxml;
	opens Hawkermanager to javafx.graphics, javafx.fxml;
	opens Customermanager to javafx.graphics, javafx.fxml;
	opens billGenerator to javafx.graphics, javafx.fxml;
	opens Billingcollector to javafx.graphics, javafx.fxml;
	opens tablesview to javafx.graphics, javafx.fxml,javafx.base;
	opens customerDashtv to javafx.graphics, javafx.fxml,javafx.base;
	opens dashBoard to javafx.graphics, javafx.fxml;
	opens billBoard to javafx.graphics, javafx.fxml,javafx.base;
	opens Adminlog to javafx.graphics, javafx.fxml;
	opens Admindesk to javafx.graphics, javafx.fxml;
}
