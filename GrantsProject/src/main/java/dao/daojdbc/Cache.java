package dao.daojdbc;

import offices.Identificateble;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cache {
    private int size;
    private LinkedHashMap<String, Identificateble> cache;

    public Cache(int size){
        this.size = size;
        cache = new LinkedHashMap<>(size);
    }

    public void addInCache (Identificateble obj){
        while (cache.size() >= size){
            deleteFirst();
        }

        cache.put(obj.getId(), obj);

    }

    private void deleteFirst(){
        Iterator iterator = cache.entrySet().iterator();
        if(iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
    }

    public void deleteFromCache (String id){
        cache.remove(id);
    }

    public Identificateble getObjectIfExists(String id){
        return cache.get(id);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void print(){
        for(Map.Entry<String, Identificateble> map : cache.entrySet()){
            System.out.println(map.getKey() + ":" + map.getValue());
        }
        System.out.println("--------------");
    }
}
