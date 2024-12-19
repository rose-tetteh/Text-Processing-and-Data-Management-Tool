module com.labs.textprocessor.textprocessor2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    opens com.labs.textprocessor.textprocessor2 to javafx.fxml;
    exports com.labs.textprocessor.textprocessor2;
}