package ltd.fdsa.switcher.core.cbor.encoder;

import java.io.OutputStream;
import java.math.BigInteger;

import ltd.fdsa.switcher.core.cbor.CborEncoder;
import ltd.fdsa.switcher.core.cbor.CborException;
import ltd.fdsa.switcher.core.cbor.model.MajorType;
import ltd.fdsa.switcher.core.cbor.model.NegativeInteger;

public class NegativeIntegerEncoder extends AbstractEncoder<NegativeInteger> {

    private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1);

    public NegativeIntegerEncoder(CborEncoder encoder, OutputStream outputStream) {
        super(encoder, outputStream);
    }

    @Override
    public void encode(NegativeInteger dataItem) throws CborException {
        encodeTypeAndLength(MajorType.NEGATIVE_INTEGER, MINUS_ONE.subtract(dataItem.getValue()).abs());
    }

}
