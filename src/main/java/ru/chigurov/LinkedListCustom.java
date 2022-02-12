package ru.chigurov;

import lombok.Data;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LinkedListCustom<E> implements List<E> {

    private Node<E> head;

    private int size;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    /**
     * this method adds a new element to the collection
     *
     * @param e element to be added to the collection
     * @return true when an element is added to the collection
     */
    @Override
    public boolean add(E e) {
        try {
            writeLock.lock();
            //если первое добавление в список
            if (head == null) {
                this.head = new Node<>(e, null);
            } else {
                Node<E> temp = head;
                while (temp.getNext() != null) {
                    temp = temp.getNext();
                }
                temp.setNext(new Node<>(e, temp));
            }
            size++;
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * adding a new element to the collection by index
     *
     * @param index the position where the new element will be added
     * @param e     element to be added to the collection
     */
    @Override
    public void add(int index, E e) {
        try {
            writeLock.lock();
            if (index == 0) {
                Node<E> temp = head;
                temp.setPrev(new Node<>(e, null));
                temp.getPrev().setNext(temp);
                head = temp.getPrev();
                size++;
            } else if (size == index) {
                add(e);
            } else {
                Node<E> temp = head;
                Node<E> next;
                Node<E> prev;
                int currentIndex = 0;
                while (temp != null) {
                    if (currentIndex == index - 1) {
                        next = temp.getNext();
                        temp.setNext(new Node<>(e, temp));
                        temp.getNext().setNext(next);
                        temp = temp.getNext();
                        prev = temp;
                        temp.getNext().setPrev(prev);
                        break;
                    } else {
                        temp = temp.getNext();
                        currentIndex++;
                    }
                }
                size++;
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * getting an element from a collection by index
     *
     * @param index the position of the object in the collection to get
     * @return the object that is contained in the requested index
     */
    @Override
    public E get(int index) {
        try {
            readLock.lock();
            int currentIndex = 0;
            Node<E> temp = head;
            while (temp != null) {
                if (currentIndex == index) {
                    return temp.getValue();
                } else {
                    temp = temp.getNext();
                    currentIndex++;
                }
            }

            throw new IndexOutOfBoundsException();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * removing an element from a collection by index
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the collection
     */
    public E remove(int index) {
        try {
            writeLock.lock();
            Node<E> result;
            int currentIndex = 0;
            Node<E> temp = head;
            Node<E> prev = null;

            //removing the first element
            if (index == 0) {
                result = head;
                if (head.getNext() != null) {
                    head = head.getNext();
                    head.setPrev(null);
                } else {
                    head = null;
                }
                size--;
                return result.getValue();
            }

            //removing the last element
            if (index == size - 1) {
                while (temp != null) {
                    if (temp.getNext() == null) {
                        result = temp;
                        temp = temp.getPrev();
                        temp.setNext(null);
                        size--;
                        return result.getValue();
                    } else {
                        temp = temp.getNext();
                    }
                }
            }

            //removing the penultimate element
            while (temp != null) {
                if (currentIndex == index) {
                    prev.setNext(temp.getNext());
                    temp.getNext().setPrev(prev);
                    size--;
                    return temp.getValue();
                } else {
                    prev = temp;
                    temp = temp.getNext();
                    currentIndex++;
                }
            }

            throw new IndexOutOfBoundsException();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * removing an object from a collection
     *
     * @param o the object to be removed from the collection
     * @return true if the object was successfully removed from the collection
     */
    @Override
    public boolean remove(Object o) {
        try {
            writeLock.lock();
            Node<E> temp = head;
            if (head.getValue().equals(o)) {
                if (head.getNext() != null) {
                    head = head.getNext();
                    head.setPrev(null);
                } else {
                    head = null;
                }
                size--;
                return true;
            }

            while (temp != null) {
                if (temp.getNext().getValue().equals(o)) {
                    Node<E> pr = temp;
                    temp.setNext(temp.getNext().getNext());
                    temp = temp.getNext().getNext();
                    temp.setPrev(pr);
                    size--;
                    return true;
                } else {
                    temp = temp.getNext();
                }
            }
            throw new IllegalArgumentException();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * The method checks if the collection contains the required object
     *
     * @param o the object to be searched in the collection
     * @return true if the object is contained in the collection
     */
    @Override
    public boolean contains(Object o) {
        try {
            readLock.lock();
            Node<E> temp = head;

            while (temp != null) {
                if (temp.getValue().equals(o)) {
                    return true;
                } else {
                    temp = temp.getNext();
                }
            }

            return false;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * The method changes the value of an existing element
     *
     * @param index object to be changed
     * @param e     the new value of the element
     * @return the new value of the element
     */
    @Override
    public E set(int index, E e) {
        try {
            writeLock.lock();
            Node<E> temp = head;
            int currentIndex = 0;
            while (temp != null) {
                if (currentIndex == index) {
                    temp.setValue(e);
                    return temp.getValue();
                } else {
                    temp = temp.getNext();
                    currentIndex++;
                }
            }
            throw new IndexOutOfBoundsException();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * method checks the size of the collection
     *
     * @return collection size
     */
    @Override
    public int size() {
        try {
            readLock.lock();
            return size;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * This method removes all elements from the collection
     */
    @Override
    public void clear() {
        try {
            writeLock.lock();
            Node<E> temp = head;
            while (temp != null) {
                Node<E> next = temp.getNext();
                temp.setNext(null);
                temp.setPrev(null);
                temp.setValue(null);
                temp = next;
            }
            head = null;
            size = 0;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        Object[] result = new Object[size];
        int idx = 0;
        Node<E> temp = head;

        while (temp != null) {
            result[idx++] = temp.getValue();
            temp = temp.getNext();
        }

        return Arrays.toString(result);

    }

    @Data
    private static class Node<E> {

        private E value;
        private Node<E> next;
        private Node<E> prev;


        public Node(E value, Node<E> prev) {
            this.value = value;
            this.prev = prev;
        }

    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int counter = 0;

            @Override
            public boolean hasNext() {
                try {
                    readLock.lock();
                    return counter < size;
                } finally {
                    readLock.unlock();
                }
            }

            @Override
            public E next() {
                try {
                    readLock.lock();
                    return get(counter++);
                } finally {
                    readLock.unlock();
                }
            }
        };
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
    public int indexOf(Object o) {
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
