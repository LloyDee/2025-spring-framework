package com.loose.coupling;

import car.example.bean.MyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LooseCouplingExample {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationLooseCoupling.xml");
        UserManager userManagerWithDBProvider = (UserManager) context.getBean("userManagerWithUserDataProvider");
        System.out.println(userManagerWithDBProvider.getUserInfo());

        UserManager userManagerWithNewDataProvider = (UserManager) context.getBean("userManagerWithNewDataProvider");
        System.out.println(userManagerWithNewDataProvider.getUserInfo());

        UserManager userManagerWithWebDataProvider = (UserManager) context.getBean("userManagerWithWebDataProvider");
        System.out.println(userManagerWithWebDataProvider.getUserInfo());
    }
}
