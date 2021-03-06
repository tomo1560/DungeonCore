package net.l_bulb.dungeoncore.item.setItem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.l_bulb.dungeoncore.api.player.TheLowPlayer;
import net.l_bulb.dungeoncore.api.player.TheLowPlayerManager;
import net.l_bulb.dungeoncore.player.ability.impl.SetItemAbility;

public abstract class AbstractAbilitySetItem extends AbstractCommonSetItem {
  protected SetItemAbility emptySetitem = createEmptyAbility();

  @Override
  public void startJob(Player p, ItemStack... item) {
    // Abilityを生成
    SetItemAbility setItemAbility = createEmptyAbility();
    // 効果をつける
    addAbility(setItemAbility, p, item);

    // Playerがロードされているなら効果を追加する
    TheLowPlayer theLowPlayer = TheLowPlayerManager.getTheLowPlayer(p);
    if (theLowPlayer != null) {
      theLowPlayer.addAbility(setItemAbility);
    }
  }

  /**
   * 効果のないAbilityインスタンスを生成する
   *
   * @return
   */
  protected SetItemAbility createEmptyAbility() {
    SetItemAbility setItemAbility = new SetItemAbility(getName() + "_setitem");
    return setItemAbility;
  }

  @Override
  public void endJob(Player p) {
    // Playerがロードされているなら効果を消す
    TheLowPlayer theLowPlayer = TheLowPlayerManager.getTheLowPlayer(p);
    if (theLowPlayer != null) {
      theLowPlayer.removeAbility(emptySetitem);
    }
  }

  /**
   * この装備をつけたときのAbilityを登録する
   *
   * @return
   */
  abstract protected void addAbility(SetItemAbility emptyAbility, Player p, ItemStack... item);
}
