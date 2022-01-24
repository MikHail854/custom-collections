package ru.chigurov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListCustomTest {

    InitMyList list;

    @BeforeEach
    public void init(){
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
    }

    @Test
    public void addFirstElementTest() {
        LinkedListCustom<String> linkedListCustom = new LinkedListCustom<>();
        linkedListCustom.add("111");
        assertNotNull(linkedListCustom);
    }

    @Test
    public void addMultipleElementsTest() {
        LinkedListCustom<String> linkedListCustom = new LinkedListCustom<>();
        linkedListCustom.add("111");
        linkedListCustom.add("111");
        linkedListCustom.add("111");
        assertNotNull(linkedListCustom);
    }

    @Test
    public void addElementByIndexToTheBeginningTest() {
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        list.linkedListCustom.add(0, "000");
        assertEquals(6, list.linkedListCustom.size());
        assertEquals("000", list.linkedListCustom.get(0));
    }

    @Test
    public void addElementByIndexToMiddleTest() {
        list.linkedListCustom.add(2, "000");
        assertEquals(6, list.linkedListCustom.size());
        assertEquals("000", list.linkedListCustom.get(2));
    }

    @Test
    public void addElementByIndexToEndTest() {
        list.linkedListCustom.add(list.linkedListCustom.size(), "000");
        assertEquals(6, list.linkedListCustom.size());
        assertEquals("000", list.linkedListCustom.get(list.linkedListCustom.size() - 1));
    }

    @Test
    public void getTest() {
        String result = list.linkedListCustom.get(3);
        assertEquals("444", result);
    }

    @Test
    public void removeByToBeginningIndexTest() {
        list.linkedListCustom.remove(0);
        assertEquals(4, list.linkedListCustom.size());
        assertFalse(list.linkedListCustom.contains("111"));
    }

    @Test
    public void removeByMiddleIndexTest() {
        list.linkedListCustom.remove(3);
        assertEquals(4, list.linkedListCustom.size());
        assertFalse(list.linkedListCustom.contains("444"));
    }

    @Test
    public void removeByEndIndexTest() {
        list.linkedListCustom.remove(list.linkedListCustom.size() - 1);
        assertEquals(4, list.linkedListCustom.size());
        assertFalse(list.linkedListCustom.contains("555"));
    }

    @Test
    public void containsFalseTest() {
        assertFalse(list.linkedListCustom.contains("9999"));
    }

    @Test
    public void containsTrueTest() {
        assertTrue(list.linkedListCustom.contains("444"));
    }

    @Test
    public void setToBeginningIndexTest() {
        list.linkedListCustom.set(0, "000");
        assertTrue(list.linkedListCustom.contains("000"));
        assertEquals("000", list.linkedListCustom.get(0));
    }

    @Test
    public void setToMiddleIndexTest() {
        list.linkedListCustom.set(3, "999999");
        assertTrue(list.linkedListCustom.contains("999999"));
        assertEquals("999999", list.linkedListCustom.get(3));
        ;
    }

    @Test
    public void setToEndIndexTest() {
        list.linkedListCustom.set(list.linkedListCustom.size() - 1, "777");
        assertTrue(list.linkedListCustom.contains("777"));
        assertEquals("777", list.linkedListCustom.get(list.linkedListCustom.size() - 1));
    }

    @Test
    public void sizeTest(){
        assertEquals(5, list.linkedListCustom.size());
    }

    @Test
    public void clearTest(){
        list.linkedListCustom.clear();
        assertEquals(0, list.linkedListCustom.size());
    }


    static class InitMyList {

        private LinkedListCustom<String> linkedListCustom = new LinkedListCustom<>();

        public LinkedListCustom<String> initList() {
            linkedListCustom.add("111");
            linkedListCustom.add("222");
            linkedListCustom.add("333");
            linkedListCustom.add("444");
            linkedListCustom.add("555");
            return linkedListCustom;
        }

        @Override
        public String toString() {
            return "linkedListCustom=" + linkedListCustom;
        }
    }


}
