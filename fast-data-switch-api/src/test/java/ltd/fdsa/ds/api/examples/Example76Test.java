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
import ltd.fdsa.ds.api.cbor.model.Array;
import ltd.fdsa.ds.api.cbor.model.DataItem;
import ltd.fdsa.ds.api.cbor.model.SimpleValue;

/**
 * [_ 1, [2, 3], [4,5]] -> 0x9f01820203820405ff
 */
public class Example76Test {

    private static final List<DataItem> VALUE = new CborBuilder().add(new Array().setChunked(true)).add(1).addArray()
        .add(2).add(3).end().addArray().add(4).add(5).end().add(SimpleValue.BREAK).build();

    private static final byte[] ENCODED_VALUE = new byte[] { (byte) 0x9f, 0x01, (byte) 0x82, 0x02, 0x03, (byte) 0x82,
            0x04, 0x05, (byte) 0xff };

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
        decoder.setAutoDecodeInfinitiveArrays(false);
        List<DataItem> dataItems = decoder.decode();
        Assert.assertArrayEquals(VALUE.toArray(), dataItems.toArray());
    }

}
