module Library.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens Library.app to javafx.fxml;
    exports Library.app;
}