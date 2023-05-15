package pro.test.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBaseConfiguration {
    private String name;
    private String url;
    private String username;
    private String password;
}
