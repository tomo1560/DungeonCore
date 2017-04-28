package net.l_bulb.dungeoncore.common.event.player;

import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import net.l_bulb.dungeoncore.api.player.TheLowPlayer;
import net.l_bulb.dungeoncore.item.itemInterface.CraftItemable;
import net.l_bulb.dungeoncore.item.system.craft.TheLowCraftRecipeInterface;

public class PlayerCraftCustomItemEvent extends TheLowPlayerEvent {

  private CraftItemable itemInterface;
  private ItemStack craftedItem;

  public PlayerCraftCustomItemEvent(TheLowPlayer player, CraftItemable craftItemable, ItemStack craftedItem) {
    super(player);
    this.itemInterface = craftItemable;
    this.craftedItem = craftedItem;
  }

  private static final HandlerList handlers = new HandlerList();

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  /**
   * クラフトしたItemInterface
   * 
   * @return
   */
  public CraftItemable getItemInterface() {
    return itemInterface;
  }

  /**
   * クラフトに使用したレシピを取得
   * 
   * @return
   */
  public TheLowCraftRecipeInterface getCraftRecipe() {
    return itemInterface.getCraftRecipe();
  }

  /**
   * クラフトして実際に手に入れたアイテム
   * 
   * @return
   */
  public ItemStack getCraftedItem() {
    return craftedItem;
  }
}