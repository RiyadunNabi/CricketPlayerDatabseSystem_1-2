module com.example.myjavafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.desktop;

    opens com.example.myjavafxproject to javafx.fxml;
    opens com.example.myjavafxproject.ClientController to javafx.fxml;
    opens com.example.myjavafxproject.cricketdatabase;

    exports com.example.myjavafxproject;
}