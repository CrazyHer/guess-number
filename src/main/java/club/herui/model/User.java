package club.herui.model;

import club.herui.util.LocalStorage;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

public class User {
    public static User currentUser;
    private StringProperty name;
    private IntegerProperty score, record;

    public User(String name) {
        currentUser = this;
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(0);
        if (LocalStorage.getString(name) == null) {
            try {
                LocalStorage.setString(name, "0");
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.record = new SimpleIntegerProperty(0);
        } else this.record = new SimpleIntegerProperty(Integer.parseInt(LocalStorage.getString(name)));

    }

    public String getName() {
        return name.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public void setRecord(int record) {
        this.record.set(record);
        try {
            LocalStorage.setString(name.get(), String.valueOf(record));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getScore() {
        return score.get();
    }

    public int getRecord() {
        return record.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public IntegerProperty getScoreProperty() {
        return score;
    }

    public IntegerProperty getRecordProperty() {
        return record;
    }
}
