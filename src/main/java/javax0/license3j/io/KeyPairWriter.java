package javax0.license3j.io;

import javax0.license3j.crypto.LicenseKeyPair;

import java.io.*;
import java.util.Base64;

public class KeyPairWriter implements Closeable {
    private final OutputStream osPrivate;
    private final OutputStream osPublic;

    public KeyPairWriter(OutputStream osPrivate, OutputStream osPublic) {
        this.osPrivate = osPrivate;
        this.osPublic = osPublic;
    }

    public KeyPairWriter(File priv, File publ) throws FileNotFoundException {
        this(new FileOutputStream(priv), new FileOutputStream(publ));
    }

    public KeyPairWriter(String priv, String publ) throws FileNotFoundException {
        this(new File(priv), new File(publ));
    }

    public void write(LicenseKeyPair pair, IOFormat format) throws IOException {
        switch (format) {
            case BINARY:
                osPrivate.write(pair.getPrivate());
                osPublic.write(pair.getPublic());
                return;
            case BASE64:
                osPrivate.write(Base64.getEncoder().encode(pair.getPrivate()));
                osPublic.write(Base64.getEncoder().encode(pair.getPublic()));
                return;
        }
        throw new IllegalArgumentException("Key format " + format + " is unknown.");
    }

    @Override
    public void close() throws IOException {
        if (osPrivate != null) {
            osPrivate.close();
        }
        if (osPublic != null) {
            osPublic.close();
        }
    }
}
