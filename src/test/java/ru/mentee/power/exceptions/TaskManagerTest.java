package ru.mentee.power.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TaskManagerTest {

    private TaskManager account;
    private static final double INITIAL_BALANCE = 1000.0;
    private static final String ACCOUNT_ID = "ACC-123";

    @BeforeEach
    void setUp() {
        // Создать новый экземпляр TaskManager с начальными значениями
        account = new TaskManager(ACCOUNT_ID, INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Конструктор должен правильно устанавливать начальный баланс и ID")
    void constructorShouldSetInitialBalanceAndId() {
        assertThat(account.getId())
                .as("ID счёта должно быть таким же, как в конструкторе")
                .isEqualTo(ACCOUNT_ID);

        assertThat(account.getBalance())
                .as("Начальный баланс должно быть таким же, как передано в конструкторе")
                .isEqualTo(INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Конструктор должен выбрасывать IllegalArgumentException при отрицательном балансе")
    void constructorShouldThrowIllegalArgumentExceptionForNegativeBalance() {
        // при отрицательном балансе
        assertThatThrownBy(() -> new TaskManager(ACCOUNT_ID, -10.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Начальный баланс не может быть отрицательным");
    }

    // --- Тесты для deposit ---

    @Test
    @DisplayName("Метод deposit должен увеличивать баланс при положительной сумме")
    void depositShouldIncreaseBalanceForPositiveAmount() {
        account.deposit(500.0);
        assertThat(account.getBalance())
                .isEqualTo(INITIAL_BALANCE + 500.0);
    }

    @Test
    @DisplayName("Метод deposit должен допускать нулевую сумму")
    void depositShouldAllowZeroAmount() {
        account.deposit(0.0);
        assertThat(account.getBalance())
                .isEqualTo(INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Метод deposit должен выбрасывать IllegalArgumentException при отрицательной сумме")
    void depositShouldThrowIllegalArgumentExceptionForNegativeAmount() {
        double before = account.getBalance();
        assertThatThrownBy(() -> account.deposit(-1.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Сумма депозита не может быть отрицательной");

        // убедиться, что баланс не изменился
        assertThat(account.getBalance()).isEqualTo(before);
    }

    // --- Тесты для withdraw ---

    @Test
    @DisplayName("Метод withdraw должен уменьшать баланс при корректной сумме")
    void withdrawShouldDecreaseBalanceForValidAmount() throws TaskValidationException {
        account.withdraw(200.0);
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE - 200.0);
    }

    @Test
    @DisplayName("Метод withdraw должен позволять снять полный баланс")
    void withdrawShouldAllowWithdrawingFullBalance() throws TaskValidationException {
        account.withdraw(INITIAL_BALANCE);
        assertThat(account.getBalance()).isZero();
    }

    @Test
    @DisplayName("Метод withdraw должен допускать нулевую сумму")
    void withdrawShouldAllowZeroAmount() throws TaskValidationException {
        account.withdraw(0.0);
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать IllegalArgumentException при отрицательной сумме")
    void withdrawShouldThrowIllegalArgumentExceptionForNegativeAmount() {
        double before = account.getBalance();
        assertThatThrownBy(() -> account.withdraw(-5.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Сумма снятия не может быть отрицательной");

        // баланс не должен измениться
        assertThat(account.getBalance()).isEqualTo(before);
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать TaskValidationException при превышении баланса")
    void withdrawShouldThrowTaskValidationExceptionWhenAmountExceedsBalance() {
        double excessiveAmount = INITIAL_BALANCE + 100.0;
        double before = account.getBalance();

        assertThatThrownBy(() -> account.withdraw(excessiveAmount))
                .isInstanceOf(TaskValidationException.class)
                .satisfies(ex -> {
                    TaskValidationException tve = (TaskValidationException) ex;
                    // проверяем сообщение
                    assertThat(tve).hasMessageContaining("Недостаточно средств");
                    // проверяем поля
                    assertThat(tve.getBalance()).isEqualTo(before);
                    assertThat(tve.getWithdrawAmount()).isEqualTo(excessiveAmount);
                    assertThat(tve.getDeficit()).isEqualTo(excessiveAmount - before);
                });

        // после неудачной попытки баланс остался прежним
        assertThat(account.getBalance()).isEqualTo(before);
    }
}
