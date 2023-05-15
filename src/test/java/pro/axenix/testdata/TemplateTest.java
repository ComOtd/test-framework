package pro.axenix.testdata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class TemplateTest {
    @Test
    void testFill() throws ParseException {
        Template template = new Template("\"test\":\"${testValue}\", \"int\":${intValue}");
        Map<String, String> values = new HashMap<>();
        values.put("testValue", "OK");
        values.put("intValue", "10");
        String res = template.fill(values);
        Assertions.assertEquals("\"test\":\"OK\", \"int\":10", res);
    }
}
