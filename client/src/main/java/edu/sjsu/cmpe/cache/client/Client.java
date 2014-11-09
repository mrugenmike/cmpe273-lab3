package edu.sjsu.cmpe.cache.client;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.google.common.hash.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache3000 = new DistributedCacheService("http://localhost:3000");
        CacheServiceInterface cache3001 = new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface cache3002 = new DistributedCacheService("http://localhost:3002");

        List<CacheServiceInterface> caches = Arrays.asList(cache3000, cache3001, cache3002);
        List<String> values = Arrays.asList("a", "b","c","d","e","f","g","h","i","j");



        for (int key=1;key<=10;key++){
            String currentValue = values.get(key - 1);
            int bucket = Hashing.consistentHash(Hashing.md5().hashString(currentValue), caches.size());
            CacheServiceInterface cache = caches.get(bucket);
            System.out.println("put(" + key +" => " + currentValue + ")");
            cache.put(key, currentValue);
        }

        System.out.println("\n Distribution of Values is: \n" +
                "Server_A => http://localhost:3000/cache/  =>\n"+cache3000.getAllValues()+
                "\nServer_B => http://localhost:3001/cache/  =>\n"+cache3001.getAllValues()+
                "\nServer_C => http://localhost:3002/cache/  =>\n"+cache3002.getAllValues()
        );

        System.out.println("\nFetching values now from Cache .... \n");

        for(int key=1;key<=10;key++){
            String currentValue = values.get(key - 1);
            int bucket = Hashing.consistentHash(Hashing.md5().hashString(currentValue), caches.size());
            CacheServiceInterface cache = caches.get(bucket);
            String value = cache.get(key);
            System.out.println("get("+key+") => "+value);
        }



        System.out.println("Existing Cache Client...");
    }

}

/*
* 1 => a
2 => b
3 => c
4 => d
5 => e
6 => f
7 => g
8 => h
9 => i
10 => j
* */
