package ru.mentee.power.finaltrack.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTests {

    @Test
    void testConstructorAndGetters() {
        var tx = new Transaction(1, Transaction.Type.INCOME, new BigDecimal("1000"), Transaction.Category.SALARY, "Моя зарплата");
        assertEquals(tx.getId(), 1);
        assertEquals(tx.getType(), Transaction.Type.INCOME);
        assertEquals(tx.getAmount(), new BigDecimal("1000"));
        assertEquals(tx.getCategory(), Transaction.Category.SALARY);
        assertEquals(tx.getDescription(), "Моя зарплата");
        assertEquals(tx.getDate(), LocalDate.now());
    }

    @Test
    void testEnums() {
        assertSame(Transaction.Type.INCOME.ordinal(), 0);
        assertSame(Transaction.Type.EXPENSE.ordinal(), 1);
        assertSame(Transaction.Category.SALARY.ordinal(), 0);
        assertSame(Transaction.Category.FOOD.ordinal(), 1);
    }

    @Test
    void testToString() {
        var tx = new Transaction(1, Transaction.Type.INCOME, new BigDecimal("1000"), Transaction.Category.SALARY, "Моя зарплата");
        assertTrue(tx.toString().contains("SALARY"));
    }
}
