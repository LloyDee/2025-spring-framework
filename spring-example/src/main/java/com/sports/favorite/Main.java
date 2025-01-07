package com.sports.favorite;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationFavoriteSports.xml");

        Overseer basket = (Overseer) context.getBean("myBasketball");
        System.out.println(basket.getFavoriteSport());

        Overseer volley = (Overseer) context.getBean("myVolleyball");
        System.out.println(volley.getFavoriteSport());

        Overseer foot = (Overseer) context.getBean("myFootball");
        System.out.println(foot.getFavoriteSport());
    }
}
