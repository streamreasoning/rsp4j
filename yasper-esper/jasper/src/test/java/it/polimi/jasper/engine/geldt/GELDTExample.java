package it.polimi.jasper.engine.geldt;

import it.polimi.sr.rsp.csparql.engine.CSPARQLEngine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public abstract class GELDTExample {

    static CSPARQLEngine sr;
    static String type = "image";

    public static String getQuery(String nameQuery, String suffix, String type) throws IOException {
        URL resource = GELDTExample.class.getResource("/geldt/streams/" + type + "/" + nameQuery + suffix);
        System.out.println(resource.getPath());
        File file = new File(resource.getPath());
        return FileUtils.readFileToString(file);
    }

}
