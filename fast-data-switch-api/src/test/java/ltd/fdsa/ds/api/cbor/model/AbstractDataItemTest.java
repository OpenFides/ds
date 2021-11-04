package ltd.fdsa.ds.api.cbor.model;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;

import ltd.fdsa.ds.api.cbor.CborDecoder;
import ltd.fdsa.ds.api.cbor.CborEncoder;
import ltd.fdsa.ds.api.cbor.CborException;

public abstract class AbstractDataItemTest {

    protected void shouldEncodeAndDecode(String message, DataItem dataItem) throws CborException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CborEncoder encoder = new CborEncoder(byteArrayOutputStream);
        encoder.encode(dataItem);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DataItem object = CborDecoder.decode(bytes).get(0);
        Assert.assertEquals(message, dataItem, object);
    }

}
