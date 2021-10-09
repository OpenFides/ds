package ltd.fdsa.switcher.core.cbor.encoder;

import java.io.OutputStream;

import ltd.fdsa.switcher.core.cbor.CborEncoder;
import ltd.fdsa.switcher.core.cbor.CborException;
import ltd.fdsa.switcher.core.cbor.model.SinglePrecisionFloat;

public class SinglePrecisionFloatEncoder extends AbstractEncoder<SinglePrecisionFloat> {

    public SinglePrecisionFloatEncoder(CborEncoder encoder, OutputStream outputStream) {
        super(encoder, outputStream);
    }

    @Override
    public void encode(SinglePrecisionFloat dataItem) throws CborException {
        int bits = Float.floatToRawIntBits(dataItem.getValue());
        write((byte) ((7 << 5) | 26), (byte) ((bits >> 24) & 0xFF), (byte) ((bits >> 16) & 0xFF),
            (byte) ((bits >> 8) & 0xFF), (byte) ((bits >> 0) & 0xFF));
    }

}
