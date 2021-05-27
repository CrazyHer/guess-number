package club.herui.view.guessing;

import java.util.Random;

public class Judger {
    private String randomNum;

    public Judger() {
        changeNum();
    }

    public void changeNum() {
        randomNum = String.format("%04d", new Random().nextInt(9999));
        System.out.println(randomNum);
    }

    public String judge(String value) {
        int m = 0, n = 0;
        boolean[] randomNumMarked = new boolean[4];
        boolean[] valueMarked = new boolean[4];

        for (int i = 0; i < 4; i++) {
            if (randomNum.charAt(i) == value.charAt(i)) {
                m++;
                randomNumMarked[i] = true;
                valueMarked[i] = true;
                continue;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!valueMarked[i] && !randomNumMarked[j] && value.charAt(i) == randomNum.charAt(j)) {
                    n++;
                    valueMarked[i] = true;
                    randomNumMarked[j] = true;
                    break;
                }
            }
        }
        return "" + m + "A" + n + "B";
    }

    public String getAnswer() {
        return randomNum;
    }
}
