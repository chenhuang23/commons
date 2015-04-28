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

        client.setAccessKey("e480f548b8f5424aa1fc1e3a26db8ed9");
        client.setAccessSecret("e4ee7057cb78435f882b2ddf10c3ef91");
        client.setHost("10.120.153.226");
        client.setPort(5672);
        client.setProductId("4ee8febca9494d3b87a0dff244335bd1");

        client.init();

        producer = client.createMqProducer("wyb_loan_dev");
        consumer = client.createMqConsumer("wyb_loan_dev", null);
    }

    @After
    public void after() throws Exception {

        consumer.shutdown();
        producer.shutdown();
        client.shutdown();
    }

    @Test
    public void testProduct() {

        Assert.assertTrue(producer.sendMessage("Test".getBytes()));

    }

}
