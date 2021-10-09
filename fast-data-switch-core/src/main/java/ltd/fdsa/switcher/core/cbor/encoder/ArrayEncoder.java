package ltd.fdsa.switcher.core.cbor.encoder;

import java.io.OutputStream;
import java.util.List;

import ltd.fdsa.switcher.core.cbor.CborEncoder;
import ltd.fdsa.switcher.core.cbor.CborException;
import ltd.fdsa.switcher.core.cbor.model.Array;
import ltd.fdsa.switcher.core.cbor.model.DataItem;
import ltd.fdsa.switcher.core.cbor.model.MajorType;

public class ArrayEncoder extends AbstractEncoder<Array> {

    public ArrayEncoder(CborEncoder encoder, OutputStream outputStream) {
        super(encoder, outputStream);
    }

    @Override
    public void encode(Array array) throws CborException {
        List<DataItem> dataItems = array.getDataItems();
        if (array.isChunked()) {
            encodeTypeChunked(MajorType.ARRAY);
        } else {
            encodeTypeAndLength(MajorType.ARRAY, dataItems.size());
        }
        for (DataItem dataItem : dataItems) {
            encoder.encode(dataItem);
        }
    }

}
