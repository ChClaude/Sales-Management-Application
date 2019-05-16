package SalesManagement;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * AppFunctionalities.java
 * <p>
 * This class creates the windows of different functionalities
 * of the main program
 *
 * @author Claude C DE-TCHAMBILA
 * Date: 2019/05/11
 */
public class AppFunctionalities {

    ComboBox<String> categories;
    ListView<FoodMenuItem> itemListView;
    ArrayList<FoodMenuItem> categoryRestrictive;
    private boolean nextTime;

    public static void salesReport() {

    }

    public void sortedListOfFoodItems(ArrayList<FoodMenuItem> foodMenuItems) {
        Stage window = new Stage();
        window.setTitle("Sorted List of Food Items");
        window.setOnCloseRequest(event -> {
            event.consume();
            window.close();
        });

        ListView itemsList = new ListView(FXCollections.observableArrayList(foodMenuItems));

        StackPane layout = new StackPane(itemsList);
        layout.setPrefSize(700, 400);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }

    public void saleTransaction(ArrayList<FoodMenuItem> foodMenuItems) {
        Stage window = new Stage();
        window.setTitle("Sales Transaction");
        window.initModality(Modality.APPLICATION_MODAL);

        VBox vBox = new VBox(15);

        Text headerText = new Text("Sales Transaction");

        categories = new ComboBox<>();
        categories.getItems().addAll("main meal", "starter", "drinks");
        categories.setPromptText("select category");

        categories.setOnAction(event -> {
            categoryRestrictive = null;
            categoryRestrictive = new ArrayList<>();
            boolean matchCategory = false;
            String string = categories.getValue();

            for (FoodMenuItem f : foodMenuItems) {
                if (f.getCategory().equalsIgnoreCase(string)) {
                    categoryRestrictive.add(f);
                    matchCategory = true;
                }
            }

            if (nextTime) {
                itemListView.getItems().clear();
            }


            if (matchCategory) {
                itemListView.getItems().clear();
                itemListView.getItems().addAll(FXCollections.observableArrayList(categoryRestrictive));
                itemListView.setVisible(true);
                nextTime = true;
            }
        });


        itemListView = new ListView<>();
        itemListView.setVisible(false);

        HBox hBox = new HBox(30);
        Button buyButton = new Button("BUY");

        Button backButton = new Button("BACK");
        backButton.setOnAction(event -> window.close());

        hBox.getChildren().addAll(buyButton, backButton);

        vBox.getChildren().addAll(headerText, categories, itemListView, hBox);

        Scene scene = new Scene(vBox, 700, 400);

        window.setScene(scene);
        window.showAndWait();
    }


}
