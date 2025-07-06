package ru.mentee.power.conditions;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class LogicalTrainerTest {

    @Test
    void testEvaluateLogic_AllTrue() {
        Map<Integer, Boolean> results = LogicalTrainer.evaluateLogic(true, true, true);
        assertThat(results.get(1)).as("1: Prog && Math").isTrue();
        assertThat(results.get(2)).as("2: Prog || Reading").isTrue();
        assertThat(results.get(3)).as("3: Math && !Reading").isFalse();
        assertThat(results.get(4)).as("4: !Prog && !Math").isFalse();
        assertThat(results.get(5)).as("5: Prog || Math || Reading").isTrue();
        assertThat(results.get(6)).as("6: Dislikes == 2").isFalse(); // 0 dislikes
    }

    @Test
    void testEvaluateLogic_ProgAndMathOnly() {
        Map<Integer, Boolean> results = LogicalTrainer.evaluateLogic(true, true, false);
        assertThat(results.get(1)).as("1: Prog && Math").isTrue();
        assertThat(results.get(2)).as("2: Prog || Reading").isTrue();
        assertThat(results.get(3)).as("3: Math && !Reading").isTrue();
        assertThat(results.get(4)).as("4: !Prog && !Math").isFalse();
        assertThat(results.get(5)).as("5: Prog || Math || Reading").isTrue();
        assertThat(results.get(6)).as("6: Dislikes == 2").isFalse(); // 1 dislike
    }

    @Test
    void testEvaluateLogic_ReadingOnly() {
        Map<Integer, Boolean> results = LogicalTrainer.evaluateLogic(false, false, true);
        assertThat(results.get(1)).as("1: Prog && Math").isFalse();
        assertThat(results.get(2)).as("2: Prog || Reading").isTrue();
        assertThat(results.get(3)).as("3: Math && !Reading").isFalse();
        assertThat(results.get(4)).as("4: !Prog && !Math").isTrue();
        assertThat(results.get(5)).as("5: Prog || Math || Reading").isTrue();
        assertThat(results.get(6)).as("6: Dislikes == 2").isTrue(); // 2 dislikes
    }


    // 🔥 ВАША ЗАДАЧА: Дописать этот тест! 🔥
    @Test
    void testEvaluateLogic_AllFalse() {
        // Используя assertThat, проверьте случай, когда все три ответа - false.
        // Ожидаемые результаты: 1:F, 2:F, 3:F, 4:T, 5:F, 6:F (3 dislikes)
        Map<Integer, Boolean> results = LogicalTrainer.evaluateLogic(false, false, false);
        assertThat(results.get(1)).as("1: Prog && Math").isFalse();
        assertThat(results.get(2)).as("2: Prog || Reading").isFalse();
        assertThat(results.get(3)).as("3: Math && !Reading").isFalse();
        assertThat(results.get(4)).as("4: !Prog && !Math").isTrue();
        assertThat(results.get(5)).as("5: Prog || Math || Reading").isFalse();
        assertThat(results.get(6)).as("6: Dislikes == 2").isFalse(); // 3 dislikes
    }
}
