package SalesManagement;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main.java
 * <p>
 * This is the main class of the project.
 * it comprises the entry point to the program.
 *
 * @author Claude C DE-TCHAMBILA
 * Date: 2019/05/11
 */
public class Main extends Application {

    // ArrayList containing Food items
    static ArrayList<FoodMenuItem> foodMenuItems;

    // Object of AppMenu that initiates the menu containing the main functionalities
    AppMenu appMenu;

    public static void main(String[] args) {

        foodMenuItems = new ArrayList<>();

        // adding suggested food items
        foodMenuItems.add(new FoodMenuItem("Cheese burger", "main meal", 11.50, new Sale()));
        foodMenuItems.add(new FoodMenuItem("Gatsby", "main meal", 8.50, new Sale()));
        foodMenuItems.add(new FoodMenuItem("Cappuccino", "drinks", 10.25, new Sale()));
        foodMenuItems.add(new FoodMenuItem("Hot wings", "starter", 6.00, new Sale()));
        foodMenuItems.add(new FoodMenuItem("Fish and chips", "main meal", 9.50, new Sale()));
        foodMenuItems.add(new FoodMenuItem("Coffee", "drinks", 5.00, new Sale()));
        foodMenuItems.add(new FoodMenuItem("Calamari", "starter", 5.50, new Sale()));


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sales Management");
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            boolean exit = ExitDialog.display();

            if (exit)
                primaryStage.close();
        });

        Pane root = new Pane();
        root.setPrefSize(850, 570);

        Image bgImage = new Image("SalesManagement/bg_salesmanagement.png");

        ImageView backgroundImageView = new ImageView(bgImage);
        backgroundImageView.setFitWidth(850);
        backgroundImageView.setFitHeight(570);

        appMenu = new AppMenu();
        appMenu.setVisible(false);

        Text promptText = new Text("\t\tWelcome!!!\nPlease press [ENTER] to enter");
        promptText.setFill(Color.rgb(240, 240, 240));
        promptText.setFont(promptText.getFont().font(20));

        FadeTransition promptFadeTransition = new FadeTransition(Duration.seconds(0.9), promptText);
        promptFadeTransition.setFromValue(1.0);
        promptFadeTransition.setToValue(0.0);
        promptFadeTransition.setCycleCount(Animation.INDEFINITE);
        promptFadeTransition.play();

        Pane.layoutInArea(appMenu, 225, 210, 500, 280, 0, null, true, true, HPos.LEFT, VPos.TOP, true);

        Pane.layoutInArea(promptText, 290, 300, 200, 50, 0, null, true, true, HPos.LEFT, VPos.TOP, true);

        root.getChildren().addAll(backgroundImageView, promptText, appMenu);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!appMenu.isVisible() && promptText.isVisible()) {
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.6), appMenu);
                    fadeTransition.setFromValue(0);
                    fadeTransition.setToValue(1);


                    appMenu.setVisible(true);
                    fadeTransition.play();
                    promptText.setVisible(false);
                }
            }

            if (event.getCode() == KeyCode.ESCAPE) {
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.6), appMenu);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);

                fadeTransition.setOnFinished(event1 -> appMenu.setVisible(false));
                fadeTransition.play();
                promptText.setVisible(true);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This class provides enables the creation of customized buttons
     * for the main Menu.
     */
    private static class MenuButton extends StackPane {

        private Text text;

        /**
         * @param name name of labelled on the button
         */
        public MenuButton(String name) {
            text = new Text(name);
            text.setFont(text.getFont().font(18));

            text.setFill(Color.WHITE);

            // creating a complex shape
            Path path = new Path();

            // Moving to the starting point
            MoveTo moveTo = new MoveTo(30, 25);

            // creating 1st line
            LineTo line1 = new LineTo(400, 25);

            // creating 2nd line
            LineTo line2 = new LineTo(355, 45);

            // creating 3rd line
            LineTo line3 = new LineTo(400, 65);

            // creating 4th line
            LineTo line4 = new LineTo(30, 65);

            // creating 5th line
            LineTo line5 = new LineTo(75, 45);

            // creating 1st line
            LineTo line6 = new LineTo(30, 25);

            // Adding all elements to the path
            path.getElements().add(moveTo);
            path.getElements().addAll(line1, line2, line3, line4, line5, line6);

            path.setOpacity(0.7);
            path.setFill(Color.BLACK);

            GaussianBlur blur = new GaussianBlur(3.5);

            path.setEffect(blur);

            setAlignment(Pos.CENTER);
            setRotate(0.35);
            getChildren().addAll(path, text);

            setOnMouseEntered(event -> {
                path.setTranslateX(10);
                text.setTranslateY(4);
                path.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseExited(event -> {
                path.setTranslateX(0);
                text.setTranslateY(0);
                path.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            DropShadow dropShadow = new DropShadow(50, Color.WHITE);
            dropShadow.setInput(new Glow());

            // TODO: ADD setMouse event listeners here

        }

    }

    private static class ExitDialog {

        static boolean exit;

        public static boolean display() {
            Stage window = new Stage();

            exit = false;

            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Exit");
            window.setMinWidth(250);
            window.setOnCloseRequest(event -> {
                event.consume();
                window.close();
            });
            window.setResizable(false);


            Label label = new Label("Are you sure you want to exit?");

            Button yesButton = new Button("YES");
            yesButton.setOnAction(event -> {
                exit = true;
                window.close();
            });

            Button noButton = new Button("NO");
            noButton.setOnAction(event -> {
                exit = false;
                window.close();
            });

            HBox hBox = new HBox(10, yesButton, noButton);
            hBox.setAlignment(Pos.CENTER);

            GridPane gridPane = new GridPane();

            gridPane.setPadding(new Insets(10));
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(10);

            GridPane.setConstraints(label, 0, 0);
            GridPane.setConstraints(hBox, 0, 1);

            gridPane.getChildren().addAll(label, hBox);

            Scene scene = new Scene(gridPane);

            window.setScene(scene);
            window.showAndWait();

            return exit;
        }

    }

    /**
     * This class builds the main menu
     * the add sub menu
     * as well as the delete sub menu.
     *
     * It also provides the different procedures to handle
     * the above menus functionalities.
     */
    private class AppMenu extends Parent {


        public AppMenu() {
            VBox firstMenu = new VBox(15);
            Pane addMenu = new Pane();
            Pane deleteMenu = new Pane();

            firstMenu.setTranslateX(100);
            firstMenu.setTranslateY(200);

            addMenu.setTranslateX(100);
            addMenu.setTranslateY(200);

            deleteMenu.setTranslateX(100);
            deleteMenu.setTranslateY(200);


            final int offset = 400;
            addMenu.setTranslateX(offset);

            // ADD button and listener
            MenuButton addButton = new MenuButton("ADD FOOD ITEM");
            addButton.setOnMouseClicked(event -> {
                translateMenus(firstMenu, addMenu, offset);
            });

            MenuButton deleteButton = new MenuButton("DELETE FOOD ITEM");
            deleteButton.setOnMouseClicked(event -> {
                translateMenus(firstMenu, deleteMenu, offset);
            });

            MenuButton sortButton = new MenuButton("SORTED LIST OF FOOD ITEMS");
            sortButton.setOnMouseClicked(event -> {
                AppFunctionalities appFunctionalities = new AppFunctionalities();

                Collections.sort(foodMenuItems);
                appFunctionalities.sortedListOfFoodItems(foodMenuItems);
            });

            MenuButton transactionButton = new MenuButton("SALES TRANSACTION");
            transactionButton.setOnMouseClicked(event -> {
                AppFunctionalities transactionAppFunctionalities = new AppFunctionalities();

                transactionAppFunctionalities.saleTransaction(foodMenuItems);
            });

            MenuButton reportButton = new MenuButton("SALES REPORT");

            // Exit button and listener
            MenuButton exitButton = new MenuButton("EXIT");
            exitButton.setOnMouseClicked(event -> {
                boolean exit = ExitDialog.display();

                if (exit)
                    System.exit(0);
            });

            firstMenu.getChildren().addAll(addButton, deleteButton, sortButton, transactionButton, reportButton, exitButton);

            // creating components of addMenu
            Rectangle rectangle = new Rectangle(480, 250);
            rectangle.setFill(Color.BLACK);
            rectangle.setOpacity(0.6);

            // ADD_MENU Layout
            VBox vBox = new VBox(45);
            vBox.setPadding(new Insets(15, 0, 0, 0));
            vBox.setAlignment(Pos.CENTER);

            // ADD_MENU HEADER
            Text addMenuHeader = new Text("ADD FOOD ITEM");
            addMenuHeader.setFill(Color.rgb(245, 245, 245));
            addMenuHeader.setFont(addMenuHeader.getFont().font(18));

            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);

            TextField itemName = new TextField();
            itemName.setPromptText("item's name");
            HBox.setMargin(itemName, new Insets(0, 0, 0, 20));

            ComboBox<String> itemsCategory = new ComboBox<>();
            itemsCategory.setPromptText("item's category");
            itemsCategory.getItems().addAll("main meal", "drinks", "starter");

            TextField itemPrice = new TextField();
            itemPrice.setPromptText("item's price");

            hBox.getChildren().addAll(itemName, itemsCategory, itemPrice);

            HBox hBox1 = new HBox(15);
            hBox1.setAlignment(Pos.CENTER);

            // ADD item to the menu list
            Button addFoodItem = new Button("ADD");
            addFoodItem.setOnAction(event -> {

                FoodMenuItem item = new FoodMenuItem(itemName.getText(), itemsCategory.getValue(),
                        Double.parseDouble(itemPrice.getText()), new Sale());

                Main.foodMenuItems.add(item);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Food item '" + itemName.getText() + "' has been added to the menu list.");
                alert.showAndWait();

                // empty input fields
                itemName.setText("");
                itemsCategory.setValue("main meal");
                itemPrice.setText("");
            });


            Button backToMainMenu = new Button("BACK");
            backToMainMenu.setOnAction(event -> {
                reverseTranslateMenus(firstMenu, addMenu, offset);
            });

            hBox1.getChildren().addAll(addFoodItem, backToMainMenu);

            vBox.getChildren().addAll(addMenuHeader, hBox, hBox1);


            addMenu.getChildren().addAll(rectangle, vBox);


            getChildren().addAll(firstMenu);

            // DELETE_Menu
            // TODO: continue customizing the delete food items menus here

            VBox vBox1 = new VBox(30);

            TextField searchTextField = new TextField();
            searchTextField.setPromptText("Search for item to delete...");

            Button searchButton = new Button("Search");


            HBox hBox2 = new HBox(12, searchTextField, searchButton);
            hBox2.setAlignment(Pos.CENTER);
            hBox2.setPrefSize(480, 20);

            VBox vBox2 = new VBox(20);
            vBox2.setAlignment(Pos.CENTER);

            Label searchResult = new Label("Item's found");
            searchResult.setTextFill(Color.rgb(245, 245, 245));
            searchResult.setFont(searchResult.getFont().font(18));
            searchResult.setVisible(false);

            Label foundItem = new Label("Food item \t Food item category \t Food price");
            foundItem.setTextFill(Color.rgb(245, 245, 245));
            foundItem.setFont(foundItem.getFont().font(18));
            foundItem.setVisible(false);

            Button deleteItemButton = new Button("DELETE ITEM");
            deleteItemButton.setVisible(false);

            Button backButton = new Button("BACK");

            HBox hBox3 = new HBox(15, deleteItemButton, backButton);
            hBox3.setAlignment(Pos.CENTER);


            vBox2.getChildren().addAll(searchResult, foundItem, hBox3);
            VBox.setMargin(hBox3, new Insets(15, 0, 0, 0));

            vBox2.setAlignment(Pos.CENTER);

            vBox1.getChildren().addAll(hBox2, vBox2);
            VBox.setMargin(hBox2, new Insets(18, 0, 0, 0));

            // Adding all the components to deleteMenu
            Rectangle rectangle1 = new Rectangle(480, 250);
            rectangle1.setFill(Color.BLACK);
            rectangle1.setOpacity(0.6);

            backButton.setOnAction(event -> {
                reverseTranslateMenus(firstMenu, deleteMenu, offset);
                searchTextField.setText("");
                searchResult.setVisible(false);

                foundItem.setVisible(false);
                deleteItemButton.setVisible(false);
            });

            // search for item
            AtomicInteger i = new AtomicInteger();
            searchButton.setOnAction(event -> {
                String checkItem = searchTextField.getText();

                boolean found = false;

                for (i.set(0); i.get() < foodMenuItems.size(); i.getAndIncrement()) {
                    if (checkItem.trim().equalsIgnoreCase(foodMenuItems.get(i.get()).getFooItem().trim())) {
                        found = true;
                        break;
                    }
                }


                if (found) {
                    searchResult.setText("Item " + foodMenuItems.get(i.get()).getFooItem() + " found.");
                    searchResult.setVisible(true);

                    foundItem.setText(String.format("%s \t %s \t %s", foodMenuItems.get(i.get()).getFooItem(), foodMenuItems.get(i.get()).getCategory(), foodMenuItems.get(i.get()).getPrice()));
                    foundItem.setVisible(true);
                    deleteItemButton.setVisible(true);
                } else {
                    searchTextField.setText("");
                    searchResult.setText("Item's " + checkItem + " not found.");
                    searchResult.setVisible(true);

                    foundItem.setVisible(false);
                    deleteItemButton.setVisible(false);
                }

            });

            deleteItemButton.setOnAction(event -> {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Delete Dialog");
                dialog.setHeaderText("You're about to delete a food item");
                dialog.setContentText("Are you sure you want to delete " + foodMenuItems.get(i.get()).getFooItem() +
                        " from the menu list?");

                // set the button types
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

                Optional<ButtonType> result = dialog.showAndWait();

                if (result.get() == ButtonType.YES) {
                    foodMenuItems.remove(i.get());
                    searchTextField.setText("");
                    searchResult.setVisible(false);

                    foundItem.setVisible(false);
                    deleteItemButton.setVisible(false);
                } else {
                    dialog.close();
                    searchTextField.setText("");
                    searchResult.setVisible(false);

                    foundItem.setVisible(false);
                    deleteItemButton.setVisible(false);
                }

            });

            deleteMenu.getChildren().addAll(rectangle1, vBox1);

        }

        public void translateMenus(Pane firstLayout, Pane secondLayout, int offset) {
            getChildren().add(secondLayout);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), firstLayout);
            tt.setToX(firstLayout.getTranslateX() - offset);


            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), secondLayout);
            tt1.setToX(firstLayout.getTranslateX() - 60);

            tt.play();
            tt1.play();

            tt.setOnFinished(event1 -> getChildren().remove(firstLayout));
        }

        public void reverseTranslateMenus(Pane firstLayout, Pane secondLayout, int offset) {
            getChildren().add(firstLayout);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), secondLayout);
            tt.setToX(secondLayout.getTranslateX() + offset);


            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), firstLayout);
            tt1.setToX(secondLayout.getTranslateX() + 60);

            tt.play();
            tt1.play();

            tt.setOnFinished(event1 -> getChildren().remove(secondLayout));
        }

    }

}
