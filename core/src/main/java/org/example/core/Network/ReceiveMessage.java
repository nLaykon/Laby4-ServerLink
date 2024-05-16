package org.example.core.Network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.labymod.api.client.network.server.ServerData;
import org.example.core.ServerLink;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ReceiveMessage {

  private final ServerLink addon;

  public ReceiveMessage(ServerLink addon) {
    this.addon = addon;
  }

  public void startServer() {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
      server.createContext("/post", new PostHandler());
      server.start();
      System.out.println("Server started on port 8000");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  class PostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      if ("POST".equals(exchange.getRequestMethod())) {
        ServerData serverData = addon.labyAPI().serverController().getCurrentServerData();
        if (serverData != null) {
          InputStream requestBody = exchange.getRequestBody();
          byte[] buffer = new byte[1024];
          int bytesRead;
          StringBuilder requestContent = new StringBuilder();
          while ((bytesRead = requestBody.read(buffer)) != -1) {
            requestContent.append(new String(buffer, 0, bytesRead));
          }
          System.out.println("Received POST content: " + requestContent.toString());

          JsonObject jsonData = JsonParser.parseString(requestContent.toString()).getAsJsonObject();

          String value = jsonData.get("key").getAsString();
          System.out.println("Value: " + value);

          addon.labyAPI().minecraft().chatExecutor().chat(value);

          String response = "Received POST request successfully";
          exchange.sendResponseHeaders(200, response.getBytes().length);
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        } else {
          String response = "Not connected to a server";
          exchange.sendResponseHeaders(400, response.getBytes().length);
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }
      } else {
        exchange.sendResponseHeaders(405, -1);
      }
    }
  }
}
