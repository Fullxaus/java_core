
import ru.mentee.power.conditions.TrafficLight;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TrafficLightTest {

    @Test
    void testGetRecommendation_RedSignal() {
        Assertions.assertThat(TrafficLight.getRecommendation("Красный")).isEqualTo("Стой на месте!");
        Assertions.assertThat(TrafficLight.getRecommendation("красный")).isEqualTo("Стой на месте!"); // Проверка регистра
    }

    @Test
    void testGetRecommendation_YellowSignal() {
        Assertions.assertThat(TrafficLight.getRecommendation("Желтый")).isEqualTo("Приготовься, но подожди!");
        Assertions.assertThat(TrafficLight.getRecommendation("ЖЕЛТЫЙ")).isEqualTo("Приготовься, но подожди!");
    }

    @Test
    void testGetRecommendation_GreenSignal() {
        Assertions.assertThat(TrafficLight.getRecommendation("Зеленый")).isEqualTo("Можно переходить дорогу!");
        Assertions.assertThat(TrafficLight.getRecommendation("зеленый")).isEqualTo("Можно переходить дорогу!");
    }



    @Test
    void testGetRecommendation_InvalidSignal() {

        Assertions.assertThat(TrafficLight.getRecommendation("Синий")).isEqualTo("Некорректный сигнал!");
        Assertions.assertThat(TrafficLight.getRecommendation("")).isEqualTo("Некорректный сигнал!");
        Assertions.assertThat(TrafficLight.getRecommendation(null)).isEqualTo("Некорректный сигнал!");
        Assertions.assertThat(TrafficLight.getRecommendation("Абсурдный сигнал")).isEqualTo("Некорректный сигнал!");
        Assertions.assertThat(TrafficLight.getRecommendation("   ")).isEqualTo("Некорректный сигнал!");
    }
}
