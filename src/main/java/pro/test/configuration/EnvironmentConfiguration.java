package pro.test.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentConfiguration {
    private List<DataBaseConfiguration> database;
    private List<HttpConfiguration> http;

    public DataBaseConfiguration getDatabaseConfiguration(String name){
        return database.stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Не найдена конфигурация db"));
    }

    public String getHttpHostUrl(String name){
        HttpConfiguration httpConfiguration = http.stream()
                .filter(h -> h.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Не найдена конфигурация http"));
        return httpConfiguration.getUrl();
    }
}
