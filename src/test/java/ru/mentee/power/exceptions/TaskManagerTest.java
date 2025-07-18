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
        account = new TaskManager(ACCOUNT_ID, INITIAL_BALANCE);
    }


    @Test
    @DisplayName("Конструктор должен выбрасывать NullPointerException при null ID")
    void constructorShouldThrowNullPointerExceptionForNullId() {
        assertThatThrownBy(() -> new TaskManager(null, 100.0))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("id");
    }

    @Test
    @DisplayName("Конструктор должен корректно работать при нулевом начальном балансе")
    void constructorShouldAllowZeroInitialBalance() {
        TaskManager zeroAccount = new TaskManager(ACCOUNT_ID, 0.0);
        assertThat(zeroAccount.getBalance()).isZero();
        assertThat(zeroAccount.getId()).isEqualTo(ACCOUNT_ID);
    }

    @Test
    @DisplayName("Метод deposit должен принимать очень маленькую положительную сумму (EPS)")
    void depositShouldAcceptTinyPositiveAmount() {
        double before = account.getBalance();
        double eps = Double.MIN_VALUE; // самая маленькая положительная константа double
        account.deposit(eps);
        assertThat(account.getBalance()).isEqualTo(before + eps);
    }

    @Test
    @DisplayName("Метод deposit должен выбрасывать при переполнении баланса")
    void depositShouldThrowOnOverflow() {
        double huge = Double.MAX_VALUE;
        TaskManager hugeAccount = new TaskManager(ACCOUNT_ID, huge);

        // Проверяем, что при депозите не бросается исключения:
        assertThatCode(() -> hugeAccount.deposit(huge))
                .doesNotThrowAnyException();

        // А баланс становится бесконечностью:
        assertThat(hugeAccount.getBalance())
                .isInfinite()              // баланс == Infinity
                .isPositive();             // и это +Infinity
    }

    @Test
    @DisplayName("Метод withdraw должен корректно работать при снятии почти всего баланса (balance - EPS)")
    void withdrawShouldAllowAlmostFullBalance() throws TaskValidationException {
        double eps = 1e-10;
        double amount = INITIAL_BALANCE - eps;
        account.withdraw(amount);

        // проверяем, что остаток близок к eps с допускам 1e-10
        assertThat(account.getBalance())
                .isCloseTo(eps, within(1e-10));
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать при underflow (слишком большой запрос)")
    void withdrawShouldThrowOnUnderflow() {
        // Сразу берем сумму, которая приведет к очень большому дефициту
        double excessive = Double.MAX_VALUE;
        assertThatThrownBy(() -> account.withdraw(excessive))
                .isInstanceOf(TaskValidationException.class)
                .hasMessageContaining("Недостаточно средств для снятия")
                .satisfies(ex -> {
                    TaskValidationException tve = (TaskValidationException) ex;
                    assertThat(tve.getBalance()).isEqualTo(INITIAL_BALANCE);
                    assertThat(tve.getWithdrawAmount()).isEqualTo(excessive);
                    assertThat(tve.getDeficit()).isEqualTo(excessive - INITIAL_BALANCE);
                });
    }

    @Test
    @DisplayName("Метод withdraw не изменяет баланс, если сумма равна 0.0 (проверка на -0.0)")
    void withdrawShouldAllowNegativeZeroAmount() throws TaskValidationException {
        double before = account.getBalance();
        double negativeZero = -0.0d;   // IEEE-754 «-0.0»
        // На практике amount < 0.0 бросит IllegalArgumentException,
        // но проверяем, что -0.0 рассматривается как 0.0
        account.withdraw(negativeZero);
        assertThat(account.getBalance()).isEqualTo(before);
    }
}
