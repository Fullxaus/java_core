import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import ru.mentee.power.variables.Variables;

import static org.junit.jupiter.api.Assertions.*;

public class VariablesTest {

    @Test
    @DisplayName("Проверка геттеров getName и getAge")
    public void testGetters() {
        Variables variables = new Variables(30, "Dmitrii");
        assertEquals(30, variables.getAge(), "Возраст должен быть 30");
        assertEquals("Dmitrii", variables.getName(), "Имя должно быть Dmitrii");
    }

    @Test
    @DisplayName("Проверка сеттеров setName и setAge")
    public void testSetters() {
        Variables variables = new Variables(25, "Anna");
        variables.setAge(35);
        variables.setName("Ivan");
        assertEquals(35, variables.getAge(), "Возраст должен быть изменен на 35");
        assertEquals("Ivan", variables.getName(), "Имя должно быть изменено на Ivan");
    }

    @Test
    @DisplayName("Проверка значений null и пустой строки для имени")
    public void testNameNullOrEmpty() {
        Variables variables = new Variables(23, null);
        assertNull(variables.getName(), "Имя должно быть null");

        variables.setName("");
        assertEquals("", variables.getName(), "Имя должно быть пустой строкой");
    }

    @Test
    @DisplayName("Проверка больших значений возраста")
    public void testAgeBoundaries() {
        Variables variables = new Variables(0, "Test"); // минимально допустимый возраст
        assertEquals(0, variables.getAge(), "Возраст должен быть 0");

        variables.setAge(150); // очень большой показатель возраста
        assertEquals(150, variables.getAge(), "Возраст должен быть 150");

        variables.setAge(-1);
        assertEquals(-1, variables.getAge(), "Возраст может быть отрицательным (проверка текущей логики)");
    }



}
