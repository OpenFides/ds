package ltd.fdsa.ds.core.examples;

import ltd.fdsa.ds.core.cbor.model.AbstractNumberTest;

/**
 * -1000 -> 0x3903e7
 */
public class Example18Test extends AbstractNumberTest {

    public Example18Test() {
        super(-1000, new byte[] { 0x39, 0x03, (byte) 0xe7 });
    }

}
