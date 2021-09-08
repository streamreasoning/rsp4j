package org.streamreasoning.rsp4j.examples.operators.r2r;

import java.util.*;

public class UpwardExtension {
    private Map<String, Set<String>> extensions;

    public UpwardExtension(Map<String, List<String>> schema) {
        this.extensions = new HashMap<>();
        addSchema(schema);
    }
    public void addSchema(Map<String, List<String>> schema) {
        // 1 To extract the top parents, we extract all concepts and remove those that have parents:
        // 1.1 get all childeren
        Set<String> childeren = new HashSet<String>();
        for (List<String> childs : schema.values()) {
            childeren.addAll(childs);
        }
        // 1.2. get all the types
        Set<String> all = new HashSet<String>(childeren);
        all.addAll(schema.keySet());
        Set<String> tops = new HashSet(all);
        // 1.2 remove the childeren from all the types and the parents remain
        tops.removeAll(childeren);
        // 2 recursively follow the hierarchy from the top parents and build the inference structure
        for (String top : tops) {
            if (!this.extensions.containsKey(top)) {
                this.extensions.put(top, new HashSet<>());
            }
            findSubclasses(top, schema);
        }
    }
    public Set<String> getUpwardExtension(String type){
        if(extensions.containsKey(type)){
            return extensions.get(type);
        }else{
            return Collections.emptySet();
        }
    }

    private void findSubclasses(String parent, Map<String, List<String>> schema) {
        if (schema.containsKey(parent)) {
            for (String child : schema.get(parent)) {
                if (!this.extensions.containsKey(child)) {
                    this.extensions.put(child, new HashSet<>());
                }
                // add all the parent concepts of the current parent to the extensions
                this.extensions.get(child).addAll(this.extensions.get(parent));
                this.extensions.get(child).add(parent);
                // recursive step
                findSubclasses(child, schema);
            }
        }
    }
}
