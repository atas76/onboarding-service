package ee.tuleva.onboarding.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Configuration
public class HttpsURLConnectionConfiguration {

    @Value("${ssl.keystore:#{null}}")
    private String base64PKCSKeystore;

    @Value("${ssl.keystore.password:#{null}}")
    private String keystorePassword;

    @Value("${ssl.trustAllHTTPSHosts:#{false}}")
    private Boolean trustAllHTTPSHosts;

    @PostConstruct
    public void initialize() {

        KeyManager[] keyManagers = getKeyManagers();
        TrustManager[] trustManagers = getTrustManagers();
        initializeSslContext(keyManagers, trustManagers);

        if (trustAllHTTPSHosts) {
            HttpsURLConnection.setDefaultHostnameVerifier(new DummyHostVerifier());
        }
    }

    private void initializeSslContext(KeyManager[] keyManagers, TrustManager[] trustManagers) {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyManagers, trustManagers, new SecureRandom());
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private TrustManager[] getTrustManagers() {
        return new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String t) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String t) {
            }
        } };
    }

    private KeyManager[] getKeyManagers() {
        KeyManager[] keyManagers = null;

        try {
            if (!isBlank(base64PKCSKeystore)) {
                byte[] p12 = Base64.getDecoder().decode(this.base64PKCSKeystore);
                ByteArrayInputStream stream = new ByteArrayInputStream(p12);

                KeyStore keyStore = KeyStore.getInstance("pkcs12");
                keyStore.load(stream, this.keystorePassword.toCharArray());
                stream.close();

                String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(defaultAlgorithm);
                keyManagerFactory.init(keyStore, this.keystorePassword.toCharArray());
                keyManagers = keyManagerFactory.getKeyManagers();
            }

        } catch (KeyStoreException | IOException | CertificateException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return keyManagers;
    }

    static class DummyHostVerifier implements HostnameVerifier {
        public boolean verify(String name, SSLSession sess) {
            return true;
        }
    }


}
