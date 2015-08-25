package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<String, Object>();
        data.put("test", 1);
        
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> synMap = Collections.synchronizedMap(map);
        synMap.put("test", 2);
        
        //深入了解集合
        List<String> collection = new ArrayList<String>();
        collection.add("zhang san");
        collection.add("li si");
        collection.add("wang wu");
        
        for (int i = 0; i < collection.size(); i++) {
            String value = collection.get(i);
            if ("zhang san".equals(value)) {
                collection.remove(value);
            } else {
                System.out.println(value);
            }
        }
        
        //内部用iterator 实现，一样报错
        /*for (String value : collection) {
            if ("zhang san".equals(value)) {
                collection.remove(value);
            } else {
                System.out.println(value);
            }
        }*/
        
        /*Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            String value = iterator.next();
            if ("li si".equals(value)) {
                collection.remove(value);
            } else {
                System.out.println(value);
            }
        }*/
    }
}
