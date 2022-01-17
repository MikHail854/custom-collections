package ru.chigurov;

import java.util.*;

public class ArrayListCustom<E> implements List<E> {


    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};
    private static final Object[] EMPTY_ELEMENT_DATA = {};
    transient Object[] elementData;
    private int size;
    private int currentCapacity;

    public ArrayListCustom() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        currentCapacity += DEFAULT_CAPACITY;
    }

    public ArrayListCustom(int initCapacity) {
        if (initCapacity >= 0) {
            this.elementData = new Object[initCapacity];
            currentCapacity += initCapacity;
        } else {
            throw new IllegalArgumentException("Illegal capacity: " + initCapacity);
        }
    }

    @Override
    public boolean add(E e) {
        capacityIncrease();
        elementData[size++] = e;
        return true;
    }

    private void capacityIncrease() {
        if (size == currentCapacity) {
            currentCapacity += 10;
            Object[] newList = new Object[currentCapacity];
            if (size >= 0) System.arraycopy(elementData, 0, newList, 0, size);
            elementData = newList;
        }
    }

    @Override
    public E get(int index) {
        if (index <= size - 1 && index >= 0) {
            return (E) elementData[index];
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        int i = 0;
        String result = "[";
        while (elementData[i] != null) {
            result = result + elementData[i];
            if (elementData[i + 1] == null) {
                result = result + "]";
            } else {
                result = result + ", ";
            }
            i++;
        }
        return "elementData=" + result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i].equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
//        return null;
    }
}