package org.laykon.core;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import org.laykon.core.Network.ReceiveMessage;
import org.laykon.core.listener.MessageListener;

@AddonMain
public class ServerLink extends LabyAddon<ServerLinkConfig> {

  private ReceiveMessage messageReceiver;

  @Override
  protected void enable() {
    this.registerSettingCategory();
    messageReceiver = new ReceiveMessage(this);
    messageReceiver.startServer();
    this.registerListener(new MessageListener(this));
  }
  @Override
  protected Class<ServerLinkConfig> configurationClass() {
    return ServerLinkConfig.class;
  }
}
