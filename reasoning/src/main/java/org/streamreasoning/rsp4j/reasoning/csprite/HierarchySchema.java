package org.streamreasoning.rsp4j.reasoning.csprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierarchySchema {

    private final HashMap<String, List<String>> schema;

    public HierarchySchema(){
        this.schema = new HashMap<>();
    }
    public void addSubClassOf(String child, String parent) {
        schema.putIfAbsent(parent,new ArrayList<>());
        schema.get(parent).add(child);
    }
    public Map<String,List<String>> getSchema(){
        return schema;
    }
}
