package pro.axenix.testdata;

import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequiredArgsConstructor
public class Template {
    private final String body;

    public String fill(Map<String, String> values) throws ParseException {
        Pattern pattern = Pattern.compile("(\\$\\{([\\w|-]*)\\})");
        StringBuilder buffer = new StringBuilder(this.body);
        Matcher matcher = pattern.matcher(buffer);
        int start = 0;
        while (matcher.find(start)) {
            String key = matcher.group(2);
            if (values.containsKey(key)) {
                String value = values.get(key);
                buffer.replace(matcher.start(1), matcher.end(1), value);
                start = matcher.start(1) + value.length();
            } else {
                throw new ParseException(String.format("Variable %s not found", key), start);
            }
        }
        return buffer.toString();
    }
}
