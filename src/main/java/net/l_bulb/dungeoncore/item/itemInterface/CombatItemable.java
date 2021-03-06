package net.l_bulb.dungeoncore.item.itemInterface;

import org.bukkit.event.player.PlayerDropItemEvent;

import net.l_bulb.dungeoncore.common.event.player.PlayerCombatEntityEvent;

public interface CombatItemable extends RightClickItemable, EquipItemable {
  /**
   * Entityを攻撃した時
   *
   * @param e
   */
  void onCombatEntity(PlayerCombatEntityEvent e);

  /**
   * アイテムを捨てる時
   *
   * @param e
   */
  void onPlayerDropItemEvent(PlayerDropItemEvent e);

  /**
   * デフォルトのスロットの数
   *
   * @return
   */
  int getDefaultSlotCount();

  /**
   * デフォルトのスロットの数
   *
   * @return
   */
  int getMaxSlotCount();
}
