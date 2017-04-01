package ee.tuleva.onboarding.kpr;

import com.ibm.jms.JMSBytesMessage;
import ee.tuleva.onboarding.config.SocksConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;

/**
 * Disable java assertions on this test (remove "-ea" from idea run conf), MQ Factory fails with assertions.
 */
@ContextConfiguration(classes = {SocksConfiguration.class, MhubConnectivityTest.class, MhubConfiguration.class}, initializers = YamlFileApplicationContextInitializer.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MhubConnectivityTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void sendAndReceiveMessageToMhub() throws Exception {
        jmsTemplate.send(new MyMessageCreator("Testikas"));
        // sleeping for some return messages
        Thread.sleep(5000);
    }

    @Bean
    public MessageListener createMyListener() {
        return new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("Message received:");
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        throw new RuntimeException();
                    }
                } else if (message instanceof JMSBytesMessage) {
                    JMSBytesMessage bytesMessage = (JMSBytesMessage) message;
                    try {
                        int length = (int)bytesMessage.getBodyLength();
                        byte[] msg = new byte[length];
                        bytesMessage.readBytes(msg, length);
                        System.out.println(new String(msg));
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println(message.getClass());
                }
            }
        };
    }


    public class MyMessageCreator implements MessageCreator {
        private String message;

        private MyMessageCreator(String message) {
            this.message = message;
        }

        @Override
        public Message createMessage(Session session) throws JMSException {
            return session.createTextMessage(message);
        }
    }

}