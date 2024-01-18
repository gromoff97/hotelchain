package org.gromovhotels.hotelchain.collections.list;

import lombok.NonNull;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.gromovhotels.hotelchain.utils.ValidationUtils.validateNonNegative;

final class NodeUtils {

    private NodeUtils() {
        throw new AssertionError("Нельзя создать экземпляр утилитного класса");
    }

    static <T> Node<T> findNodeByIndex(int index, @NonNull Node<T> initialNode) {
        Node<T> currentNode = initialNode;
        for (int currentNodeIndex = 0; currentNodeIndex < validateNonNegative(index); currentNodeIndex++) {
            currentNode = currentNode.getNextNode();
            if (currentNode == initialNode) {
                throw new IllegalStateException("Поиск Nodes по индексу в зацикленном списке привёл к началу списка.");
            }
        }

        return currentNode;
    }

    /** Удаляет ноду по условию и возвращает первый первую ноду */
    static <T> DeleteResult<T> deleteNodeFirstSatisfying(Node<T> initialNode, @NonNull Predicate<Node<T>> nodePredicate) {
        if (initialNode == null) {
            return new DeleteResult<>(false, null);
        }

        Node<T> prevNode = nodeWatchingAt(initialNode);
        Node<T> currentNode = initialNode;

        // Если попался сразу же первый элемент
        if (currentNode.satisfies(nodePredicate)) {
            if (currentNode.refersToItself()) {
                return new DeleteResult<>(true, null);
            } else {
                Node<T> newHeadNode = currentNode.getNextNode();
                prevNode.setNextNode(newHeadNode);
                return new DeleteResult<>(true, newHeadNode);
            }
        }

        prevNode = prevNode.getNextNode();
        currentNode = currentNode.getNextNode();
        while (currentNode != initialNode) {
            if (nodePredicate.test(currentNode)) {
                Node<T> nextAfterCurrent = currentNode.getNextNode();
                prevNode.setNextNode(nextAfterCurrent);
                return new DeleteResult<>(true, initialNode);
            }
            prevNode = prevNode.getNextNode();
            currentNode = currentNode.getNextNode();
        }

        return new DeleteResult<>(false, initialNode);
    }

    private static <T> Node<T> nodeWatchingAt(@NonNull Node<T> initialNode) {
        Node<T> currentNode = initialNode;
        while (currentNode.getNextNode() != initialNode) {
            currentNode = currentNode.getNextNode();
        }
        return currentNode;
    }

    static <T> Stream<Node<T>> nodesStream(Node<T> initialNode) {
        if (initialNode == null) {
            return Stream.empty();
        }
        if (initialNode.getNextNode() == null) {
            return Stream.of(initialNode);
        }
        return Stream.concat(
                Stream.of(initialNode),
                Stream.iterate(initialNode.getNextNode(), currentNodeIsNot(initialNode), Node::getNextNode)
        );
    }

    private static <T> Predicate<Node<T>> currentNodeIsNot(Node<T> finalNode) {
        return node -> node != finalNode;
    }

    public record DeleteResult<T>(boolean somethingDeleted, Node<T> newInitialNode) {}
}
