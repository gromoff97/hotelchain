import lombok.With;
import org.gromovhotels.hotelchain.collections.list.CycledLinkedList;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.*;

public class CycledListTest {

    private final TestObj first = TestObj.random().withName("first");
    private final TestObj second = TestObj.random().withName("second");
    private final TestObj third = TestObj.random().withName("third");
    private final TestObj fourth = TestObj.random().withName("fourth");
    private final TestObj fifth = TestObj.random().withName("fifth");

    @Test
    @Order(1)
    void empty() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();
        assertThat(newList.isEmpty() && newList.size() == 0).isTrue();
    }

    @Test
    @Order(2)
    void emptyNegTests() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();
        assertThatThrownBy(() -> newList.insert(first, -2)).isInstanceOf(IllegalArgumentException.class).hasMessage("Значение '-2' не должно быть отрицательным");
        assertThatThrownBy(() -> newList.insert(first, -1)).isInstanceOf(IllegalArgumentException.class).hasMessage("Значение '-1' не должно быть отрицательным");

        assertThatThrownBy(() -> newList.get(-1)).isInstanceOf(IllegalArgumentException.class).hasMessage("Значение '-1' не должно быть отрицательным");
        assertThatThrownBy(() -> newList.get(0)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (0) выступает за пределы размера массива (size = 0)");
        assertThatThrownBy(() -> newList.get(1)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (1) выступает за пределы размера массива (size = 0)");
        assertThatThrownBy(() -> newList.get(2)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (2) выступает за пределы размера массива (size = 0)");

        assertThat(newList.delete(first)).isFalse();
    }

    @Test
    @Order(3)
    void insertionInTheEndTests() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();

        assertThatCode(() -> {
            newList.insertInTheEnd(first);
            newList.insertInTheEnd(second);
            newList.insertInTheEnd(third);
            newList.insertInTheEnd(fourth);
            newList.insertInTheEnd(fifth);
        }).doesNotThrowAnyException();

        assertThat(newList.get(0)).isEqualTo(first);
        assertThat(newList.get(1)).isEqualTo(second);
        assertThat(newList.get(2)).isEqualTo(third);
        assertThat(newList.get(3)).isEqualTo(fourth);
        assertThat(newList.get(4)).isEqualTo(fifth);
    }

    @Test
    @Order(4)
    void insertInBeginninTests() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();

        assertThatCode(() -> {
            newList.insert(first, 0);
            newList.insert(second, 0);
            newList.insert(third, 0);
            newList.insert(fourth, 0);
            newList.insert(fifth, 0);
        }).doesNotThrowAnyException();

        assertThat(newList.get(0)).isEqualTo(fifth);
        assertThat(newList.get(1)).isEqualTo(fourth);
        assertThat(newList.get(2)).isEqualTo(third);
        assertThat(newList.get(3)).isEqualTo(second);
        assertThat(newList.get(4)).isEqualTo(first);

        assertThat(newList.size()).isEqualTo(5);
        assertThat(newList.isEmpty()).isFalse();
    }

    @Test
    @Order(5)
    void insertTests() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();

        TestObj newTestObj = TestObj.random();
        assertThatCode(() -> {
            newList.insertInTheEnd(first);
            newList.insert(second, 1);
            newList.insert(third, 1);
            newList.insertInTheEnd(newTestObj);
            newList.insert(fourth, 2);
            newList.insert(fifth, 0);
        }).doesNotThrowAnyException();


        assertThat(newList.get(0)).isEqualTo(fifth);
        assertThat(newList.get(1)).isEqualTo(first);
        assertThat(newList.get(2)).isEqualTo(third);
        assertThat(newList.get(3)).isEqualTo(fourth);
        assertThat(newList.get(4)).isEqualTo(second);
        assertThat(newList.get(5)).isEqualTo(newTestObj);

        assertThat(newList.size()).isEqualTo(6);
        assertThat(newList.isEmpty()).isFalse();
    }

    @Test
    @Order(6)
    void insertNegTests() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();

        assertThatThrownBy(() -> newList.insert(fourth, 1)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (1) выступает за пределы размера массива (size = 0)");

        newList.insert(first, 0);
        newList.insert(second, 1);
        newList.insert(third, 2);

        assertThatThrownBy(() -> newList.insert(fourth, 4)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (4) выступает за пределы размера массива (size = 3)");
    }

    @Test
    @Order(7)
    void streamTest() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();
        List<TestObj> initialList = List.of(first, second, third, fourth, fifth);
        initialList.forEach(newList::insertInTheEnd);
        List<TestObj> actualList = newList.stream().toList();
        assertThat(actualList).containsExactlyElementsOf(initialList);
    }

    @Test
    @Order(8)
    void deleteTest() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();
        List<TestObj> toInsert = List.of(first, second, third, fourth, fifth);

        toInsert.forEach(newList::insertInTheEnd);

        assertThat(newList.delete(TestObj.random())).isFalse();
        assertThat(newList.size()).isEqualTo(5);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(first, second, third, fourth, fifth);

        assertThat(newList.delete(third)).isTrue();
        assertThat(newList.size()).isEqualTo(4);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(first, second, fourth, fifth);

        assertThat(newList.delete(first)).isTrue();
        assertThat(newList.size()).isEqualTo(3);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(second, fourth, fifth);

        assertThat(newList.delete(fifth)).isTrue();
        assertThat(newList.size()).isEqualTo(2);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(second, fourth);

        assertThat(newList.delete(second)).isTrue();
        assertThat(newList.size()).isEqualTo(1);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(fourth);

        assertThat(newList.delete(fourth)).isTrue();
        assertThat(newList.size()).isEqualTo(0);
        assertThat(newList.isEmpty()).isTrue();
        assertThat(newList.stream().toList()).isEmpty();

        toInsert.forEach(testObj -> {
            assertThat(newList.delete(testObj)).isFalse();
            assertThat(newList.size()).isEqualTo(0);
            assertThat(newList.isEmpty()).isTrue();
            assertThat(newList.stream().toList()).isEmpty();
        });
    }

    @Test
    @Order(9)
    void deleteByIndexTests() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();
        List.of(first, second, third, fourth, fifth).forEach(newList::insertInTheEnd);

        assertThatThrownBy(() -> newList.delete(-2)).isInstanceOf(IllegalArgumentException.class).hasMessage("Значение '-2' не должно быть отрицательным");
        assertThatThrownBy(() -> newList.delete(-1)).isInstanceOf(IllegalArgumentException.class).hasMessage("Значение '-1' не должно быть отрицательным");
        assertThatThrownBy(() -> newList.delete(5)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (5) выступает за пределы размера массива (size = 5)");
        assertThatThrownBy(() -> newList.delete(6)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (6) выступает за пределы размера массива (size = 5)");

        assertThat(newList.delete(2)).isTrue();
        assertThat(newList.size()).isEqualTo(4);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(first, second, fourth, fifth);

        assertThat(newList.delete(0)).isTrue();
        assertThat(newList.size()).isEqualTo(3);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(second, fourth, fifth);

        assertThat(newList.delete(2)).isTrue();
        assertThat(newList.size()).isEqualTo(2);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(second, fourth);

        assertThat(newList.delete(1)).isTrue();
        assertThat(newList.size()).isEqualTo(1);
        assertThat(newList.isEmpty()).isFalse();
        assertThat(newList.stream().toList()).containsExactly(second);

        assertThat(newList.delete(0)).isTrue();
        assertThat(newList.size()).isEqualTo(0);
        assertThat(newList.isEmpty()).isTrue();
        assertThat(newList.stream().toList()).isEmpty();

        assertThatThrownBy(() -> newList.delete(0)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (0) выступает за пределы размера массива (size = 0)");
    }

    @Test
    @Order(10)
    void getAfterInsertNegTest() {
        CycledLinkedList<TestObj> newList = new CycledLinkedList<>();
        List.of(first, second, third, fourth, fifth).forEach(newList::insertInTheEnd);

        assertThatThrownBy(() -> newList.get(-1)).isInstanceOf(IllegalArgumentException.class).hasMessage("Значение '-1' не должно быть отрицательным");
        assertThatThrownBy(() -> newList.get(5)).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Указанный индекс (5) выступает за пределы размера массива (size = 5)");
    }

    @With
    private record TestObj(String name, String address) {
        public static TestObj random() {
            return new TestObj(randomAlphabetic(5), randomNumeric(5));
        }
    }

}
