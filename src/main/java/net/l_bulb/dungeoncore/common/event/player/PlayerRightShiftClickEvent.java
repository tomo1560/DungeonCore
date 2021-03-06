package net.l_bulb.dungeoncore.common.event.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import net.l_bulb.dungeoncore.item.CustomWeaponItemStack2;

import lombok.Getter;

@Getter
public class PlayerRightShiftClickEvent extends PlayerEvent {
  private static final HandlerList handlers = new HandlerList();

  CustomWeaponItemStack2 attackItem;

  public PlayerRightShiftClickEvent(Player who, ItemStack item) {
    super(who);
    this.attackItem = CustomWeaponItemStack2.getInstance(item);
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public void callEvent() {
    if (attackItem != null) {
      Bukkit.getServer().getPluginManager().callEvent(this);
    }
  }
}