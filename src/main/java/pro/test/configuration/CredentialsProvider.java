package pro.test.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class CredentialsProvider {

    public Credentials get() throws IOException {
        Path path = Paths.get("src", "test", "resources", "credentials.yml");
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        InputStream fileStream = Files.newInputStream(path);
        log.info("Open file credentials.yml");
        return objectMapper.readValue(fileStream, Credentials.class);
    }
}
