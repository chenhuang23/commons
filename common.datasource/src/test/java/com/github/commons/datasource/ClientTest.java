package com.github.commons.datasource;

import com.github.commons.datasource.seq.DBSeqGenerator;
import com.github.commons.utils.format.JsonUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

public class ClientTest {

    public static void main(String[] args) throws IOException, SQLException {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:bean.xml");
        DataSource hello = (DataSource) ctx.getBean("datasourceTest");
        System.out.println("0------->> " + hello.getConnection());

        DBSeqGenerator gen = new DBSeqGenerator();

        gen.setDataSource(hello);
        gen.setKey("applyNo");
        gen.setRetryTime(3);
        gen.setStep(2);
        gen.setTableName("seq_gen");

        // Integer.parseInt(String.valueOf(properties.get("druid.maxActive")))

        for (int i = 0; i < 100; i++) {
            System.out.println(i + " - " + gen.generate());
        }
    }
}
