package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoWhileLoopExampleTest {

    @Test
    void testRepeatAction_SeveralRepeats() {
        // Arrange
        DoWhileLoopExample example = new DoWhileLoopExample();
        String[] answers = {"да", "да", "нет"};

        // Act
        int count = example.repeatAction(answers);

        // Assert
        assertEquals(2, count);
    }

    @Test
    void testRepeatAction_NoRepeats() {
        // Arrange
        DoWhileLoopExample example = new DoWhileLoopExample();
        String[] answers = {"нет"};

        // Act
        int count = example.repeatAction(answers);

        // Assert
        assertEquals(0, count);
    }

    @Test
    void testRepeatAction_EmptyArray() {
        // Arrange
        DoWhileLoopExample example = new DoWhileLoopExample();
        String[] answers = {};

        // Act
        int count = example.repeatAction(answers);

        // Assert
        assertEquals(0, count);
    }

    @Test
    void testRepeatAction_NullArray() {
        // Arrange
        DoWhileLoopExample example = new DoWhileLoopExample();
        String[] answers = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> example.repeatAction(answers));
    }
}
