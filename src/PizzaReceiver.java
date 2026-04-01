/**
 * Project: Lab4
 * Purpose Details: Receive Pizza JSON from RabbitMQ
 * Course: IST242
 * Author: Ziyan Zheng
 * Date Developed: 03/31/2026
 * Last Date Changed: 03/31/2026
 * Rev: 1.0
 */

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
        System.out.println("Waiting for pizza orders...");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String receivedPizzaJson = new String(delivery.getBody());
            System.out.println("Received: " + receivedPizzaJson);

            ObjectMapper jsonToPizza = new ObjectMapper();
            Pizza receivedPizza = jsonToPizza.readValue(receivedPizzaJson, Pizza.class);

            System.out.println("Pizza ID: " + receivedPizza.getId());
            System.out.println("Size: " + receivedPizza.getSize());
            System.out.println("Toppings: " + receivedPizza.getToppings());
            System.out.println("Price: " + receivedPizza.getPrice());
        };

        channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
    }
}
