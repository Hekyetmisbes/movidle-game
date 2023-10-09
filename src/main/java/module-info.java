module com.erciyes.movidlegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.erciyes.movidlegame to javafx.fxml;
    exports com.erciyes.movidlegame;
}