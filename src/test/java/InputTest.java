import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputTest {

    @ParameterizedTest
    @CsvSource({" "})
    void validateName(String line) {//given
        assertThatThrownBy(() -> new Input().validateName(line))//when
                .isInstanceOf(IllegalArgumentException.class);//then
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
