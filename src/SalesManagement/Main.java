package SalesManagement;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main.java
 *
 * This is the main class of the project.
 * it comprises the entry point to the program.
 *
 * @author Claude C DE-TCHAMBILA
 * Date: 2019/05/11
 */
public class Main extends Application {

    // Object of AppMenu that initiates the menu containing the main functionalities
    AppMenu appMenu;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sales Management");

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

        Pane.layoutInArea(appMenu, 225, 210, 430, 280, 0, null, true, true, HPos.LEFT, VPos.TOP, true);

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
         *
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
                text.setTranslateY(5);
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

    /**
     *
     * This class builds the menu for the app
     */
    private class AppMenu extends Parent {

        public AppMenu() {
            VBox firstMenu = new VBox(15);
            VBox secondMenu = new VBox(15);

            firstMenu.setTranslateX(100);
            firstMenu.setTranslateY(200);

            secondMenu.setTranslateX(100);
            secondMenu.setTranslateY(200);

            final int offset = 400;
            secondMenu.setTranslateX(offset);

            MenuButton addButton = new MenuButton("ADD FOOD ITEM");
            // TODO: ADD addButton listener

            MenuButton deleteButton = new MenuButton("DELETE FOOD ITEM");

            MenuButton sortButton = new MenuButton("SORTED LIST OF FOOD ITEMS");

            MenuButton transactionButton = new MenuButton("SALES TRANSACTION");

            MenuButton reportButton = new MenuButton("SALES REPORT");

            MenuButton exitButton = new MenuButton("EXIT");

            firstMenu.getChildren().addAll(addButton, deleteButton, sortButton, transactionButton, reportButton, exitButton);

            getChildren().addAll(firstMenu);
        }

    }

}
