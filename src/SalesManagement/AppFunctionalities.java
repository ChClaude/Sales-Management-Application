package SalesManagement;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

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

    private ComboBox<String> categories;
    private ArrayList<FoodMenuItem> categoryRestrictive;
    private boolean nextTime;

    private TableView<FoodMenuItem> salesTransactionTable;

    private ArrayList<FoodMenuItem> saleReportItem;
    public AppFunctionalities() {
        saleReportItem = new ArrayList<>();
    }

    public void sortedListOfFoodItems(ArrayList<FoodMenuItem> foodMenuItems) {
        Stage window = new Stage();
        window.setTitle("Sorted List of Food Items");
        window.setOnCloseRequest(event -> {
            event.consume();
            window.close();
        });

        TableView<FoodMenuItem> tableView = new TableView<>();

        TableColumn<FoodMenuItem, String> name = new TableColumn<>("Item Name");
        name.setMinWidth(270);
        name.setCellValueFactory(new PropertyValueFactory<>("foodItem"));

        TableColumn<FoodMenuItem, String> category = new TableColumn<>("Category");
        category.setMinWidth(230);
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<FoodMenuItem, Double> price = new TableColumn<>("Price");
        price.setMinWidth(197);
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.setItems(FXCollections.observableArrayList(foodMenuItems));
        tableView.getColumns().addAll(name, category, price);


        StackPane layout = new StackPane(tableView);
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
        vBox.setAlignment(Pos.CENTER);
//        vBox.setBackground();

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
                salesTransactionTable.getItems().clear();
            }


            if (matchCategory) {
                salesTransactionTable.getItems().clear();
                salesTransactionTable.setItems(FXCollections.observableArrayList(categoryRestrictive));
                salesTransactionTable.setVisible(true);
                nextTime = true;
            }
        });

        salesTransactionTable = new TableView<>();
        TableColumn<FoodMenuItem, String> name = new TableColumn<>("Item Name");
        name.setCellValueFactory(new PropertyValueFactory<>("foodItem"));
        name.setMinWidth(290);

        TableColumn<FoodMenuItem, String> category = new TableColumn<>("Category");
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setMinWidth(210);

        TableColumn<FoodMenuItem, Double> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setMinWidth(197);

        salesTransactionTable.getColumns().addAll(name, category, price);

        salesTransactionTable.setMaxHeight(200);
        salesTransactionTable.setVisible(false);

        HBox hBox = new HBox(30);
        hBox.setAlignment(Pos.CENTER);

        Button buyButton = new Button("BUY");

        Button backButton = new Button("BACK");

        Label numItem = new Label("0");

        backButton.setOnAction(event -> {
            window.close();
            numItem.setText("0");

        });

        buyButton.setOnAction(event -> {
            try {
                numItem.setText("0");
                saleReportItem.add(salesTransactionTable.getSelectionModel().getSelectedItem());
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Choose a category and select a food item");
                alert.showAndWait();
            }
        });

        Button removeItem = new Button("-");
        removeItem.setOnAction(event -> {
            try {
                salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().decrementItemsSold();

                String value = String.valueOf(salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().getItemsSold());

                numItem.setText(value);
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Choose a category and select a food item");
                alert.showAndWait();
            }

        });

        Button addItem = new Button("+");
        addItem.setOnAction(event -> {
            try {
                salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().incrementItemsSold();

                String value = String.valueOf(salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().getItemsSold());

                numItem.setText(value);
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Choose a category and select a food item");
                alert.showAndWait();
            }
        });

        HBox.setMargin(numItem, new Insets(0, 0, 0, 100));

        hBox.getChildren().addAll(buyButton, backButton, numItem, removeItem, addItem);

        vBox.getChildren().addAll(headerText, categories, salesTransactionTable, hBox);

        Scene scene = new Scene(vBox, 700, 400);

        window.setScene(scene);
        window.showAndWait();
    }

    public ArrayList<FoodMenuItem> getSaleReportItem() {
        return saleReportItem;
    }

}
