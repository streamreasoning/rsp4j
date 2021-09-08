package org.streamreasoning.rsp.enums;

import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.api.RDFUtils;

public enum License {

    CC("https://creativecommons.org/licenses/by-nc/4.0/"), MIT("https://opensource.org/licenses/MIT"), Apache2("https://opensource.org/licenses/Apache-2.0");
    private final IRI url;

    License(String s) {
        this.url = RDFUtils.createIRI(s);
    }

    public IRI url() {
        return url;
    }
}
