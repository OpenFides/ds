package ltd.fdsa.switcher.core.examples;

import ltd.fdsa.switcher.core.cbor.model.AbstractNumberTest;

/**
 * 0 -> 0x00
 */
public class Example02Test extends AbstractNumberTest {

    public Example02Test() {
        super(1, new byte[] { 0x01 });
    }

}
