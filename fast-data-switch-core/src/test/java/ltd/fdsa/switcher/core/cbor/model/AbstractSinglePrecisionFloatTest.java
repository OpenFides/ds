package ltd.fdsa.switcher.core.cbor.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import ltd.fdsa.switcher.core.cbor.CborDecoder;
import ltd.fdsa.switcher.core.cbor.CborEncoder;
import ltd.fdsa.switcher.core.cbor.CborException;

public abstract class AbstractSinglePrecisionFloatTest {

    private final float value;
    private final byte[] encodedValue;

    public AbstractSinglePrecisionFloatTest(float value, byte[] encodedValue) {
        this.value = value;
        this.encodedValue = encodedValue;
    }

    @Test
    public void shouldEncode() throws CborException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        CborEncoder encoder = new CborEncoder(byteOutputStream);
        encoder.encode(new SinglePrecisionFloat(value));
        Assert.assertArrayEquals(encodedValue, byteOutputStream.toByteArray());
    }

    @Test
    public void shouldDecode() throws CborException {
        InputStream inputStream = new ByteArrayInputStream(encodedValue);
        CborDecoder decoder = new CborDecoder(inputStream);
        DataItem dataItem = decoder.decodeNext();
        Assert.assertTrue(dataItem instanceof SinglePrecisionFloat);
        SinglePrecisionFloat singlePrecisionFloat = (SinglePrecisionFloat) dataItem;
        Assert.assertEquals(value, singlePrecisionFloat.getValue(), 0);
    }

}
