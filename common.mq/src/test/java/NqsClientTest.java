import com.github.commons.mq.domain.ConsumerHandler;
import com.github.commons.mq.domain.MqMessage;
import com.netease.cloud.nqs.client.ClientConfig;
import org.junit.*;

import com.github.commons.mq.domain.MqConsumer;
import com.github.commons.mq.domain.MqProducer;
import com.github.commons.mq.nqs.NqMqClient;

@Ignore
public class NqsClientTest {

    private NqMqClient client;
    private MqProducer producer;
    private MqConsumer consumer;

    @Before
    public void before() throws Exception {

        // client = new NqMqClient();
        // client.setAccessKey("e480f548b8f5424aa1fc1e3a26db8ed9");
        // client.setAccessSecret("e4ee7057cb78435f882b2ddf10c3ef91");
        // client.setProductId("4ee8febca9494d3b87a0dff244335bd1");
        // client.setHost("127.0.0.1");
        // client.setPort(5672);

        client = new NqMqClient();
        client.setHost("127.0.0.1");
        client.setPort(5672);
        client.setProductId("/"); // 使用 RabbitMQ 〳 vhost
        client.setAccessKey("admin");
        client.setAccessSecret("nRcyVu7MB9");
        client.setAuthMechanism(ClientConfig.AUTH_PLAIN);

        client.init();

        producer = client.createMqProducer("huey.rabbit.resultqueue");
        consumer = client.createMqConsumer("huey.rabbit.resultqueue", null);

        // producer = client.createMqProducer("loan_dev");
        // consumer = client.createMqConsumer("loan_dev", null);
    }

    @After
    public void after() throws Exception {

        consumer.shutdown();
        producer.shutdown();
        client.shutdown();
    }

    @Test
    public void testProduct() {

        Assert.assertTrue(producer.sendMessage("test=-----........".getBytes()));

    }

    @Test
    public void testConsumer() {

        consumer.consumeMessage(new ConsumerHandler() {

            @Override
            public boolean handler(MqMessage msg) {

                System.out.println(new String(msg.getMessage()));

                return true;
            }
        });

    }
}
