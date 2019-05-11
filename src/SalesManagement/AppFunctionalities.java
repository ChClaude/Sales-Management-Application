package SalesManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Collections;

/**
 * AppFunctionalities.java
 *
 * This class creates the windows of different functionalities
 * of the main program
 *
 * @author Claude C DE-TCHAMBILA
 * Date: 2019/05/11
 */
public class AppFunctionalities {

    public static void sortedListOfFoodItems() {
        Stage window = new Stage();
        window.setTitle("Sorted List of Food Items");
        window.setOnCloseRequest(event -> {
            event.consume();
            window.close();
        });

        Collections.sort(Main.foodMenuItems);

        ListView itemsList = new ListView(FXCollections.observableArrayList(Main.foodMenuItems));

        StackPane layout = new StackPane(itemsList);
        layout.setPrefSize(700, 400);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }

    public static void saleTransaction() {

    }

    public static void salesReport() {

    }

}
