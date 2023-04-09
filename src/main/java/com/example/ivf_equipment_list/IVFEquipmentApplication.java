package com.example.ivf_equipment_list;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IVFEquipmentApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IVFEquipmentApplication.class.getResource("productSearch.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setTitle("IVF Equipment List");
        stage.setScene(scene);
        stage.show();

//        Parent root = FXMLLoader.load(getClass().getResource("productSearch.fxml"));
//        stage.setTitle("IVF Equipment List");
//        stage.setScene(new Scene(root,1080, 600 ));
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}