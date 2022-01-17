package ru.chigurov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListCustomTest {

    private InitMyList list;


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
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        list.arrayListCustom.add(0, "000");
        assertEquals(4, list.arrayListCustom.size());
        assertEquals("000", list.arrayListCustom.get(0));
    }

    @Test
    public void addElementByIndexToMiddleTest() {
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        list.arrayListCustom.add(1, "000");
        assertEquals(4, list.arrayListCustom.size());
        assertEquals("000", list.arrayListCustom.get(1));
    }

    @Test
    public void addElementByIndexToEndTest() {
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        list.arrayListCustom.add(list.arrayListCustom.size(), "000");
        assertEquals(4, list.arrayListCustom.size());
        assertEquals("000", list.arrayListCustom.get(list.arrayListCustom.size() - 1));
    }

    @Test
    public void removeByIndexTest() {
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        list.arrayListCustom.remove(1);
        assertEquals(2, list.arrayListCustom.size());
    }

    @Test
    public void removeByObjectTest() {
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        list.arrayListCustom.remove("2");
        assertEquals(2, list.arrayListCustom.size());
        assertNotEquals("2", list.arrayListCustom.get(1));
        assertFalse(list.arrayListCustom.contains("2"));
    }

    @Test
    public void getTest() {
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        assertEquals("2", list.arrayListCustom.get(1));
    }

    @Test
    public void setTest() {
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        list.arrayListCustom.set(1, "000");
        assertEquals("000", list.arrayListCustom.get(1));
    }

    @Test
    public void indexOfTest(){
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        assertEquals(1, list.arrayListCustom.indexOf("2"));
    }

    @Test
    public void clearTest(){
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        assertEquals(3, list.arrayListCustom.size());
        list.arrayListCustom.clear();
        assertEquals(0, list.arrayListCustom.size());
    }

    @Test
    public void sizeTest(){
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        assertEquals(3, list.arrayListCustom.size());
    }

    @Test
    public void isEmptyTest(){
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        assertFalse(list.arrayListCustom.isEmpty());
    }

    @Test
    public void containsTrueTest(){
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
        assertTrue(list.arrayListCustom.contains("2"));
    }

    @Test
    public void containsFalseTest(){
        list = new InitMyList();
        list.initList();
        assertNotNull(list);
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
