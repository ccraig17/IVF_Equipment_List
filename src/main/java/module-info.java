module com.example.ivf_equipment_list {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.ivf_equipment_list to javafx.fxml;
    exports com.example.ivf_equipment_list;
}