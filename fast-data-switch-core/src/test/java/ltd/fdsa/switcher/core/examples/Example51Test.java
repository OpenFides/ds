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
import ltd.fdsa.switcher.core.cbor.model.ByteString;
import ltd.fdsa.switcher.core.cbor.model.DataItem;

/**
 * 23(h'01020304') -> 0xd74401020304
 */
public class Example51Test {

    private final List<DataItem> VALUE;

    private final byte[] ENCODED_VALUE = new byte[] { (byte) 0xd7, 0x44, 0x01, 0x02, 0x03, 0x04 };

    public Example51Test() {
        DataItem di = new ByteString(new byte[] { 0x01, 0x02, 0x03, 0x04 });
        di.setTag(23);

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
