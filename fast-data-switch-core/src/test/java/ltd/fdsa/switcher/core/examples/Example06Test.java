package ltd.fdsa.switcher.core.examples;

import ltd.fdsa.switcher.core.cbor.model.AbstractNumberTest;

/**
 * 25 -> 0x1819
 */
public class Example06Test extends AbstractNumberTest {

    public Example06Test() {
        super(25, new byte[] { 0x18, 0x19 });
    }

}
