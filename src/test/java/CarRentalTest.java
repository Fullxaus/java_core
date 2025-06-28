import conditions.RentCar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarRentalTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Тест аренды автомобиля с подходящим возрастом")
    public void testRentCar_eligible() {
        RentCar.age = 18;
        RentCar.rentCar();
        assertEquals("Вы можете арендовать автомобиль" + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();
    }

    @Test
    @DisplayName("Тест аренды автомобиля с неподходящим возрастом")
    public void testRentCar_notEligible() {
        RentCar.age = 17;
        RentCar.rentCar();
        assertEquals("Вы не можете арендовать автомобиль" + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();
    }

    @Test
    @DisplayName("Тест аренды автомобиля 2 с подходящим возрастом")
    public void testRentCar2_eligible() {
        RentCar.age = 18;
        RentCar.rentCar2();
        assertEquals("Вы можете арендовать автомобиль" + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();
    }

    @Test
    @DisplayName("Тест аренды автомобиля 2 с неподходящим возрастом")
    public void testRentCar2_notEligible() {
        RentCar.age = 17;
        RentCar.rentCar2();
        assertEquals("Вы не можете арендовать автомобиль" + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();
    }
}


