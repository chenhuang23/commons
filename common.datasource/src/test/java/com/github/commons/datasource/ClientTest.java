package com.github.commons.datasource;

import com.github.commons.utils.format.JsonUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class ClientTest {

    public static void main(String[] args) throws IOException {


        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:bean.xml");
        DataSource hello = (DataSource) ctx.getBean("datasourceTest");
        System.out.println("0------->> "+hello);


    }
}
