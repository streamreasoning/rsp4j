package ready2go;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestReplace {

    public static void main(String[] args) {

        String s = "SELECT \n" +
                "   1 AS \"cQuestType\", \"\" AS \"cLang\", (\"http://www.streamreasoning.org/ontologies/2018/9/colors#\" || \"Qq1_stream1w1VIEW0\".\"color\") AS \"c\"\n," +
                " (\"http://www.streamreasoning.org/ontologies/2018/9/colors#\" || \"Qq2_stream1w1VIEW3\".\"cas\") AS \"b\"\n" +
                "FROM \n" +
                "    \"q1\".\"stream1w1\" \"Qq1_stream1w1VIEW0\"";

        String[] sql = new String[]{s};

        Matcher m = Pattern.compile("\\((.*?)\\)\\sAS\\s(.*?)").matcher(s);

        Map<String, String> submap = new HashMap<>();
        while (m.find()) {
            String group = m.group(1);
            if (group.contains("||")) {
                String y = group.split("\\|\\|")[0];
                String x = group.split("\\|\\|")[1];
                submap.put("(" + group + ")", "CONCAT(" +
                        y +
                        ", " +
                        x + ")");
            }
        }

        submap.forEach((s1, s2) -> sql[0] = sql[0].replace(s1, s2));

        System.out.println(sql[0]);

    }

}
