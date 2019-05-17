package SalesManagement;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * AppFunctionalities.java
 * <p>
 * This class creates the windows of different functionalities
 * of the main program
 *
 * @author CC DE-TCHAMBILA & CK MBUYI
 * Date: 2019/05/11
 */
public class AppFunctionalities {

    private ComboBox<String> categories;
    private ArrayList<FoodMenuItem> categoryRestrictive;
    private boolean nextTime;

    private TableView<FoodMenuItem> salesTransactionTable;

    private static ArrayList<FoodMenuItemSaleReport> saleReportItem;

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
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
//        vBox.setBackground();

        saleReportItem = new ArrayList<>();

        Text headerText = new Text("Sales Transaction");
        headerText.setFont(headerText.getFont().font(16));

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

        Label priceItem = new Label("R0");

        Label numItem = new Label("0");

        backButton.setOnAction(event -> {
            window.close();
            numItem.setText("0");

        });

        buyButton.setOnAction(event -> {
            try {
                priceItem.setText("R0");
                numItem.setText("0");
                saleReportItem.add(new FoodMenuItemSaleReport(salesTransactionTable.getSelectionModel().getSelectedItem()));

                System.out.println(saleReportItem.size());
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

                double unitPrice = salesTransactionTable.getSelectionModel().getSelectedItem().getPrice();


                String priceValue = String.valueOf(salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().getTotalSalesValue());

                salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().incrementTotalSalesValue(unitPrice);

                String value = String.valueOf(salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().getItemsSold());

                priceItem.setText("R" + priceValue);
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

                double unitPrice = salesTransactionTable.getSelectionModel().getSelectedItem().getPrice();

                salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().incrementTotalSalesValue(unitPrice);

                String priceValue = String.valueOf(salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().getTotalSalesValue());

                String value = String.valueOf(salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().getItemsSold());

                priceItem.setText("R" + priceValue);
                numItem.setText(value);
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Choose a category and select a food item");
                alert.showAndWait();
            }
        });

        salesTransactionTable.setOnMouseClicked(event -> {
            String unitValue = String.valueOf(salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().getTotalSalesValue());
            String value = String.valueOf(salesTransactionTable.getSelectionModel().getSelectedItem().getSaleInfo().getItemsSold());

            priceItem.setText("R" + unitValue);
            numItem.setText(value);
        });

        HBox.setMargin(priceItem, new Insets(0, 0, 0, 100));

        hBox.getChildren().addAll(buyButton, backButton, priceItem, numItem, removeItem, addItem);

        vBox.getChildren().addAll(headerText, categories, salesTransactionTable, hBox);

        Scene scene = new Scene(vBox, 700, 400);

        window.setScene(scene);
        window.showAndWait();
    }

    public void salesReport() {
        Stage window = new Stage();
        window.setTitle("Sales Report");
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);

        TableView<FoodMenuItemSaleReport> tableView;

        // item column
        TableColumn<FoodMenuItemSaleReport, String> itemColumn = new TableColumn<>("Item");
        itemColumn.setMinWidth(230);
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        // sales count column
        TableColumn<FoodMenuItemSaleReport, Integer> salesCountColumn = new TableColumn<>("Sales Count");
        salesCountColumn.setMinWidth(145);
        salesCountColumn.setCellValueFactory(new PropertyValueFactory<>("salesCount"));

        // item column
        TableColumn<FoodMenuItemSaleReport, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setMinWidth(213);
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        tableView = new TableView<>();
        tableView.setItems(FXCollections.observableArrayList(saleReportItem));
        tableView.getColumns().addAll(itemColumn, salesCountColumn, totalColumn);

        Button backButton = new Button("BACK");

        backButton.setOnAction(event -> {
            window.close();
        });

        // Sales_Report menu
        VBox vBox3 = new VBox(20);
        vBox3.setPadding(new Insets(15, 5, 10, 5));
        Text salesReportMenuHeader = new Text("Joe’s Fast-Food Joint");


        Text date = new Text(LocalDate.now().toString());
        date.setFont(date.getFont().font(16));

        salesReportMenuHeader.setFont(salesReportMenuHeader.getFont().font(16));
        vBox3.setAlignment(Pos.CENTER);


        HBox bottomBox = new HBox(30);
        bottomBox.setAlignment(Pos.CENTER);
        Label totalSaleText = new Label("Today’s Total Sales:");
        totalSaleText.setFont(totalSaleText.getFont().font(16));

        // computing the total amount

        double amount = 0;

        if (saleReportItem.size() > 0) {
            for (FoodMenuItemSaleReport f : saleReportItem) {
                amount += f.getTotal();
            }
        }

        Label totalAmount = new Label("R" + amount);
        bottomBox.getChildren().addAll(totalSaleText, totalAmount);


        vBox3.getChildren().addAll(salesReportMenuHeader, date, tableView, bottomBox, backButton);



        StackPane layout = new StackPane();

        layout.getChildren().addAll(vBox3);

        Scene scene = new Scene(layout, 600, 400);

        window.setScene(scene);
        window.showAndWait();
    }

}
