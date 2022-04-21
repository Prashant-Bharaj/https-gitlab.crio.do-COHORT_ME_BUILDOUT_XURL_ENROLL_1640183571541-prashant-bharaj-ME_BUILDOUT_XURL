package com.crio.shorturl;

import java.util.*;



public class XUrlImpl implements XUrl {
    HashMap<String, String> shortToLong = new HashMap<>();
    HashMap<String, String> longToShort = new HashMap<>();
    HashMap<String, Integer> hit = new HashMap<>();
    // a-z 0-9
    // 
    static String s = "abcdefghijklmnopqrstuvwxyz0123456789";
    static int number=0;
    // Map number to 36 base
    List<Integer> decimalTo36(int number){
        List<Integer> ans = new ArrayList<>();
        while(number > 0){
            ans.add(0,number % 36);
            number /= 36; 
        }
        return ans;
    }
    String makeUrl(int number){
        String url = "http://short.url/";
        List<Integer> reqIndex = decimalTo36(number);
        for(int i = 1; i <= 9-reqIndex.size(); i++){
            url += s.charAt(0);
        }
        for(int i = 0; i < decimalTo36(number).size(); i++){
            url += s.charAt(reqIndex.get(i));
        }
        return url;
    }
    @Override
    public String registerNewUrl(String longUrl) {
        // generate the shortUrl
        // for repeated shortUrls assign next possible url
        hit.put(longUrl, hit.getOrDefault(longUrl, 0)+1);
        if(longToShort.containsKey(longUrl)){
            return longToShort.get(longUrl);
        }
        String url;
        while(shortToLong.containsKey(url = makeUrl(number))){
            number++;
        }
        shortToLong.put(url, longUrl);
        longToShort.put(longUrl, url);
        return url;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(shortToLong.containsKey(shortUrl)){
            return null;
        }
        shortToLong.put(shortUrl, longUrl);
        longToShort.put(longUrl, shortUrl);
        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        // TODO Auto-generated method stub
        return shortToLong.get(shortUrl);
    }

    @Override
    public Integer getHitCount(String longUrl) {
        // TODO Auto-generated method stub
        return hit.getOrDefault(longUrl,0);
    }

    @Override
    public String delete(String longUrl) {
        // TODO Auto-generated method stub
        String shorturl = longToShort.remove(longUrl);
        shortToLong.remove(shorturl);
        return shorturl;
    }

}