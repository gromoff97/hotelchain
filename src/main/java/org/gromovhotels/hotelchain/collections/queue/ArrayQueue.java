package org.gromovhotels.hotelchain.collections.queue;


import lombok.NonNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Представляет из себя реализацю очереди на основе динамического массива со сдвигом элементов (FIFO).
 * Сдвиг массива происходит после вставки нового элемента (см. {@link ArrayQueue#add(Object)}).
 */
public final class ArrayQueue<T> {
    private static final int DEFAULT_CAPACITY = 16;

    private T[] array;
    private int arrayActualSize;

    @SuppressWarnings("unchecked")
    private void init() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        arrayActualSize = 0;
    }

    public ArrayQueue() {
        init();
    }

    /**
     *  Метод для вставки элемента в очередь
     */
    public void add(@NonNull T element) {
        if (isFull()) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[arrayActualSize] = element;
        arrayActualSize++;
    }

    /**
     * Метод для получения и удаления элемента из очереди.
     */
    public Optional<T> poll() {
        if (arrayActualSize == 0) {
            return Optional.empty();
        }
        T polledHotelGuest = array[0];
        shiftLeft();
        arrayActualSize--;
        return Optional.of(polledHotelGuest);
    }

    private void shiftLeft() {
        for (int counter = 0; counter < arrayActualSize-1; counter++) {
            array[counter] = array[counter + 1];
        }
        array[arrayActualSize - 1] = null;
    }

    private boolean isFull() {
        return arrayActualSize == array.length;
    }

    /**
     * Универсальный метод для получения доступа к элементам списка
     */
    public Stream<T> stream() {
        return Arrays.stream(array).filter(Objects::nonNull);
    }

    public void clear() {
        init();
    }

    public Optional<T> peek() {
        if (arrayActualSize == 0) {
            return Optional.empty();
        }
        return Optional.of(array[0]);
    }
}
