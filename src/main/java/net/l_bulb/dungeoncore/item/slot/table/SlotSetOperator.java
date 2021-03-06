package net.l_bulb.dungeoncore.item.slot.table;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import net.l_bulb.dungeoncore.dungeoncore.LbnRuntimeException;
import net.l_bulb.dungeoncore.item.CustomWeaponItemStack2;
import net.l_bulb.dungeoncore.item.slot.SlotInterface;
import net.l_bulb.dungeoncore.item.slot.SlotType;
import net.l_bulb.dungeoncore.item.slot.slot.EmptySlot;
import net.l_bulb.dungeoncore.item.slot.slot.UnavailableSlot;

public class SlotSetOperator {
  protected SlotSetOperator(CustomWeaponItemStack2 attackItem,
      SlotInterface magicStone) {
    this.attackItem = attackItem;
    this.magicStone = magicStone;
  }

  CustomWeaponItemStack2 attackItem;
  SlotInterface magicStone;

  public CustomWeaponItemStack2 getAttackItem() {
    return attackItem;
  }

  public SlotInterface getMagicStone() {
    return magicStone;
  }

  /**
   * スロットに魔法石をセットする
   */
  public void setSlot() {
    SlotType slotType = magicStone.getSlotType();
    if (slotType == SlotType.NORMAL) {
      // 空のスロットを削除し、魔法石をセットする
      attackItem.removeSlot(new EmptySlot());
      attackItem.addSlot(magicStone);
    } else if (slotType == SlotType.ADD_EMPTY) {
      // 空のスロットを追加
      attackItem.addSlot(new EmptySlot());
    } else if (slotType == SlotType.REMOVE_UNAVAILABLE) {
      // 使用不可のスロットを削除する
      attackItem.removeSlot(new UnavailableSlot());
    }
    attackItem.updateItem();
  }

  /**
   * 魔法石をセットできるかセットする
   *
   * @return
   */
  public String check() {
    ArrayList<SlotInterface> useSlot = attackItem.getUseSlot();

    int emptyNum = 0;
    int unavailableNum = 0;
    for (SlotInterface slotInterface : useSlot) {
      // 空のスロットを調べる
      if (slotInterface.isSame(new EmptySlot())) {
        emptyNum++;
        // 使用不可のスロットを調べる
      } else if (slotInterface.isSame(new UnavailableSlot())) {
        unavailableNum++;
      }
    }

    SlotType type = magicStone.getSlotType();

    if (type == SlotType.NORMAL) {
      // 空のスロットがないなら何もしない
      if (emptyNum <= 0) { return "空きスロットが存在しません。"; }
      // 同じ魔法石は付けれない
      if (useSlot.contains(magicStone)) { return "同じ魔法石はセットできません。"; }
    } else if (type == SlotType.ADD_EMPTY) {
      if (attackItem.getItemInterface().getMaxSlotCount() - useSlot.size() <= 0) { return "これ以上、このアイテムにスロットを追加できません。"; }
    } else if (type == SlotType.REMOVE_UNAVAILABLE) {
      if (unavailableNum <= 0) { return "使用不可のスロットが存在しません。"; }
    } else {
      new LbnRuntimeException("invalid magic stone").printStackTrace();
      return "その魔法石は使えません";
    }
    return null;
  }

  /**
   * 成功確率
   *
   * @return
   */
  public int getSuccessRate() {
    return magicStone.getLevel().getSucessPer();
  }

  /**
   * 装着失敗時の処理
   *
   * @param cursor
   */
  public void rollback(ItemStack cursor) {
    CustomWeaponItemStack2 instance = CustomWeaponItemStack2.getInstance(cursor);
    // nullの可能性がある
    if (instance == null) {
      new LbnRuntimeException("magic stone is null").printStackTrace();
      ;
      return;
    }

    if (magicStone.getSlotType() == SlotType.NORMAL) {
      instance.removeSlot(magicStone);
      instance.addSlot(new UnavailableSlot());
    } else if (magicStone.getSlotType() == SlotType.ADD_EMPTY) {
      instance.removeSlot(new EmptySlot());
      instance.addSlot(new UnavailableSlot());
    } else if (magicStone.getSlotType() == SlotType.REMOVE_UNAVAILABLE) {
      instance.addSlot(new UnavailableSlot());
    }
    instance.updateItem();
  }

  /**
   * 成功時のコメント
   *
   * @return
   */
  public String getScuessComment() {
    return magicStone.getSlotType().getSuccessComment();
  }

  /**
   * 失敗時のコメント
   *
   * @return
   */
  public String getFailureComment() {
    return magicStone.getSlotType().getFailureComment();
  }

}
