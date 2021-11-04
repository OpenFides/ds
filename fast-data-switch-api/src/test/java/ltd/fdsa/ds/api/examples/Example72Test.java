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
 * (_ h'0102', h'030405') -> 0x5f42010243030405ff
 */
public class Example72Test {

    private static final List<DataItem> VALUE = new CborBuilder().startByteString().add(new byte[] { 0x01, 0x02 })
        .add(new byte[] { 0x03, 0x04, 0x05 }).end().build();

    private static final byte[] ENCODED_VALUE = new byte[] { 0x5f, 0x42, 0x01, 0x02, 0x43, 0x03, 0x04, 0x05,
            (byte) 0xff };

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
        decoder.setAutoDecodeInfinitiveByteStrings(false);
        List<DataItem> dataItems = decoder.decode();
        Assert.assertArrayEquals(VALUE.toArray(), dataItems.toArray());
    }

}
