package ltd.fdsa.ds.api.examples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ltd.fdsa.ds.api.cbor.CborBuilder;
import ltd.fdsa.ds.api.cbor.CborDecoder;
import ltd.fdsa.ds.api.cbor.CborEncoder;
import ltd.fdsa.ds.api.cbor.CborException;
import ltd.fdsa.ds.api.cbor.model.DataItem;

/**
 * {_ "Fun": true, "Amt": -2} -> 0xbf6346756ef563416d7421ff
 */
public class Example82Test {

    private static final List<DataItem> VALUE = new CborBuilder().startMap().put("Fun", true).put("Amt", -2).end()
        .build();

    private static final byte[] ENCODED_VALUE = new byte[] { (byte) 0xbf, 0x63, 0x46, 0x75, 0x6e, (byte) 0xf5, 0x63,
            0x41, 0x6d, 0x74, 0x21, (byte) 0xff };

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
