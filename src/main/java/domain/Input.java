package domain;

import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Input {

    public static final String INPUT_NAME = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)";
    public static final String INPUT_NAME_ERROR = "[ERROR] 공백/여백은 이름으로 사용할 수 없습니다.";
    public static final String INPUT_MONEY = "의 배팅 금액은?";
    public static final String INPUT_MONEY_ERROR = "[ERROR] 1000의 배수로 금액을 입력하세요.";
    public static final String ASK_GIVE_ONE_MORE_CARD = "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)";
    public static final String ASK_GIVE_ONE_MORE_CARD_ERROR = "[ERROR] y 혹은 n을 입력하세요.(대/소문자 구분 없음)";

    public String inputName() {
        System.out.println(INPUT_NAME);
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
        System.out.println(name + INPUT_MONEY);
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
        System.out.println(player + ASK_GIVE_ONE_MORE_CARD);
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
        List<String> names = Arrays.asList(line.split(Print.DELIMITER));

        boolean hasInvalid = names.stream()
                .anyMatch(name -> name == null || name.trim().isEmpty());

        if (hasInvalid || duplicateNames(names)) {
            throw new IllegalArgumentException(INPUT_NAME_ERROR);
        }
    }

    public boolean duplicateNames(List<String> names) {
        List<String> duplicates = names.stream()
                .filter(s -> Collections.frequency(names, s) > 1)
                .collect(Collectors.toList());
        return duplicates.size() > 0;
    }

    public void validateMoney(String money) {
        if (Integer.parseInt(money) % 1000 != 0) {
            throw new IllegalArgumentException(INPUT_MONEY_ERROR);
        }
    }

    public void validateAnswer(String answer) {
        if (!"Y".equals(answer) && !"N".equals(answer)) {
            throw new IllegalArgumentException(ASK_GIVE_ONE_MORE_CARD_ERROR);
        }
    }
}
