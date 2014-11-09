package edu.sjsu.cmpe.cache.client;


import java.util.Arrays;
import java.util.List;
import com.google.common.hash.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        //ConsistentHash consistentHash = new ConsistentHash<String>(Hashing.md5(),4);
        CacheServiceInterface cache = new DistributedCacheService("http://localhost:3000");
        CacheServiceInterface cache3001 = new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface cache3002 = new DistributedCacheService("http://localhost:3002");
        List<CacheServiceInterface> servers = Arrays.asList(cache, cache3001, cache3002);
        //Hashing.consistentHash(Hashing.md5().hashString())



        //servers.get(consistentHash.)



        cache.put(1, "foo");
        System.out.println("put(1 => foo)");

        String value = cache.get(1);
        System.out.println("get(1) => " + value);

        cache3001.put(2,"bob");
        System.out.println("get(2) from 3002 => "+cache3001.get(2));

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
