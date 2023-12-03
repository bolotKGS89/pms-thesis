package it.ringmaster.configuration;

import com.amazon.pay.api.PayConfiguration;
import com.amazon.pay.api.exceptions.AmazonPayClientException;
import com.amazon.pay.api.types.Environment;
import com.amazon.pay.api.types.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class AmazonPayConfig {

    @Bean
    public PayConfiguration payConfiguration() {
        PayConfiguration payConfiguration = null;
        try {
            payConfiguration = new PayConfiguration()
                    .setPublicKeyId("SANDBOX-AHCKWTDF45IKNRPAOH3S2UG7")
                    .setRegion(Region.EU)
                    .setPrivateKey(this.readPrivateKeyString())
                    .setEnvironment(Environment.SANDBOX)
                    .setAlgorithm("AMZN-PAY-RSASSA-PSS-V2"); // Amazon Signing Algorithm, Optional: uses AMZN-PAY-RSASSA-PSS if not specified
        } catch (AmazonPayClientException e) {
            e.printStackTrace(); // Handle exception appropriately in your application
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return payConfiguration;
    }

    private String readPrivateKeyString() throws IOException {
        // Load the .pem file from the resources folder
        ClassPathResource classPathResource = new ClassPathResource("AmazonPay_SANDBOX-AHCKWTDF45IKNRPAOH3S2UG7.pem");
        InputStream inputStream = classPathResource.getInputStream();

        // Convert InputStream to String
        String pemContents = convertInputStreamToString(inputStream);

        // Close the InputStream
        inputStream.close();

        return pemContents;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
