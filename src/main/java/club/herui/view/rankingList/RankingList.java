package club.herui.view.rankingList;

import club.herui.util.LocalStorage;
import club.herui.util.StageAccessor;
import club.herui.view.startMenu.StartMenu;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.Arrays;

public class RankingList extends Pane {
    private Button returnBtn;

    public RankingList() {
        StageAccessor.setSize(480, 720);
        setBackground(new Background(new BackgroundImage(new Image("rankinglistbg.jpeg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        Label label = new Label("排名");
        label.setLayoutX(8);
        label.setLayoutY(25);
        label.setFont(Font.font(26));
        getChildren().add(label);

        label = new Label("用户名");
        label.setLayoutX(180);
        label.setLayoutY(25);
        label.setFont(Font.font(26));
        getChildren().add(label);

        label = new Label("最高分");
        label.setLayoutX(379);
        label.setLayoutY(25);
        label.setFont(Font.font(26));
        getChildren().add(label);

        returnBtn = new Button("返回");
        returnBtn.setLayoutX(400);
        returnBtn.setLayoutY(606);
        returnBtn.setOnAction(event -> handleReturnBtnClick());
        getChildren().add(returnBtn);

        renderList();
    }

    private void renderList() {
        Pair<String, String>[] records = LocalStorage.getAll();
        // 对记录进行降序排序
        Arrays.sort(records, (o1, o2) -> Integer.parseInt(o2.getValue()) - Integer.parseInt(o1.getValue()));
        for (int i = 0; i < (Math.min(records.length, 15)); i++) {
            Label label = new Label(String.valueOf(i + 1));
            label.setLayoutX(25);
            label.setLayoutY(80 + (30) * i);
            label.setFont(Font.font(18));
            getChildren().add(label);

            label = new Label(records[i].getKey());
            label.setLayoutX(180);
            label.setLayoutY(80 + (30) * i);
            label.setFont(Font.font(18));
            getChildren().add(label);

            label = new Label(records[i].getValue());
            label.setLayoutX(412);
            label.setLayoutY(80 + (30) * i);
            label.setFont(Font.font(18));
            getChildren().add(label);

        }
    }

    private void handleReturnBtnClick() {
        getScene().setRoot(new StartMenu());
        StageAccessor.setSize(800, 560);
    }
}