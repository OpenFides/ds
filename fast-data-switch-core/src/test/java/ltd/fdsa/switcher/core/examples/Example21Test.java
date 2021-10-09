package ltd.fdsa.switcher.core.examples;

import ltd.fdsa.switcher.core.cbor.model.AbstractHalfPrecisionFloatTest;

/**
 * Example 21: 1.0 -> 0xf93c00
 */
public class Example21Test extends AbstractHalfPrecisionFloatTest {

    public Example21Test() {
        super(1.0f, new byte[] { (byte) 0xf9, 0x3c, 0x00 });
    }

}
