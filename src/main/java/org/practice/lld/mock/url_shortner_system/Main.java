package org.practice.lld.mock.url_shortner_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] args) {
        UrlShortener urlShortener = new UrlShortener();

        List<String> list = Collections.synchronizedList(new ArrayList<>());

        for (int i=0; i<5000; i++){
            new Thread(() -> {
                String temp = urlShortener.getShortenedUrl("https://google."+Thread.currentThread().toString()+".com");

                list.add(temp);
                System.out.println(temp);

                System.out.println(urlShortener.getActualUrl(temp));
            }).start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(list.size());
    }
}

class UrlShortener {

    public static final String BASE_62_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static AtomicLong counter  = new AtomicLong(0);

    ConcurrentHashMap<String, String> urlMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, String> reverseUrlMap = new ConcurrentHashMap<>();


    String getShortenedUrl(String longUrl){

        reverseUrlMap.computeIfAbsent(longUrl, url ->{
            String encodedUrl = ShortnerUtil.encode62(counter.longValue());

            counter.getAndIncrement();
            urlMap.put(encodedUrl, longUrl);
            return encodedUrl;
        });

        return reverseUrlMap.get(longUrl);
    }

    String getActualUrl(String shortUrl){
        return urlMap.getOrDefault(shortUrl, null);
    }
}

class ShortnerUtil{

    private ShortnerUtil() {
    }

    public static String encode62(long counter){
        StringBuilder encodedString = new StringBuilder();
        while(counter > 0){
            encodedString.append(UrlShortener.BASE_62_CHAR.charAt((int)(counter % 62)));
            counter = counter/62;
        }
        return encodedString.toString();
    }
}
/**
 * DB design:
 *
 * Url{
 *     BigInteger id;
 *     String longUrl;
 *     String shortUrl; (Base62 encoding of id)
 * }
 *
 * */


