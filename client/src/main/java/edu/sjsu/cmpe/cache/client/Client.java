package edu.sjsu.cmpe.cache.client;


import java.util.*;

import com.google.common.hash.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache3000 = new DistributedCacheService("http://localhost:3000");
        CacheServiceInterface cache3001 = new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface cache3002 = new DistributedCacheService("http://localhost:3002");



        Integer replicationFactor = 10;
        List<CacheServiceInterface> caches = Arrays.asList(cache3000, cache3001, cache3002);
        List<String> values = Arrays.asList("a", "b","c","d","e","f","g","h","i","j");

        ConsistentHash<CacheServiceInterface> consistentHash = new ConsistentHash<CacheServiceInterface>(Hashing.md5(), replicationFactor, caches);

        for (int key=1;key<=10;key++){
            CacheServiceInterface bucket = consistentHash.get(key);
            bucket.put(key, values.get(key-1) );
            System.out.println("put(" + key + " => " + values.get(key-1) + ")");
        }

        System.out.println("\nDistribution of Values is: \n" +
                "Server_A => http://localhost:3000/cache/  =>\n"+cache3000.getAllValues()+
                "\nServer_B => http://localhost:3001/cache/  =>\n"+cache3001.getAllValues()+
                "\nServer_C => http://localhost:3002/cache/  =>\n"+cache3002.getAllValues()
        );

        System.out.println("\nFetching values now from Cache .... \n");

        for(int key=1;key<=10;key++){
            CacheServiceInterface bucket = consistentHash.get(key);
            String value = bucket.get(key);
            System.out.println("get("+key+") => "+value);
        }

        System.out.println("Existing Cache Client...");
    }

}
