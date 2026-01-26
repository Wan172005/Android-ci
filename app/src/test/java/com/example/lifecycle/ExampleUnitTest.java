package com.example.lifecycle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    // Test màu đỏ
    @Test
    public void testRedColor() {

        int color = MainActivity.getColorFromName("hoang");

        assertEquals(0xffff0000, color);
    }

    // Test màu xanh lá
    @Test
    public void testGreenColor() {

        int color = MainActivity.getColorFromName("thuan");

        assertEquals(0xff00ff00, color);
    }

    // Test màu xanh dương
    @Test
    public void testBlueColor() {

        int color = MainActivity.getColorFromName("phuong");

        assertEquals(0xff0000ff, color);
    }

    // Test không nhập gì
    @Test
    public void testNullInput() {

        int color = MainActivity.getColorFromName(null);

        assertEquals(0xffffffff, color);
    }

    // Test không khớp tên
    @Test
    public void testUnknownName() {

        int color = MainActivity.getColorFromName("abc");

        assertEquals(0xffffffff, color);
    }
}