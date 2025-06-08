module com.umbell {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.umbell to javafx.fxml;
    opens com.umbell.controller to javafx.fxml;
    exports com.umbell;
    exports com.umbell.controller;
}
