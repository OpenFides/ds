package ltd.fdsa.ds.core.examples;

import ltd.fdsa.ds.core.cbor.model.AbstractNumberTest;

/**
 * 1000 -> 0x1903e8
 */
public class Example08Test extends AbstractNumberTest {

    public Example08Test() {
        super(1000, new byte[] { 0x19, 0x03, (byte) 0xe8 });
    }

}
