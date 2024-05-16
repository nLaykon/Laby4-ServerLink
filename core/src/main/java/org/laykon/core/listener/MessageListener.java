package org.laykon.core.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import org.laykon.core.Network.SendPost;
import org.laykon.core.ServerLink;
import java.io.IOException;


public class MessageListener {

  private final ServerLink addon;

  public MessageListener(ServerLink addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onChatReceive(ChatReceiveEvent it) throws IOException {
    Thread thread = new Thread(() -> {
      try {
        SendPost.sendRequest(it.chatMessage().getPlainText());
      } catch (Exception e) {
        System.out.println(e);
      }
    });
    thread.start();
  }
}
