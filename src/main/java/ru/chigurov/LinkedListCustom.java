package ru.chigurov;

import java.util.*;

public class LinkedListCustom<E> implements List<E> {

    private Node<E> head;

    private int size;

    @Override
    public boolean add(E e) {
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
    }

    @Override
    public E get(int index) {
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

        throw new IllegalArgumentException();
    }

    public E remove(int index) {
        Node<E> result;

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

        int currentIndex = 0;
        Node<E> temp = head;

        while (temp != null) {
            if (currentIndex == index - 1) {
                Node<E> pr;
                pr = temp;
                result = temp.getNext();
                temp.setNext(temp.getNext().getNext());
                temp = temp.getNext().getNext();
                temp.setPrev(pr);
                size--;
                return result.getValue();
            } else {
                temp = temp.getNext();
                currentIndex++;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public boolean remove(Object o) {
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
    }

    @Override
    public boolean contains(Object o) {
        Node<E> temp = head;

        while (temp != null) {
            if (temp.getValue().equals(o)) {
                return true;
            } else {
                temp = temp.getNext();
            }
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Node<E> temp = head;
        while (temp != null) {
            Node<E> next = temp.getNext();
            temp.setNext(null);
            temp.setPrev(null);
            temp.setValue(null);
            temp = next;
        }
        size = 0;
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

    //    @Data
    private static class Node<E> {

        private E value;

        private Node<E> next;

        private Node<E> prev;


        public Node(E value, Node<E> prev) {
            this.value = value;
            this.prev = prev;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

    }

    @Override
    public boolean isEmpty() {
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
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

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
        return null;
    }


}
