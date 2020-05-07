package neo4j.poc.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import neo4j.PGraph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

public class PGraphImpl implements PGraph {
    Collection<Event> events;

    public PGraphImpl() {
        URL url = getClass().getResource("/SocialNetwork");
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Event>>() {
        }.getType();
        try {
            this.events = gson.fromJson(new FileReader(url.getPath()), collectionType);
        } catch (FileNotFoundException e) {
            this.events = Collections.EMPTY_LIST;
            e.printStackTrace();
        }
    }

    // {"initiated": "Cory", "accepted": "Levi", "friends": true, "date": "2019-08-08T16:13:11.774754"}

    @Override
    public List<String> nodes() throws FileNotFoundException {
        Set<String> s = new HashSet<>();
        List<String> personNames = new ArrayList<>();
        events.forEach(event -> {
            s.add(event.getAccepted());
            s.add(event.getInitiated());
        });
        personNames.addAll(s);
        return personNames; // ["Cory","Zidane","Kaka"...]
    }

    @Override
    public List<String[]> edges() throws FileNotFoundException {
        //String[] strings = {"Cory", "Levi", "friends"};
        List<String[]> strings = new ArrayList<String[]>();

        events.forEach(event -> {
            strings.add(new String[]{event.getInitiated(), event.getAccepted(), "friends", event.getDate()});
        });

        //List<String[]> strings1 = Arrays.asList(new String[][]{strings});
        return strings; // [{"Cory","Levi","friends","2019-05-02"}, ...]
    }

    @Override
    public long timestamp() {
        return 0;
    }
}
