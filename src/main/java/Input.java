import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;

public class Input {

    public String inputName() {
        System.out.println(Print.INPUT_NAME);
        String names = Console.readLine();
        try {
            validateName(names);
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.getStackTrace();
            return inputName();
        }
        return names;
    }

    public double inputMoney(String name) {
        System.out.println(name + Print.INPUT_MONEY);
        String money = Console.readLine();
        try {
            validateMoney(money);
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.getStackTrace();
            return inputMoney(name);
        }
        return Double.parseDouble(money);
    }

    public String inputYn(String player) {
        System.out.println(player + Print.ASK_GIVE_ONE_MORE_CARD);
        String answer;
        try {
            answer = Console.readLine().toUpperCase();
            validateAnswer(answer);
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.getStackTrace();
            return inputYn(player);
        }
        return answer;
    }

    public void validateName(String line) {
        String[] names = line.split(Print.DELIMITER);

        boolean hasInvalid = Arrays.stream(names)
                .anyMatch(name -> name == null || name.trim().isEmpty());

        if (hasInvalid) {
            throw new IllegalArgumentException(Print.INPUT_NAME_ERROR);
        }
    }

    public void validateMoney(String money) {
        if (Integer.parseInt(money) % 1000 != 0) {
            throw new IllegalArgumentException(Print.INPUT_MONEY_ERROR);
        }
    }

    public void validateAnswer(String answer) {
        if (!"Y".equals(answer) && !"N".equals(answer)) {
            throw new IllegalArgumentException(Print.ASK_GIVE_ONE_MORE_CARD_ERROR);
        }
    }
}
