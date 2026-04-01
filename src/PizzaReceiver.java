import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

public class PizzaReceiver {

    private static final String QUEUE_NAME = "pizzaQueue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Waiting for pizza messages...");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println("\nReceived message: " + message);

            if (message.startsWith("{")) {
                ObjectMapper jsonToPizza = new ObjectMapper();
                Pizza receivedPizza = jsonToPizza.readValue(message, Pizza.class);
                System.out.println("ID: " + receivedPizza.getId());
                System.out.println("Size: " + receivedPizza.getSize());
                System.out.println("Toppings: " + receivedPizza.getToppings());
                System.out.println("Price: $" + receivedPizza.getPrice());
            } else {
                String[] parts = message.split("\\|");
                System.out.println("ID: " + parts[0]);
                System.out.println("Size: " + parts[1]);
                System.out.println("Toppings: " + parts[2]);
                System.out.println("Price: $" + parts[3]);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
    }
}
