package it.polimi.jasper.parser.sparql;

import lombok.ToString;

/**
 * Created by Riccardo on 06/08/16.
 */
@ToString
public class Prefix {

    private String prefix, uri;


    public Prefix(String match) {
        this.prefix = match.replace(":", "");
    }

    public Prefix setURI(String uri) {
        this.uri = uri.replace(">", "").replace(">", "");
        return this;
    }

    public String getPrefix() {
        return prefix.trim();
    }

    public String getUri() {
        return uri.trim();
    }
}
