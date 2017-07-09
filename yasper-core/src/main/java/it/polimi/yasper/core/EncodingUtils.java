package it.polimi.yasper.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Riccardo on 20/08/16.
 */
public class EncodingUtils {
    private static final String dotmark = "_1_";
    private static final String percentage = "_2_";

    public static String encode(String uri) {
        try {
            return URLEncoder.encode(uri, "UTF-8").replace(".", dotmark).replace("%", percentage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static String decode(String encoded) {
        try {
            return URLDecoder.decode(encoded.replace(dotmark, ".").replace(percentage, "%"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
