package net.l_bulb.dungeoncore.common.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerQuitDungeonGameEvent extends PlayerEvent {
  private static final HandlerList handlers = new HandlerList();

  public PlayerQuitDungeonGameEvent(Player who) {
    super(who);
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
