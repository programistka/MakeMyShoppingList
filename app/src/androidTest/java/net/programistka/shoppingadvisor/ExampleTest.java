package net.programistka.shoppingadvisor;

import android.test.InstrumentationTestCase;

/**
 * Created by maga on 23.03.16.
 */
public class ExampleTest extends InstrumentationTestCase {
    public void test() throws Exception {

        // tabelka
        // 1 10.02.2016
        // 1 15.02.2016
        // 2 13.02.2016
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}