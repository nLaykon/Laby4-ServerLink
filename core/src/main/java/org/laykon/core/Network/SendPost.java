package org.laykon.core.Network;

import org.laykon.core.ServerLink;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendPost {

  public void sendContent(String content) {
    Runnable post = new Post(content);
    Thread thread = new Thread(post);
    thread.start();
  }

  private class Post implements Runnable {
    private String content;

    Post(String c) {
      content = c;
    }

    @Override
    public void run() {
      try {
        sendRequest(content);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }


  public static void sendRequest(String content) throws IOException {
    String postData = "{\"testIg\": \"" + content + "\"}";
    URL myUrl = new URL("http://192.168.0.80:8000/post");

    HttpURLConnection serverConnect = (HttpURLConnection) myUrl.openConnection();
    serverConnect.setRequestMethod("POST");
    serverConnect.setRequestProperty("Content-Type", "application/json");

    serverConnect.setDoOutput(true);

    try (OutputStream os = serverConnect.getOutputStream()) {
      byte[] input = postData.getBytes("utf-8");
      os.write(input, 0, input.length);
    }

    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(serverConnect.getInputStream(), "utf-8"))) {
      StringBuilder response = new StringBuilder();
      String responseLine;
      while ((responseLine = br.readLine()) != null) {
        response.append(responseLine.trim());
      }
      System.out.println("Response: " + response.toString());
    }

    serverConnect.disconnect();
  }
}
