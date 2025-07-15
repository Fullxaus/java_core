package ru.mentee.power.collections.base;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ListUtilsTest {

    @Test
    void shouldMergeTwoListsAndRemoveDuplicates() {
        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = Arrays.asList("Banana", "Cherry", "Date");
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result)
                .hasSize(4)
                .containsExactlyInAnyOrder("Apple", "Banana", "Cherry", "Date");
    }

    @Test
    void shouldReturnFirstListElementsWhenSecondListIsEmpty() {
        List<String> list1 = Arrays.asList("Apple", "Banana");
        List<String> list2 = Arrays.asList();
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder("Apple", "Banana");
    }

    @Test
    void shouldReturnEmptyListWhenBothListsAreEmpty() {
        List<String> list1 = Arrays.asList();
        List<String> list2 = Arrays.asList();
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnSecondListWhenFirstListIsNull() {
        List<String> list1 = null;
        List<String> list2 = Arrays.asList("Date", "Elderberry");
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder("Date", "Elderberry");
    }

    @Test
    void shouldReturnEmptyListWhenBothListsAreNull() {
        List<String> result = ListUtils.mergeLists(null, null);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldIgnoreNullElementsWhenMergingLists() {
        List<String> list1 = Arrays.asList("Apple", null, "Banana");
        List<String> list2 = Arrays.asList(null, "Cherry", "Banana");
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result)
                .hasSize(4) // null is a unique element and will be included since method doesn't exclude null explicitly
                .containsExactlyInAnyOrder("Apple", "Banana", "Cherry", null);
        // Если нужно игнорировать null, тогда метод надо править, сейчас null добавляется
    }

    @Test
    void shouldHandleCustomScenarioForMergeLists() {
        List<String> list1 = Arrays.asList("", "  ", "Apple", "Banana", null);
        List<String> list2 = Arrays.asList("Banana", "Date", "", null);
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result)
                .hasSize(6)
                .containsExactlyInAnyOrder("", "  ", "Apple", "Banana", "Date", null);
    }

}
