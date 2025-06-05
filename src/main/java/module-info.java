module com.umbell {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.umbell to javafx.fxml;
    exports com.umbell;
}
