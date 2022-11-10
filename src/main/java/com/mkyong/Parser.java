//package com.mkyong;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.net.URL;
//import java.net.URLConnection;
//
//public class Parser {
//    public static void main(String[] args) throws IOException {
//        URL url;
//        URLConnection uc;
//        StringBuilder parsedContentFromUrl = new StringBuilder();
//        String urlString="https://urfu.ru/ru/about/personal-pages/personal/person/anna.kitchak/";
//    System.out.println("Getting content for URl : " + urlString);
//        url = new URL(urlString);
//        uc = url.openConnection();
//    uc.connect();
//        uc = url.openConnection();
//        uc.addRequestProperty("User-Agent",
//                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
//    uc.getInputStream();
//        BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
//        int ch;
//    while ((ch = in.read()) != -1) {
//            parsedContentFromUrl.append((char) ch);
//        }
//    System.out.println(parsedContentFromUrl);
//
//    }
//}

package com.mkyong;

import org.jsoup.Jsoup;

public class Parser {
    public static void main (String[] args){
        try{
            var site = Jsoup.connect("https://urfu.ru/ru/about/personal-pages/").get();
            var contact = site.select("a");
            var name = site.select("span.text2");
            for (var element: contact){
                System.out.println(element.attr("href"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
















//
//try{
//        var site = Jsoup.connect("https://urfu.ru/ru/about/personal-pages/personal/person/r.r.abdullin/").get();
//        var contact = site.select("div.contacts-col");
//        System.out.println(contact.text());
//        } catch (Exception e){
//        e.printStackTrace();
//        }