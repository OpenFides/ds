package ltd.fdsa.switcher.core.examples;

import ltd.fdsa.switcher.core.cbor.model.AbstractNumberTest;

/**
 * 23 -> 0x17
 */
public class Example04Test extends AbstractNumberTest {

    public Example04Test() {
        super(23, new byte[] { 0x17 });
    }

}
