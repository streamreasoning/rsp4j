package it.polimi.jasper.parser.streams;

import lombok.*;
import org.apache.jena.graph.Node;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Riccardo on 12/08/16.
 */
@Data
@NoArgsConstructor
@ToString(exclude = {"regex", "p"})
public class Register {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    final private String regex = "([0-9]+)\\s*(ms|s|m|h|d|GRAPH|TRIPLES)";
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    final private Pattern p = Pattern.compile(regex);
    private Node id;
    private Integer every;
    private String unit;
    private Type type;

    public Register setId(Node id) {
        this.id = id;
        return this;
    }

    public Register setType(String type) {
        this.type = Type.valueOf(type);
        return this;
    }

    public Register addCompute(String match) {
        System.out.println(match);
        // TODO hide visibility out of the package
        Matcher matcher = p.matcher(match);
        if (matcher.find()) {
            MatchResult res = matcher.toMatchResult();
            this.every = Integer.parseInt(res.group(1));
            this.unit = res.group(2);
        }
        return this;
    }

    public enum Type {
        STREAM("STREAM"), QUERY("QUERY");

        String s;

        Type(String s) {
            this.s = s.toUpperCase();
        }
    }
}
