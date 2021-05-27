package club.herui.util;

import javafx.stage.Stage;

public class StageAccessor {
    private static Stage stage;

    public static void setStage(Stage theStage) {
        stage = theStage;
    }

    public static boolean hasStage() {
        return stage != null;
    }

    public static Stage getStage() {
        if (stage != null) return stage;
        else throw new NullPointerException("Stage is null!");
    }

    public static void closeApp() {
        if (stage != null) stage.close();
    }

    public static void setTitle(String title) {
        if (stage != null) stage.setTitle(title);
    }

    public static void setResizable(boolean value) {
        if (stage != null) stage.setResizable(value);
    }
    public static void setSize(double width,double height){
        if (stage!=null) {
            stage.setWidth(width);
            stage.setHeight(height);
        }
    }
}
