package com.greenfox.malachit.service;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveThumbnailTask {

  private final static String QUEUE_NAME = "thumbnails";

  public void receiveTask() throws Exception {
    Connection connection = initConnection();
    Channel channel = initChannel(connection);
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
        String message = new String(body, "UTF-8");
        try {
          System.out.println("**********   " + message + "   *************");
          try {
            System.out.println("###### RESIZING ######");
            Thread.sleep(10000);
          } catch (InterruptedException _ignored) {
            System.out.println("interrupted");
            Thread.currentThread().interrupt();
          }
        } finally {
          System.out.println(" [x] Done");
          channel.basicAck(envelope.getDeliveryTag(), false);
          try {
            closeMQ(connection, channel);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    };
    channel.basicConsume(QUEUE_NAME, false, consumer);
  }

  private Connection initConnection() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(System.getenv("RABBIT"));
    Connection connection = factory.newConnection();
    return connection;
  }

  private Channel initChannel(Connection connection) throws Exception {
    Channel channel = connection.createChannel();
    channel.basicQos(1);
    return channel;
  }

  private void closeMQ(Connection connection, Channel channel) throws Exception {
    channel.close();
    connection.close();
  }
}