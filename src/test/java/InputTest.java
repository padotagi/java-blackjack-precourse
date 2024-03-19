import domain.Input;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputTest {

    @Test
    void validateName() {
        //given
        //동일 문자, 블랭크, 공백 포함
        String line = "a,b,aa, ,";
        assertThatThrownBy(() -> new Input().validateName(line))//when
                .isInstanceOf(IllegalArgumentException.class);//then
    }

    @Test
    void duplicateNames() {
        //given
        List<String> names = new ArrayList<>(Arrays.asList("a", "ba", "a"));
        //when
        List<String> duplicates = names.stream()
                .filter(s -> Collections.frequency(names, s) > 1)
                .collect(Collectors.toList());
        //then
        assertThat(duplicates.size()).isEqualTo(2);
    }

    @Test
    void validateMoney() {
        //given
        List<String> lines = new ArrayList<>(Arrays.asList("1", "1,000", "20"));
        for (String line : lines) {
            assertThatThrownBy(() -> new Input().validateMoney(line))//when
                    .isInstanceOf(IllegalArgumentException.class);//then
        }
    }

    @Test
    void validateAnswer() {
        //given
        List<String> lines = new ArrayList<>(Arrays.asList("a", "1", "yes", " ", ""));
        for (String line : lines) {
            assertThatThrownBy(() -> new Input().validateAnswer(line))//when
                    .isInstanceOf(IllegalArgumentException.class);//then
        }
    }
}
