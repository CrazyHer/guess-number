package club.herui.view.guessing;

import club.herui.model.User;
import club.herui.view.rankingList.RankingList;
import club.herui.view.startMenu.StartMenu;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GuessGame extends Pane {
    private TextField textField;
    private Label hint, score, timerLabel, record;
    private Button submitBtn, changeNumBtn, rankingListBtn, returnBtn;
    private ProgressBar progressBar;
    private Judger judger; // 评判器，评判每次猜数结果
    private BooleanProperty isStart;
    private User user;

    public GuessGame() {
        user = User.currentUser;
        setBackground(new Background(new BackgroundImage(new Image("guessingbg.jpeg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        Label label = new Label("您好，" + user.getName() + "!\n\n请输入您猜的四位数字:");
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(324.0);
        label.setLayoutY(87.0);
        label.setPrefHeight(48.0);
        label.setPrefWidth(161.0);
        label.setTextAlignment(TextAlignment.CENTER);
        getChildren().add(label);

        textField = new TextField();
        textField.setLayoutX(324.0);
        textField.setLayoutY(135.0);
        textField.setPrefHeight(65.0);
        textField.setPrefWidth(161.0);
        textField.setAlignment(Pos.CENTER);
        textField.setFont(Font.font(31));
        // 设置过滤器，只允许输入最多4位数字
        textField.setTextFormatter(new TextFormatter<String>((change) -> {
            // 添加对退格等输入的特殊支持
            char inputChar = change.getText().length() > 0 ? change.getText().charAt(0) : '\0';
            if (inputChar >= '0' && inputChar <= '9' && textField.getText().length() <= 3 || inputChar == '\0')
                return change;
            else return null;
        }));

        submitBtn = new Button("确认");
        submitBtn.setLayoutX(380);
        submitBtn.setLayoutY(209);

        changeNumBtn = new Button("换一个数");
        changeNumBtn.setLayoutX(368);
        changeNumBtn.setLayoutY(368);
        changeNumBtn.setOnAction(event -> handleChangeNum());

        rankingListBtn = new Button("排行榜");
        rankingListBtn.setLayoutX(710);
        rankingListBtn.setLayoutY(449);

        returnBtn = new Button("返回");
        returnBtn.setLayoutX(710);
        returnBtn.setLayoutY(482);

        progressBar = new ProgressBar(1.0);
        progressBar.setLayoutX(300.0);
        progressBar.setLayoutY(29.0);
        progressBar.setPrefWidth(200);

        HBox hBox = new HBox();
        label = new Label("还剩 ");
        hBox.getChildren().add(label);
        timerLabel = new Label("60");
        hBox.getChildren().add(timerLabel);
        label = new Label(" s");
        hBox.getChildren().add(label);
        hBox.setLayoutX(379.0);
        hBox.setLayoutY(14.0);


        label = new Label("提示：");
        label.setLayoutX(386.0);
        label.setLayoutY(258.0);
        getChildren().add(label);

        hint = new Label("?");
        hint.setFont(Font.font(31.0));
        hint.setAlignment(Pos.CENTER);
        hint.setContentDisplay(ContentDisplay.CENTER);
        hint.setLayoutX(324.0);
        hint.setLayoutY(273.0);
        hint.setPrefHeight(65);
        hint.setPrefWidth(161);

        hint.setStyle("-fx-border-color: black");

        label = new Label("规则：\n" +
                "玩家输入所猜第一个数字\n" +
                "后开始60s倒计时，此时\n" +
                "玩家需要在有限的时间内\n" +
                "尽可能多地猜中系统随机\n" +
                "生成的4位数。每猜中一次\n" +
                "玩家本局积分+1\n" +
                "每猜一次数，游戏都将给\n" +
                "出提示：“完全猜中的数字\n" +
                "个数”和“猜中数字但位置\n" +
                "错误的数字个数”，比如\n" +
                "nAmB，数字n表示猜中的\n" +
                "位置正确的数字个数，数\n" +
                "字m表示数字正确而位置不\n" +
                "对的数字个数。\n");
        label.setLayoutX(93);
        label.setLayoutY(93);
        label.setPrefHeight(254.0);
        label.setPrefWidth(161.0);
        getChildren().add(label);

        label = new Label("当前得分：");
        label.setLayoutX(572.0);
        label.setLayoutY(87);
        getChildren().add(label);

        score = new Label();
        score.setAlignment(Pos.CENTER);
        score.setLayoutX(587.0);
        score.setLayoutY(120);
        score.setPrefHeight(100.0);
        score.setPrefWidth(100.0);
        score.textProperty().bind(user.getScoreProperty().asString());
        score.setFont(Font.font(45.0));

        label = new Label("最高得分：");
        label.setLayoutX(572);
        label.setLayoutY(258);
        getChildren().add(label);

        record = new Label();
        record.setAlignment(Pos.CENTER);
        record.setLayoutX(590);
        record.setLayoutY(291);
        record.setPrefHeight(100);
        record.setPrefWidth(100);
        record.textProperty().bind(user.getRecordProperty().asString());
        record.setFont(Font.font(45));

        getChildren().addAll(textField, hint, score, hBox, record, submitBtn, changeNumBtn, rankingListBtn, returnBtn, progressBar);

        isStart = new SimpleBooleanProperty(false);
        //绑定按钮事件
        returnBtn.setOnAction((e) -> getScene().setRoot(new StartMenu()));
        rankingListBtn.setOnAction((e) -> getScene().setRoot(new RankingList()));
        submitBtn.setOnAction((e) -> handleSubmit());
        textField.setOnAction(event -> handleSubmit());

        judger = new Judger();
    }

    private void handleSubmit() {
        if (textField.getText().length() < 4) {
            hint.setText("?");
            return;
        }
        if (!isStart.get()) {
            // 生成随机数
            judger.changeNum();
            // 开始倒计时，在Task线程中更新UI
            Task task = new Task<Integer>() {
                @Override
                protected Integer call() {
                    updateValue(60);
                    updateProgress(60, 60);
                    for (int i = 60; i > 0; i--) {
                        updateValue(i);
                        updateProgress(i, 60);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isStart.set(false);
                    return 0;
                }
            };
            timerLabel.textProperty().bind(task.valueProperty().asString());
            progressBar.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded((e) -> user.setScore(0)); // 重置本局得分
            var thread = new Thread(task);
            thread.setDaemon(true);
            isStart.set(true);
            thread.start();
        }

        // 本局游戏已开始，判断猜数结果
        String res = judger.judge(textField.getText());
        if (res.equals("4A0B")) {
            hint.setText("猜对啦！");
            user.setScore(user.getScore() + 1);
            if (user.getScore() > user.getRecord()) user.setRecord(user.getScore());
            judger.changeNum();
        } else hint.setText(res);

    }

    private void handleChangeNum() {
        textField.clear();
        hint.setText("?");
        judger.changeNum();
    }

}

