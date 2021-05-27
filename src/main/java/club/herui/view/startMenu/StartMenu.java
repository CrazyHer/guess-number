package club.herui.view.startMenu;

import club.herui.model.User;
import club.herui.util.StageAccessor;
import club.herui.view.guessing.GuessGame;
import club.herui.view.rankingList.RankingList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class StartMenu extends Pane {
    private Button startGameBtn, rankListBtn, exitBtn;
    private TextField nameInput;

    public StartMenu() {
        super();
        setBackground(new Background(new BackgroundImage(new Image("menubg.PNG"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        nameInput = new TextField();
        nameInput.setLayoutX(320);
        nameInput.setLayoutY(241);
        nameInput.setAlignment(Pos.CENTER);

        startGameBtn = new Button("开始游戏");
        startGameBtn.setLayoutX(354);
        startGameBtn.setLayoutY(280);
        startGameBtn.setPrefHeight(32);
        startGameBtn.setPrefWidth(92);
        startGameBtn.setOnAction((e) -> handleStartGameBtn());

        rankListBtn = new Button("排行榜");
        rankListBtn.setLayoutX(354);
        rankListBtn.setLayoutY(323);
        rankListBtn.setPrefHeight(32);
        rankListBtn.setPrefWidth(92);
        rankListBtn.setOnAction((e) -> getScene().setRoot(new RankingList()));

        exitBtn = new Button("退出游戏");
        exitBtn.setLayoutX(354);
        exitBtn.setLayoutY(367);
        exitBtn.setPrefHeight(32);
        exitBtn.setPrefWidth(92);
        exitBtn.setOnAction((e) -> StageAccessor.closeApp());

        getChildren().addAll(nameInput, startGameBtn, rankListBtn, exitBtn);

    }

    private void handleStartGameBtn() {
        if (nameInput.getText().trim().length() > 0) {
            new User(nameInput.getText().trim());
            getScene().setRoot(new GuessGame());
        } else {
            var alert = new Alert(Alert.AlertType.WARNING, "输入用户名才能开始游戏哦！");
            alert.setTitle("请输入用户名");
            alert.show();
        }
        ;
    }
}
