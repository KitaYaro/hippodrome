
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;


class HorseTest {
    private Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse("Horse", 10, 10);
    }

    @Test
    void whenFirstParamConstructorNull() {
        // Проверяем, что выбрасывается IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 10, 10));
        // Проверяем сообщение исключения
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\t ", "  "})
    void whenFirstParamConstructorIsBlank(String name) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 10, 10));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }
    @Test
    void whenSpeedParamConstructorNegative() {
    IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Horse("Horse", -10, 10));
    assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void whenDistanceParamConstructorNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Horse", 10, -10));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getNameReturnsCorrectValue() {
        String expected = "Horse";
        assertEquals(expected, horse.getName());
    }

    @Test
    void getSpeedReturnsCorrectValue() {
        double expected = 10;
        assertEquals(expected, horse.getSpeed());
    }

    @Test
    void getDistanceReturnsCorrectValue() {
        double expected = 10;
        assertEquals(expected, horse.getDistance());
    }

    @Test
    void getDistanceWithTwoParamConstructor() {
        Horse horse = new Horse("TestHorse", 5.0);
        double expected = 0;
        assertEquals(expected, horse.getDistance());
    }

    @Test
    void moveUpdatesDistanceAccordingToFormula() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            double randomValueMock = 0.5; // Значение, которое вернет мок
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValueMock);

            double initialDistance = horse.getDistance(); // Получаем начальное значение
            double speed = horse.getSpeed(); // Получаем скорость

            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            // Расчет ожидаемого результата по формуле
            double expectedDistance = initialDistance + speed * randomValueMock;
            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}