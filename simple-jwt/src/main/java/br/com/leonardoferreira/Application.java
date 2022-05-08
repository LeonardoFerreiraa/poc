package br.com.leonardoferreira;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import static br.com.leonardoferreira.KeyType.ASYMMETRIC;
import static br.com.leonardoferreira.KeyType.SYMMETRIC;

public class Application {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {
        System.out.println("\n\n\n\n\n============================================================================================");
        final Algorithm algorithm = Algorithm.HS512;
        final Pair<Key, Key> keyPair = KeyRing.newKeyForAlgorithm(algorithm);

        final String jwt = createJwt(keyPair.second(), algorithm);
        System.out.println(jwt);

        final Triple<Header, Payload, String> result = decodeJwt(keyPair.first(), jwt);
        System.out.println(result);
        System.out.println("============================================================================================\n\n\n\n\n");
    }

    private static String createJwt(final Key key, final Algorithm algorithm) {
        final Header header = new Header(algorithm);
        final Payload payload = new Payload("leoferreira");

        final String encodedHeader = Base64.encode(Json.encode(header));
        final String encodedPayload = Base64.encode(Json.encode(payload));
        final String signature = Signer.sign(
                key,
                algorithm,
                encodedHeader + "." + encodedPayload
        );

        return encodedHeader + "." + encodedPayload + "." + signature;
    }

    private static Triple<Header, Payload, String> decodeJwt(final Key key, final String jwt) {
        final String[] split = jwt.split("\\.");
        if (split.length != 3) {
            throw new IllegalArgumentException("invalid jwt format");
        }

        final String rawHeader = split[0];
        final Header header = Json.decode(Base64.decode(rawHeader), Header.class);

        final Algorithm algorithm = header.alg();

        final String rawPayload = split[1];
        final String signature = split[2];

        final boolean result = Signer.verify(
                key,
                algorithm,
                rawHeader + "." + rawPayload,
                signature
        );
        if (!result) {
            throw new IllegalArgumentException("signature doesnt match");
        }

        final Payload payload = Json.decode(Base64.decode(rawPayload), Payload.class);
        return new Triple<>(header, payload, signature);
    }

}

record Header(Algorithm alg) {
}

record Payload(String username) {
}

enum Algorithm {
    HS256("HmacSHA256", "HmacSHA256", SYMMETRIC),
    HS384("HmacSHA384", "HmacSHA384", SYMMETRIC),
    HS512("HmacSHA512", "HmacSHA512", SYMMETRIC),

    RS256("RSA", "SHA256withRSA", ASYMMETRIC),
    RS384("RSA", "SHA384withRSA", ASYMMETRIC),
    RS512("RSA", "SHA512withRSA", ASYMMETRIC),

    ES256("ECDSA", "SHA256withECDSA", ASYMMETRIC),
    ES384("ECDSA", "SHA384withECDSA", ASYMMETRIC),
    ES512("ECDSA", "SHA512withECDSA", ASYMMETRIC),

    PS256("RSA", "SHA256withRSA", ASYMMETRIC),
    PS384("RSA", "SHA256withRSA", ASYMMETRIC),
    PS512("RSA", "SHA256withRSA", ASYMMETRIC);

    private final String keyAlgorithm;
    private final String signAlgorithm;
    private final KeyType keyType;

    Algorithm(final String keyAlgorithm, final String signAlgorithm, final KeyType keyType) {
        this.keyAlgorithm = keyAlgorithm;
        this.signAlgorithm = signAlgorithm;
        this.keyType = keyType;
    }

    public String getKeyAlgorithm() {
        return keyAlgorithm;
    }

    public String getSignAlgorithm() {
        return signAlgorithm;
    }

    public KeyType getKeyType() {
        return keyType;
    }
}

enum KeyType {
    ASYMMETRIC,
    SYMMETRIC
}

class Base64 {

    public static String encode(final byte[] data) {
        final byte[] rawResult = java.util.Base64.getUrlEncoder()
                .withoutPadding()
                .encode(data);
        return new String(rawResult);
    }

    public static String encode(final String str) {
        return encode(str.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decode(String str) {
        return java.util.Base64.getUrlDecoder()
                .decode(str.getBytes(StandardCharsets.UTF_8));
    }

}

class Json {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String encode(final Object target) {
        try {
            return OBJECT_MAPPER.writeValueAsString(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T decode(final byte[] json, final Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

class KeyRing {

    public static Pair<Key, Key> newKeyForAlgorithm(final Algorithm algorithm) {
        return switch (algorithm.getKeyType()) {
            case SYMMETRIC -> {
                final SecretKey secretKey = KeyRing.newSecretKey(algorithm);
                yield new Pair<>((Key) secretKey, (Key) secretKey);
            }
            case ASYMMETRIC -> {
                final Pair<PublicKey, PrivateKey> keyPair = KeyRing.newKeyPair(algorithm);
                yield new Pair<>((Key) keyPair.first(), (Key) keyPair.second());
            }
        };
    }

    public static Pair<PublicKey, PrivateKey> newKeyPair(final Algorithm algorithm) {
        try {
            final KeyPair kp = KeyPairGenerator.getInstance(algorithm.getKeyAlgorithm())
                    .generateKeyPair();
            return new Pair<>(kp.getPublic(), kp.getPrivate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SecretKey newSecretKey(final Algorithm algorithm) {
        try {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.getKeyAlgorithm());
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Signer {

    public static String sign(final Key key, final Algorithm algorithm, final String data) {
        return switch (algorithm.getKeyType()) {
            case SYMMETRIC -> sign((SecretKey) key, algorithm, data);
            case ASYMMETRIC -> sign((PrivateKey) key, algorithm, data);
        };
    }

    public static String sign(final SecretKey key, final Algorithm algorithm, final String data) {
        try {
            final Mac mac = Mac.getInstance(algorithm.getSignAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
            mac.init(key);
            mac.update(data.getBytes(StandardCharsets.UTF_8));

            return Base64.encode(mac.doFinal());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String sign(final PrivateKey key, final Algorithm algorithm, final String data) {
        try {
            final Signature signature = Signature.getInstance(algorithm.getSignAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
            signature.initSign(key);
            signature.update(data.getBytes(StandardCharsets.UTF_8));

            return Base64.encode(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(final Key key, final Algorithm algorithm, final String data, final String receivedSignature) {
        return switch (algorithm.getKeyType()) {
            case SYMMETRIC -> verify((SecretKey) key, algorithm, data, receivedSignature);
            case ASYMMETRIC -> verify((PublicKey) key, algorithm, data, receivedSignature);
        };
    }

    public static boolean verify(final SecretKey key, final Algorithm algorithm, final String data, final String receivedSignature) {
        try {
            final String computedSignature = sign(key, algorithm, data);

            return MessageDigest.isEqual(
                    Base64.decode(computedSignature),
                    Base64.decode(receivedSignature)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(final PublicKey key, final Algorithm algorithm, final String data, final String receivedSignature) {
        try {
            final Signature signature = Signature.getInstance(algorithm.getSignAlgorithm());
            signature.initVerify(key);
            signature.update(data.getBytes(StandardCharsets.UTF_8));

            return signature.verify(Base64.decode(receivedSignature));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

record Pair<F, S>(F first, S second) {
}

record Triple<F, S, T>(F first, S second, T third) {
}

