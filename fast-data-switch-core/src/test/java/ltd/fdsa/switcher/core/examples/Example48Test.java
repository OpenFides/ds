package ltd.fdsa.switcher.core.examples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ltd.fdsa.switcher.core.cbor.CborBuilder;
import ltd.fdsa.switcher.core.cbor.CborDecoder;
import ltd.fdsa.switcher.core.cbor.CborEncoder;
import ltd.fdsa.switcher.core.cbor.CborException;
import ltd.fdsa.switcher.core.cbor.model.DataItem;
import ltd.fdsa.switcher.core.cbor.model.UnicodeString;

/**
 * 0("2013-03-21T20:04:00Z") -> 0xc074323031332d30332d32315432303a30343a30305a
 */
public class Example48Test {

    private final List<DataItem> VALUE;

    private final byte[] ENCODED_VALUE = new byte[] { (byte) 0xc0, 0x74, 0x32, 0x30, 0x31, 0x33, 0x2d, 0x30, 0x33, 0x2d,
            0x32, 0x31, 0x54, 0x32, 0x30, 0x3a, 0x30, 0x34, 0x3a, 0x30, 0x30, 0x5a };

    public Example48Test() {
        DataItem di = new UnicodeString("2013-03-21T20:04:00Z");
        di.setTag(0);

        VALUE = new CborBuilder().add(di).build();
    }

    @Test
    public void shouldEncode() throws CborException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        CborEncoder encoder = new CborEncoder(byteOutputStream);
        encoder.encode(VALUE);
        Assert.assertArrayEquals(ENCODED_VALUE, byteOutputStream.toByteArray());
    }

    @Test
    public void shouldDecode() throws CborException {
        InputStream inputStream = new ByteArrayInputStream(ENCODED_VALUE);
        CborDecoder decoder = new CborDecoder(inputStream);
        List<DataItem> dataItems = decoder.decode();
        Assert.assertArrayEquals(VALUE.toArray(), dataItems.toArray());
    }

}
