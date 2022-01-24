package ru.chigurov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListCustomTest {

    private InitMyList list;

    @BeforeEach
    public void init() {
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
    }

    @Test
    public void addFirstElementTest() {
        ArrayListCustom<String> arrayListCustom = new ArrayListCustom<>();
        arrayListCustom.add("000");
        assertNotNull(arrayListCustom);
    }

    @Test
    public void addOneHundredElementTest() {
        ArrayListCustom<Integer> arrayListCustom = new ArrayListCustom<>();
        assertNotNull(arrayListCustom);
        for (int i = 0; i < 100; i++) {
            arrayListCustom.add(i);
        }
        assertEquals(100, arrayListCustom.size());
    }

    @Test
    public void addElementByIndexToTheBeginningTest() {
        list.arrayListCustom.add(0, "000");
        assertEquals(4, list.arrayListCustom.size());
        assertEquals("000", list.arrayListCustom.get(0));
    }

    @Test
    public void addElementByIndexToMiddleTest() {
        list.arrayListCustom.add(1, "000");
        assertEquals(4, list.arrayListCustom.size());
        assertEquals("000", list.arrayListCustom.get(1));
    }

    @Test
    public void addElementByIndexToEndTest() {
        list.arrayListCustom.add(list.arrayListCustom.size(), "000");
        assertEquals(4, list.arrayListCustom.size());
        assertEquals("000", list.arrayListCustom.get(list.arrayListCustom.size() - 1));
    }

    @Test
    public void removeByIndexTest() {
        list.arrayListCustom.remove(1);
        assertEquals(2, list.arrayListCustom.size());
    }

    @Test
    public void removeByObjectTest() {
        list.arrayListCustom.remove("2");
        assertEquals(2, list.arrayListCustom.size());
        assertNotEquals("2", list.arrayListCustom.get(1));
        assertFalse(list.arrayListCustom.contains("2"));
    }

    @Test
    public void getTest() {
        assertEquals("2", list.arrayListCustom.get(1));
    }

    @Test
    public void setTest() {
        list.arrayListCustom.set(1, "000");
        assertEquals("000", list.arrayListCustom.get(1));
    }

    @Test
    public void indexOfTest() {
        assertEquals(1, list.arrayListCustom.indexOf("2"));
    }

    @Test
    public void clearTest() {
        assertEquals(3, list.arrayListCustom.size());
        list.arrayListCustom.clear();
        assertEquals(0, list.arrayListCustom.size());
    }

    @Test
    public void sizeTest() {
        assertEquals(3, list.arrayListCustom.size());
    }

    @Test
    public void isEmptyTest() {
        assertFalse(list.arrayListCustom.isEmpty());
    }

    @Test
    public void containsTrueTest() {
        assertTrue(list.arrayListCustom.contains("2"));
    }

    @Test
    public void containsFalseTest() {
        assertFalse(list.arrayListCustom.contains("99"));
    }

    static class InitMyList {

        private final ArrayListCustom<String> arrayListCustom = new ArrayListCustom<>();

        public ArrayListCustom<String> initList() {
            arrayListCustom.add("1");
            arrayListCustom.add("2");
            arrayListCustom.add("3");
            return arrayListCustom;
        }
    }

}
