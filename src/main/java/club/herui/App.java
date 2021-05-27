package club.herui;

import club.herui.util.LocalStorage;
import club.herui.util.StageAccessor;
import club.herui.view.startMenu.StartMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX 猜数字游戏
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        LocalStorage.init();
        StageAccessor.setStage(primaryStage);
        primaryStage.setTitle("猜数字游戏 By HR");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(new StartMenu(), 800, 560));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}