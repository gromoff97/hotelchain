package org.gromovhotels.hotelchain.collections.list;

import lombok.NonNull;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.gromovhotels.hotelchain.collections.list.NodeUtils.*;
import static org.gromovhotels.hotelchain.utils.ValidationUtils.validateNonNegative;


/**
 * Представляет из себя реализацю кольцевого упорядоченного однонаправленного списска.
 */
public final class CycledLinkedList<T> {

    /** Первый элемент списка. Служит заголовком списка */
    private Node<T> headNode;

    /** Размер массива */
    private int size;

    /**
     * Метод для вставки элемента в коллекцию по индексу
     */
    public void insert(@NonNull T element, int index) {
        validateIndexBeforeInsert(index);

        if (index == 0) {
            if (size == 0) {
                headNode = new Node<>(element, null);
                headNode.setNextNode(headNode);
            } else {
                Node<T> newNode = new Node<>(element, headNode);
                Node<T> lastNode = findNodeByIndex(size - 1, headNode);
                lastNode.setNextNode(newNode);
                headNode = newNode;
            }
            size++;
            return;
        }

        Node<T> foundNode = findNodeByIndex(index - 1, headNode);
        Node<T> newNode = new Node<>(element, foundNode.getNextNode());
        foundNode.setNextNode(newNode);
        size++;
    }


    /** Метод для вставки элемента в конец коллекции */
    public void insertInTheEnd(@NonNull T element) {
        insert(element, size);
    }

    public T get(int index) {
        validateIndexBeforeGet(index);
        return findNodeByIndex(index, headNode).getElement();
    }

    /**
     * Универсальный метод для получения доступа к элементам списка
     */
    public Stream<T> stream() {
        return nodesStream(headNode).map(Node::getElement);
    }

    public void removeIf(@NonNull Predicate<T> predicate) {
        stream().filter(predicate).forEach(this::delete);
    }

    public boolean delete(@NonNull T element) {
        DeleteResult<T> deleteResult = deleteNodeFirstSatisfying(headNode, nodeElementEquals(element));
        if (deleteResult.somethingDeleted()) {
            headNode = deleteResult.newInitialNode();
            size--;
            return true;
        }
        return false;
    }

    public boolean delete(int index) {
        return delete(get(index));
    }

    public boolean isEmpty() {
        return headNode == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        headNode = null;
        size = 0;
    }

    private void validateIndexBeforeInsert(int index) {
        if (validateNonNegative(index) == size) {
            return;
        }
        if (index > size) {
            throw outOfBoundsException(index, size);
        }
    }

    private void validateIndexBeforeGet(int index) {
        if (validateNonNegative(index) >= size) {
            throw outOfBoundsException(index, size);
        }
    }

    private static <T> Predicate<Node<T>> nodeElementEquals(T el) {
        return node -> Objects.equals(node.getElement(), el);
    }

    private static IndexOutOfBoundsException outOfBoundsException(int index, int size) {
        String format = "Указанный индекс (%s) выступает за пределы размера массива (size = %s)";
        return new IndexOutOfBoundsException(format.formatted(index, size));
    }
}
