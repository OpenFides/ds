package ltd.fdsa.switcher.core.examples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import ltd.fdsa.switcher.core.cbor.CborDecoder;
import ltd.fdsa.switcher.core.cbor.CborEncoder;
import ltd.fdsa.switcher.core.cbor.CborException;
import ltd.fdsa.switcher.core.cbor.model.DataItem;
import ltd.fdsa.switcher.core.cbor.model.SimpleValue;

/**
 * undefined -> 0xf7
 */
public class Example44Test {

    private static final byte[] ENCODED_VALUE = new byte[] { (byte) 0xf7 };

    @Test
    public void shouldEncode() throws CborException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        CborEncoder encoder = new CborEncoder(byteOutputStream);
        encoder.encode(SimpleValue.UNDEFINED);
        Assert.assertArrayEquals(ENCODED_VALUE, byteOutputStream.toByteArray());
    }

    @Test
    public void shouldDecode() throws CborException {
        InputStream inputStream = new ByteArrayInputStream(ENCODED_VALUE);
        CborDecoder decoder = new CborDecoder(inputStream);
        DataItem dataItem = decoder.decodeNext();
        Assert.assertTrue(dataItem instanceof SimpleValue);
        SimpleValue simpleValue = (SimpleValue) dataItem;
        Assert.assertEquals(SimpleValue.UNDEFINED, simpleValue);
    }

}
