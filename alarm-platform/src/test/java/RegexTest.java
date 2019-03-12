import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @create: 2019-03-12 11:19
 **/
public class RegexTest {
    @Test
    public void test(){
        String line = "$.aggregations.result.buckets[?(@.doc_count >150)]";

        String pattern = ".*@\\.(.*)(<|>|==)";
        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        System.out.println(m.groupCount());
        if (m.find( )) {
            System.out.println("Found value: " + m.group(1) );
        } else {
            System.out.println("NO MATCH");
        }
    }
}
