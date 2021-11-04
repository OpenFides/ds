package ltd.fdsa.ds.api.examples;

import ltd.fdsa.ds.api.cbor.model.AbstractHalfPrecisionFloatTest;

/**
 * 5.960464477539063e-08 -> 0xf90001
 */
public class Example28Test extends AbstractHalfPrecisionFloatTest {

    public Example28Test() {
        super(Float.parseFloat("5.960464477539063e-08"), new byte[] { (byte) 0xf9, 0x00, 0x01 });
    }

}
