/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import org.junit.*;

import com.github.commons.mq.domain.ConsumerHandler;
import com.github.commons.mq.domain.MqConsumer;
import com.github.commons.mq.domain.MqMessage;
import com.github.commons.mq.domain.MqProducer;
import com.github.commons.mq.rabbitmq.RabbitMqClient;
import com.github.commons.utils.format.JsonUtils;

import java.util.concurrent.Executors;

/**
 * RabbitMqClientTest.java
 *
 * @author zhouxiaofeng 4/24/15
 */
@Ignore
public class RabbitMqClientTest {

    private RabbitMqClient client;
    private MqProducer     producer;
    private MqConsumer     consumer;

    @Before
    public void before() throws Exception {
        client = new RabbitMqClient();
        client.setHost("127.0.0.1");

        // client.setHost("10.240.176.106");

        client.setPort(5672);

        client.setUserName("admin");
        client.setPassword("nRcyVu7MB9");

        client.init();

        producer = client.createMqProducer("huey.rabbit.resultqueue");
        consumer = client.createMqConsumer("huey.rabbit.resultqueue", null);
    }

    @After
    public void after() throws Exception {

        if (consumer != null) {
            consumer.shutdown();
        }

        if (producer != null) {
            producer.shutdown();
        }

        if (client != null) {
            client.shutdown();
        }
    }

    @Test
    public void testProduct() {

        CrawlerReqCmd crCmd = new CrawlerReqCmd();
        crCmd.setCmd("queuecmd_crawl");
        crCmd.setId("test--4---id");

        GongjijinCrawlerRequest request = new GongjijinCrawlerRequest();

        request.setChannel("channel");
        request.setCookies("cookie");
        request.setData("data");
        request.setPassword("password");
        request.setRetryTimes(3);
        request.setText("txt");
        request.setType("type");
        request.setUid("uid");
        request.setUserName("username");

        crCmd.setRequest(request);

        Assert.assertTrue(producer.sendMessage(JsonUtils.toJson(crCmd).getBytes()));

    }

    @Test
    public void testConsumer() throws InterruptedException {

        consumer.consumeMessage(new ConsumerHandler() {

            @Override
            public boolean handler(MqMessage msg) {

                System.out.println(new String(msg.getMessage()));

                return true;
            }
        }, Executors.newFixedThreadPool(1));


        Thread.sleep(10000);

    }
}

// ["40e76bf3-640b-4dbd-8eaa-087a6cd24d55", "queuecmd_crawl", null, 0, 0,
// [["{\"userName\": \"maojie\", \"cookies\": null, \"retry\": 0, \"text\": null, \"retry_times\": 3, \"data\": null, \"time\": 1430098627.231, \"password\": \"aaa\", \"type\": \"HZGjjSpider\", \"id\": \"ea3422f0-ec7d-11e4-b17c-d43d7ec81402\", \"channel\": null}"],
// {}]]

class CrawlerReqCmd {

    private String         cmd;
    private String         id;
    private CrawlerRequest request;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CrawlerRequest getRequest() {
        return request;
    }

    public void setRequest(CrawlerRequest request) {
        this.request = request;
    }
}

interface CrawlerRequest {

}

class GongjijinCrawlerRequest implements CrawlerRequest {

    private String uid;
    private String type;
    private String userName;
    private String password;
    private String text;
    private String channel;
    private String data;
    private int    retryTimes;
    private String cookies;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
