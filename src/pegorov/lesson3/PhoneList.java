package pegorov.lesson3;

import java.util.TreeMap;
import java.util.TreeSet;

public class PhoneList {
    private TreeMap<String, TreeSet<String>> treeMap;

    public PhoneList(){
        treeMap = new TreeMap<>();
    }

    public void add(String name, String phone){
        if(treeMap.containsKey(name)){
            treeMap.get(name).add(phone);
        }else{
            TreeSet<String> treeSet = new TreeSet<>();
            treeSet.add(phone);

            treeMap.put(name, treeSet);
        }
    }

    public String get(String name){
        if (treeMap.containsKey(name)){
            return treeMap.get(name).toString();
        }else{
            return "phone is absent";
        }
    }
}
