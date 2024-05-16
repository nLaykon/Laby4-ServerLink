package org.example.core.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import org.example.core.Network.SendPost;
import org.example.core.ServerLink;
import java.io.IOException;


public class MessageListener {
  private final ServerLink addon;
  public MessageListener(ServerLink addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onChatReceive(ChatReceiveEvent it) throws IOException {
    System.out.println(it.chatMessage().getPlainText() + ": Message Debug!!!");
    try {
      SendPost.sendRequest(it.chatMessage().getPlainText());
    }catch (Exception e){
      System.out.println(e);
    }
  }



}
