package org.example.core;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import org.example.core.Network.ReceiveMessage;
import org.example.core.listener.MessageListener;

@AddonMain
public class ServerLink extends LabyAddon<ServerLinkConfig> {

  private ReceiveMessage messageReceiver;

  @Override
  protected void enable() {
    messageReceiver = new ReceiveMessage(this);
    messageReceiver.startServer();
    this.registerListener(new MessageListener(this));
  }
  @Override
  protected Class<ServerLinkConfig> configurationClass() {
    return ServerLinkConfig.class;
  }
}
