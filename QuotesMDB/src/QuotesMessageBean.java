import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.*;
import java.util.Random;

@MessageDriven(name = "MessageEJB", mappedName = "jms/QuoteFinder")
public class QuotesMessageBean implements MessageListener {
    private final Random random;
    private String companyName;

    @Resource
    private MessageDrivenContext mdc;

    @Resource(name = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    private JMSContext context;

    public QuotesMessageBean() {
        random = new Random();
        companyName = "London Insurance";
    }

    @PostConstruct
    public void init() {
        context = connectionFactory.createContext();
    }

    @PreDestroy
    public void cleanUp() {
        context.close();
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            Destination replyTo = textMessage.getJMSReplyTo();
            ObjectMapper mapper = new ObjectMapper();

            String body = textMessage.getText();
            // do something with the body

            QuoteDetails quoteDetails = new QuoteDetails(companyName, random.nextInt(400));
            String quoteDetailsJson = mapper.writeValueAsString(quoteDetails);

            JMSProducer producer = context.createProducer();
            producer.send(replyTo, quoteDetailsJson);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

