module com.labs.textprocessor.textprocessor2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires java.logging;
    requires java.desktop;

    opens com.labs.textprocessor to javafx.fxml;
    exports com.labs.textprocessor;

    exports com.labs.textprocessor.controller to javafx.fxml;
    opens com.labs.textprocessor.controller to javafx.fxml;
}