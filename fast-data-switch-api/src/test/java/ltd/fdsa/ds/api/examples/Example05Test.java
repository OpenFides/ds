package ltd.fdsa.ds.api.examples;

import ltd.fdsa.ds.api.cbor.model.AbstractNumberTest;

/**
 * 24 -> 0x1818
 */
public class Example05Test extends AbstractNumberTest {

    public Example05Test() {
        super(24, new byte[] { 0x18, 0x18 });
    }

}