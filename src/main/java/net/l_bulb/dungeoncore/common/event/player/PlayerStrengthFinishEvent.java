package net.l_bulb.dungeoncore.common.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Playerが強化を完了した瞬間
 *
 * @author KENSUKE
 *
 */
public class PlayerStrengthFinishEvent extends PlayerEvent {
  private ItemStack item;
  private int level;
  private boolean isSuccess;

  public PlayerStrengthFinishEvent(Player who, int level, ItemStack item, boolean isSuccess) {
    super(who);
    this.level = level;
    this.item = item;
    this.isSuccess = isSuccess;
  }

  public ItemStack getItem() {
    return item;
  }

  public int getLevel() {
    return level;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  private static final HandlerList handlers = new HandlerList();

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
