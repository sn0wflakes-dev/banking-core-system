package aji.intern.core.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaCrypto {

    private static final Logger log = LogManager.getLogger(RsaCrypto.class);

    private static final Integer RSA_KEY_SIZE = 2048;
    private static final OAEPParameterSpec OAEP_SHA256 =
            new OAEPParameterSpec(
                    "SHA-256",
                    "MGF1",
                    MGF1ParameterSpec.SHA256,
                    PSource.PSpecified.DEFAULT
            );

    public RsaCrypto() {}

    public static KeyPair genKey() {
        try {
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(RSA_KEY_SIZE);
            return keyGenerator.genKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            log.error("Failed to generate Key Pair");
            throw new RuntimeException(ex);
        }
    }

    public static String encrypt(String pin, String key) throws Exception {
        PublicKey publicKey = getPublicKeyFromString(key);

        // Encrypt using RSA-OAEP
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, OAEP_SHA256);

        byte[] encryptedBytes = cipher.doFinal(pin.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String base64Ciphertext, String key) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(base64Ciphertext);
        PrivateKey privateKey = getPrivateKeyFromString(key);

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey, OAEP_SHA256);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String getPublicKeyAsBase64(PublicKey publicKey) {
        byte[] privateBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateBytes);
    }

    public static String getPrivateKeyAsBase64(PrivateKey privateKey) {
        byte[] privateBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateBytes);
    }

    public static  PublicKey getPublicKeyFromString(String publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedKey = decodeKeyFromString(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    private static PrivateKey getPrivateKeyFromString(String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedKey = decodeKeyFromString(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    private static byte[] decodeKeyFromString(String key) {
        return Base64.getDecoder().decode(key);
    }

}
