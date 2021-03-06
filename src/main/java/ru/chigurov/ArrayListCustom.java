package ru.chigurov;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListCustom<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;
    transient Object[] elementData;
    private int size;
    private int currentCapacity;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    /**
     * Constructor without parameters.
     * The initial length of the array is 10
     */
    public ArrayListCustom() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        currentCapacity += DEFAULT_CAPACITY;
    }

    /**
     * Constructor with parameters
     *
     * @param initCapacity initial length of the array
     */
    public ArrayListCustom(int initCapacity) {
        if (initCapacity >= 0) {
            this.elementData = new Object[initCapacity];
            currentCapacity += initCapacity;
        } else {
            throw new IllegalArgumentException("Illegal capacity: " + initCapacity);
        }
    }

    /**
     * The "add" method adds a new element to the end of the collection
     *
     * @param e the value of the added element
     * @return true when a new element is added to the array
     */
    @Override
    public boolean add(E e) {
        try {
            lockingForWrite();
            capacityIncrease();
            elementData[size++] = e;
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * The "add" method adds a new element anywhere in the collection
     *
     * @param index   the place where the new element will be added
     * @param element the value of the new element
     */
    @Override
    public void add(int index, E element) {
        try {
            lockingForWrite();
            capacityIncrease();
            if (index == size) {
                elementData[size++] = element;
                return;
            } else {
                for (int i = 0; i < size; i++) {
                    if (i == index) {
                        E current = (E) elementData[i];
                        elementData[i] = element;
                        i++;
                        size++;
                        while (i < size) {
                            E next = (E) elementData[i];
                            elementData[i] = current;
                            current = next;
                            i++;
                        }
                        return;
                    }
                }
            }
            throw new IllegalArgumentException();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Increasing the size of an array
     */
    private void capacityIncrease() {
        if (size > currentCapacity * 0.75) {
            currentCapacity += 10;
            Object[] newList = new Object[currentCapacity];
            if (size >= 0) System.arraycopy(elementData, 0, newList, 0, size);
            elementData = newList;
        }
    }

    /**
     * Removing an element from a collection by index
     *
     * @param index of the element to be removed
     * @return the element of the element that was removed
     */
    @Override
    public E remove(int index) {
        try {
            lockingForWrite();
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    E result = (E) elementData[i];
                    while (i < size) {
                        elementData[i] = elementData[i + 1];
                        i++;
                    }
                    elementData[i] = null;
                    size--;
                    return result;
                }
            }
            throw new IllegalArgumentException();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Removing an object from a collection
     *
     * @param o object to be deleted from the collection
     * @return true if the object was successfully removed from the collection
     */
    @Override
    public boolean remove(Object o) {
        try {
            lockingForWrite();
            for (int i = 0; i < size; i++) {
                if (elementData[i].equals(o)) {
                    while (i < size) {
                        elementData[i] = elementData[i + 1];
                        i++;
                    }
                    elementData[i] = null;
                    size--;
                    return true;
                }
            }
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Getting an object by index from a collection
     *
     * @param index the index of the element to get
     * @return collection element value
     */
    @Override
    public E get(int index) {
        try {
            lockingForRead();
            if (index <= size - 1 && index >= 0) {
                return (E) elementData[index];
            } else {
                throw new IllegalArgumentException();
            }
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Changing the value of an existing collection element
     *
     * @param index   of the element to change
     * @param element the new value of the element
     * @return the new value of the element
     */
    @Override
    public E set(int index, E element) {
        try {
            lockingForWrite();
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    elementData[i] = element;
                    return element;
                }
            }
            throw new IllegalArgumentException();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * The method checks at what index the desired object is located
     *
     * @param o object to be searched among the existing collection objects
     * @return the index at which the object is found
     */
    @Override
    public int indexOf(Object o) {
        try {
            lockingForRead();
            for (int i = 0; i < size; i++) {
                if (elementData[i].equals(o)) {
                    return i;
                }
            }
            throw new NoSuchElementException();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * The method clears the entire collection
     */
    @Override
    public void clear() {
        try {
            lockingForWrite();
            for (int i = 0; i < size; i++)
                elementData[i] = null;
            size = 0;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String toString() {
        int i = 0;
        String result = "[";
        while (elementData[i] != null) {
            result = result + elementData[i];
            if (elementData[i + 1] != null) {
                result = result + ", ";
                i++;
                continue;
            }
            break;
        }
        result += "]";
        return result;
    }

    /**
     * The method checks how many elements are in the collection
     *
     * @return number of elements in the collection
     */
    @Override
    public int size() {
        try {
            lockingForRead();
            return size;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * The method checks the collection for emptiness
     *
     * @return true if the collection is empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * The method will check if the required object is contained in the collection
     *
     * @param o search object in the collection
     * @return true if the object being searched for is contained in the collection
     */
    @Override
    public boolean contains(Object o) {
        try {
            lockingForRead();
            if (o != null) {
                for (int i = 0; i < size; i++) {
                    if (elementData[i].equals(o)) {
                        return true;
                    }
                }
            }
            return false;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * The iterator is needed to implement the foreach method with
     * which you can run through the entire collection.
     * Iterators allow the caller to remove elements from the
     * underlying collection during the iteration with well-defined semantics.
     * <p>
     * Type parameters:<E> ??? the type of elements returned by this iterator
     *
     * @return Iterator
     */
    @Override
    public Iterator<E> iterator() {
        try {
            lockingForRead();
            return new Iterator<E>() {

                int counter = 0;

                @Override
                public boolean hasNext() {
                    return counter < size;
                }

                @Override
                public E next() {
                    return get(counter++);
                }
            };
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Blocking the collection for reading.
     */
    private void lockingForRead() {
        while (true) {
            boolean readLockResult = readLock.tryLock();
            if (readLockResult) {
                break;
            }
        }
    }

    /**
     * Blocking the collection for writing.
     */
    private void lockingForWrite() {
        while (true) {
            boolean writeLockResult = writeLock.tryLock();
            if (writeLockResult) {
                break;
            }
        }
    }


    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
