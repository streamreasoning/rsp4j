package org.streamreasoning.rsp4j.wspbook.wildstreams.gdeltutils;

import com.taxonic.carml.engine.function.FnoFunction;
import com.taxonic.carml.engine.function.FnoParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class GeoNamesLocationsLookup {

  private static String lookup_url = "http://api.geonames.org/search?";
  private static String username = "AlexMV12";
  private static int max_rows = 1;
  private static double fuzziness =
      0.9; // This parameter is used to find results even if parameter is mispelled
  private static HashMap<String, String> countryCodeConverterTable = new HashMap<>();

  public GeoNamesLocationsLookup() {

    String fips104 =
        "AF,AL,AG,AQ,AN,AO,AV,AY,AC,AR,AM,AA,AS,AU,AJ,BF,BA,BG,BB,BO,BE,BH,BN,BD,BT,BL,BK,BC,BV,BR,IO,BX,BU,UV,BY,CV,CB,CM,CA,CJ,CT,CD,CI,CH,KT,CK,CO,CN,CF,CG,CW,CS,IV,HR,CU,UC,CY,EZ,DK,DJ,DO,DR,EC,EG,ES,EK,ER,EN,ET,FK,FO,FJ,FI,FR,FG,FP,FS,GB,GA,GG,GM,GH,GI,GR,GL,GJ,GP,GQ,GT,GK,GV,PU,GY,HA,HM,VT,HO,HK,HU,IC,IN,ID,IR,IZ,EI,IM,IS,IT,JM,JA,JE,JO,KZ,KE,KR,KN,KS,KU,KG,LA,LG,LE,LT,LI,LY,LS,LH,LU,MC,MK,MA,MI,MY,MV,ML,MT,RM,MB,MR,MP,MF,MX,FM,MD,MN,MG,MJ,MH,MO,MZ,BM,WA,NR,NP,NL,NC,NZ,NU,NG,NI,NE,NF,CQ,NO,MU,PK,PS,WE,PM,PP,PA,PE,RP,PC,PL,PO,RQ,QA,RE,RO,RS,RW,TB,SH,SC,ST,RN,SB,VC,WS,SM,TP,SA,SG,RI,SE,SL,SN,NN,LO,SI,BP,SO,SF,SX,OD,SP,CE,SU,NS,SV,WZ,SW,SZ,SY,TW,TI,TZ,TH,TT,TO,TL,TN,TD,TS,TU,TX,TK,TV,UG,UA,AE,UK,US,UY,UZ,NH,VE,VN,VI,VQ,WF,WI,YM,ZA,ZI";
    String iso3166 =
        "AF,AL,DZ,AS,AD,AO,AI,AQ,AG,AR,AM,AW,AU,AT,AZ,BS,BH,BD,BB,BY,BE,BZ,BJ,BM,BT,BO,BA,BW,BV,BR,IO,BN,BG,BF,BI,CV,KH,CM,CA,KY,CF,TD,CL,CN,CX,CC,CO,KM,CG,CD,CK,CR,CI,HR,CU,CW,CY,CZ,DK,DJ,DM,DO,EC,EG,SV,GQ,ER,EE,ET,FK,FO,FJ,FI,FR,GF,PF,TF,GA,GM,GE,DE,GH,GI,GR,GL,GD,GP,GU,GT,GG,GN,GW,GY,HT,HM,VA,HN,HK,HU,IS,IN,ID,IR,IQ,IE,IM,IL,IT,JM,JP,JE,JO,KZ,KE,KI,KP,KR,KW,KG,LA,LV,LB,LS,LR,LY,LI,LT,LU,MO,MK,MG,MW,MY,MV,ML,MT,MH,MQ,MR,MU,YT,MX,FM,MD,MC,MN,ME,MS,MA,MZ,MM,NA,NR,NP,NL,NC,NZ,NI,NE,NG,NU,NF,MP,NO,OM,PK,PW,PS,PA,PG,PY,PE,PH,PN,PL,PT,PR,QA,RE,RO,RU,RW,BL,SH,KN,LC,MF,PM,VC,WS,SM,ST,SA,SN,RS,SC,SL,SG,SX,SK,SI,SB,SO,ZA,GS,SS,ES,LK,SD,SR,SJ,SZ,SE,CH,SY,TW,TJ,TZ,TH,TL,TG,TK,TO,TT,TN,TR,TM,TC,TV,UG,UA,AE,GB,US,UY,UZ,VU,VE,VN,VG,VI,WF,EH,YE,ZM,ZW";

    String[] fips104codes = fips104.split(",");
    String[] iso3166codes = iso3166.split(",");

    for (int i = 0; i < fips104codes.length; i++) {
      countryCodeConverterTable.put(fips104codes[i], iso3166codes[i]);
    }
  }

  /*  Items from "Locations" column in GKG are in this format:
         <Field1#Field2#...>;<Field1#Field2#...>;...
         Fields are:
             1 - Location type: - 1: Country
                                  2: US State
                                  3: US City
                                  4: World city
                                  5: World country
             2 - Location fullname: - US/World state: "State, country name"
                                    - Else: "City/Landmark, State, Country"
             3 - Location country code: 2-Chars FIPS10-4 code
             4 - Location ADM1Code: Ignored
             5 - Latitude
             6 - Longitude
             7 - Feature ID: Ignored
             Examples:
             1#Mexico#MX#MX#23#-102#MX;2#New Mexico, United States#US#USNM#34.8375#-106.237#NM
             4#Moscow, Moskva, Russia#RS#RS48#55.7522#37.6156#-2960561;4#Kremlin, Moskva, Russia#RS#RS48#55.7522#37.6156#-2960561;1#Russia#RS#RS#60#100#RS;1#France#FR#FR#46#2#FR;1#Israel#IS#IS#31.5#34.75#IS;3#Washington, Washington, United States#US#USDC#38.8951#-77.0364#531871
  */

  @FnoFunction("http://example.org/lookup")
  public String lookup(@FnoParam("http://example.org/toLookup") String locationsToLookup)
      throws IOException {
    String[] locations = locationsToLookup.split(";");

    String result = "";

    for (String location : locations) {
      String[] fields = location.split("#");

      String name = fields[1].split(",")[0];

      // CountryCode used in GDELT is FIPS10-4, while country code used in GeoNames is ISO-3166.
      String countryCode = countryCodeConverterTable.get(fields[2]);

      try {
        String query =
            "name_equals="
                + name
                + "&country="
                + countryCode
                + "&maxRows="
                + max_rows
                + "&type=rdf&fuzzy="
                + fuzziness
                + "&username="
                + username;
        query = query.replace(" ", "%20");
        URL url = new URL(lookup_url + query);
        System.out.println("URL:" + url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader br;
        if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
          br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          String line;
          while ((line = br.readLine()) != null) {
            result = result.concat("\n" + line);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return result; // This result is a RDF piece of text
  }

  public static void main(String[] args) {
    GeoNamesLocationsLookup geoNamesLocationsLookup = new GeoNamesLocationsLookup();
    String example =
        "1#United States#US#US#38#-97#US;1#Malaysia#MY#MY#2.5#112.5#MY;4#Piestany, Trnavský, Slovak Republic#LO#LO07#48.5948#17.8259#-844975;1#Nigeria#NI#NI#10#8#NI;4#Roma, Lazio, Italy#IT#IT07#41.9#12.4833#-126693;4#Kosice, Kosicky, Slovak Republic#LO#LO03#48.7139#21.2581#-843247;4#Bratislava, Bratislavsky Kraj, Slovak Republic#LO#LO02#48.15#17.1167#-840999;1#North Korea#KN#KN#40#127#KN;4#Presov, PrešOvský, Slovak Republic#LO#LO05#48.9984#21.2339#-845236;4#Tatranska Lomnica, PrešOvský, Slovak Republic#LO#LO05#49.1651#20.2843#-846442;1#United Kingdom#UK#UK#54#-2#UK";
    try {
      System.out.println(geoNamesLocationsLookup.lookup(example));
    } catch (IOException e) {

    }
  }
}