package ltd.fdsa.ds.core.examples;

import ltd.fdsa.ds.core.cbor.model.AbstractHalfPrecisionFloatTest;

/**
 * -4,0 -> 0xf9c400
 */
public class Example30Test extends AbstractHalfPrecisionFloatTest {

    public Example30Test() {
        super(Float.parseFloat("-4.0"), new byte[] { (byte) 0xf9, (byte) 0xc4, 0x00 });
    }

}
