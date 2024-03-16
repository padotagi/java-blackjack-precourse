import domain.Input;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputTest {

    @ParameterizedTest
    @CsvSource({" "})
    void validateName(String line) {//given
        assertThatThrownBy(() -> new Input().validateName(line))//when
                .isInstanceOf(IllegalArgumentException.class);//then
    }

    @Test
    void duplicateNames() {
        //given
        List<String> names = new ArrayList<>();
        names.add("a");
        names.add("ba");
        names.add("a");
        //when
        List<String> duplicates = names.stream()
                .filter(s -> Collections.frequency(names, s) > 1)
                .collect(Collectors.toList());
        //then
        assertThat(duplicates.size()).isEqualTo(2);
    }

    @ParameterizedTest
    @CsvSource({""})
    void validateMoney(String line) {
        assertThatThrownBy(() -> new Input().validateMoney(line))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({""})
    void validateAnswer(String line) {
        assertThatThrownBy(() -> new Input().validateAnswer(line))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
