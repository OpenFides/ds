package ltd.fdsa.switcher.core.examples;

import ltd.fdsa.switcher.core.cbor.model.AbstractHalfPrecisionFloatTest;

/**
 * NaN -> 0xf97e00
 */
public class Example33Test extends AbstractHalfPrecisionFloatTest {

    public Example33Test() {
        super(Float.NaN, new byte[] { (byte) 0xf9, 0x7e, 0x00 });
    }

}
