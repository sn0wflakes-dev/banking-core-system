package aji.intern.core;

import aji.intern.core.security.RsaCrypto;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RsaCryptoTest {

    @Test
    void rsaShouldEncryptAndDecrypt() throws Exception {

        KeyPair keyPair = RsaCrypto.genKey();

        String publicKey =
                RsaCrypto.getPublicKeyAsBase64(keyPair.getPublic());

        String privateKey =
                RsaCrypto.getPrivateKeyAsBase64(keyPair.getPrivate());

        String original = "123456";

        String encrypted =
                RsaCrypto.encrypt(original, publicKey);

        String decrypted =
                RsaCrypto.decrypt(encrypted, privateKey);

        assertEquals(original, decrypted);
    }
}
