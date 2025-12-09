import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class HippodromeTest {


    @Test
    void whenHorsesListIsNull() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }
    @Test
    void whenHorsesListIsEmpty() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        // Создаем список из 30 лошадей
        List<Horse> horses = IntStream.range(0, 30)
                .mapToObj(i -> new Horse("Horse" + i, i, i))
                .collect(Collectors.toList());

        // Создаем объект Hippodrome
        Hippodrome hippodrome = new Hippodrome(horses);

        // Проверяем, что возвращаемый список содержит те же объекты в той же последовательности
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(spy(new Horse("Test" + i, 1)));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        horses.forEach(horse -> verify(horse).move());
    }

    @Test
    void getWinner() {
        Horse horse1 = new Horse("horse1", 1, 10);
        Horse horse2 = new Horse("horse2", 1, 20);
        Horse horse3 = new Horse("horse3", 1, 15);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));
        assertEquals(horse2, hippodrome.getWinner());
    }
}