package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage=primaryStage;
        primaryStage.setTitle("Catalog");
        Scene scene = new Scene(initRootLayout());
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(820);
        primaryStage.show();
    }

    public TabPane initRootLayout(){
        try{
            //load root layout from fxml file
            FXMLLoader loader =new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/PrincipalTabPane.fxml"));
            TabPane principalTabPane = (TabPane) loader.load();
            return principalTabPane;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
