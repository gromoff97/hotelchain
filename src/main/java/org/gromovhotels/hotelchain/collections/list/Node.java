package org.gromovhotels.hotelchain.collections.list;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Predicate;

/**
 * Класс для создания объектов-посредников для {@link CycledLinkedList}
 */
@Data
@AllArgsConstructor
public final class Node<T> {
    private T element;
    private Node<T> nextNode;

    public boolean refersToItself() {
        return nextNode == this;
    }

    public boolean satisfies(Predicate<Node<T>> predicate) {
        return predicate.test(this);
    }
}