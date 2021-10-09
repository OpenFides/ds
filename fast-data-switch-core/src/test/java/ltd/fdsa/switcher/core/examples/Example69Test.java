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

/**
 * {"a": 1, "b": [2,3]} -> 0xa26161016162820203
 */
public class Example69Test {

    private static final List<DataItem> VALUE = new CborBuilder().addMap().put("a", 1).putArray("b").add(2).add(3).end()
        .end().build();

    private static final byte[] ENCODED_VALUE = new byte[] { (byte) 0xa2, 0x61, 0x61, 0x01, 0x61, 0x62, (byte) 0x82,
            0x02, 0x03 };

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
