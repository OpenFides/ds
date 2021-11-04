package ltd.fdsa.ds.core.examples;

import ltd.fdsa.ds.core.cbor.model.AbstractStringTest;

/**
 * "\u00fc" -> 0x62c3bc
 */
public class Example60Test extends AbstractStringTest {

    public Example60Test() {
        super("\u00fc", new byte[] { 0x62, (byte) 0xc3, (byte) 0xbc });
    }

}
