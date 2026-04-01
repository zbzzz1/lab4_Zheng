import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PizzaSender {

    private static final String QUEUE_NAME = "pizzaQueue";

    public static void main(String[] args) throws Exception {
        Pizza myPizza = new Pizza(1, "Large", "Pepperoni, Cheese", 13.99);

        ObjectMapper pizzaToJson = new ObjectMapper();
        String pizzaJson = pizzaToJson.writeValueAsString(myPizza);

        String flatFileMessage = myPizza.getId() + "," + myPizza.getSize() + "," + myPizza.getToppings() + "," + myPizza.getPrice();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, pizzaJson.getBytes());
            channel.basicPublish("", QUEUE_NAME, null, flatFileMessage.getBytes());

            System.out.println("Sent JSON: " + pizzaJson);
            System.out.println("Sent Flat File: " + flatFileMessage);
        }
    }
}
