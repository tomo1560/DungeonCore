package net.l_bulb.dungeoncore.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import net.l_bulb.dungeoncore.item.customItem.attackitem.AbstractAttackItem;
import net.l_bulb.dungeoncore.item.slot.AbstractSlot;
import net.l_bulb.dungeoncore.item.slot.SlotInterface;
import net.l_bulb.dungeoncore.item.slot.slot.EmptySlot;
import net.l_bulb.dungeoncore.item.system.strength.StrengthOperator;
import net.l_bulb.dungeoncore.player.ItemType;
import net.l_bulb.dungeoncore.util.ItemStackUtil;

public class CustomWeaponItemStack2 {
  /**
   * インスタンスを取得、通常はクラス内からしか呼ばれない
   *
   * @param item
   * @param itemInterface
   */
  private CustomWeaponItemStack2(ItemStack item, AbstractAttackItem itemInterface) {
    this.item = item;
    this.itemInterface = itemInterface;
  }

  ItemStack item;
  AbstractAttackItem itemInterface;

  static HashMap<ItemStack, CustomWeaponItemStack2> cache = new HashMap<>();

  /**
   * ItemStackから武器情報を取得する
   *
   * @param item
   * @return
   */
  public static CustomWeaponItemStack2 getInstance(ItemStack item) {
    ItemInterface customItem = ItemManager.getCustomItem(item);
    if (customItem == null) { return null; }
    // 武器でないならnullを返す
    if (customItem instanceof AbstractAttackItem) {
      CustomWeaponItemStack2 attackItemStack = getCache(item);
      return attackItemStack;
    }
    return null;
  }

  /**
   * キャッシュからインスタンスを取得する
   *
   * @param item
   * @return
   */
  private static CustomWeaponItemStack2 getCache(ItemStack item) {
    // キャッシュがあるならそれを取得
    if (cache.containsKey(item)) { return cache.get(item); }
    return new CustomWeaponItemStack2(item, (AbstractAttackItem) ItemManager.getCustomItem(item));
  }

  /**
   * ItemStackを取得
   *
   * @return
   */
  public ItemStack getItem() {
    return item;
  }

  /**
   * CustomItemを取得
   *
   * @return
   */
  public AbstractAttackItem getItemInterface() {
    return itemInterface;
  }

  /**
   * 武器のアイテムタイプを取得
   *
   * @return
   */
  public ItemType getItemType() {
    return itemInterface.getAttackType();
  }

  ArrayList<SlotInterface> slotList = null;

  /**
   * 使用している魔法石を取得
   *
   * @return
   */
  public ArrayList<SlotInterface> getUseSlot() {
    initSlot();
    return slotList;
  }

  // 魔法石情報を初期化しているかどうか
  boolean isInitSlot = false;

  /**
   * 魔法石情報を取得
   */
  protected void initSlot() {
    // すでに初期化しているなら無視する
    if (isInitSlot) { return; }

    slotList = new ArrayList<>();
    for (String string : ItemStackUtil.getLore(item)) {
      SlotInterface slotByLore = SlotManager.getSlotByLore(string);
      if (slotByLore != null) {
        slotList.add(slotByLore);
      }
    }
    isInitSlot = true;
  }

  /**
   * 魔法石を取り除く
   *
   * @param slot
   * @return
   */
  public boolean removeSlot(SlotInterface slot) {
    initSlot();
    return slotList.remove(slotList.indexOf(slot)) != null;
  }

  /**
   * 空のスロットを追加する
   *
   * @return
   */
  public boolean addEmptySlot() {
    return addSlot(new EmptySlot());
  }

  /**
   * 魔法石を追加する
   *
   * @param slot
   * @return
   */
  public boolean addSlot(SlotInterface slot) {
    initSlot();

    if (slotList.size() < 5) {
      slotList.add(slot);
      return true;
    }
    return false;
  }

  /**
   * 指定されたスロットが存在していたらTRUE
   *
   * @param slot
   * @return
   */
  public boolean hasSlot(AbstractSlot slot) {
    initSlot();
    return slotList.contains(slot);
  }

  int strengthLevel = -1;

  /**
   * 強化レベルをセットする
   *
   * @param level
   */
  public void setStrengthLevel(int level) {
    this.strengthLevel = level;
  }

  /**
   * 武器情報を取得する
   */
  public void updateItem() {
    // 強化レベルをセットしたなら更新する
    if (strengthLevel != -1) {
      StrengthOperator.updateLore(getItem(), strengthLevel);
    }

    // Slotの初期化をする
    initSlot();

    List<String> lore = ItemStackUtil.getLore(item);
    Iterator<String> iterator = lore.iterator();

    boolean slotFlg = false;
    while (iterator.hasNext()) {
      String next = iterator.next();
      if (slotFlg) {
        iterator.remove();
        // 改行が存在したらSLOT終了とする
        if (next.trim().equals("")) {
          break;
        }
      }

      if (next.contains("[SLOT]")) {
        iterator.remove();
        slotFlg = true;
      }
    }

    lore.add(ChatColor.GREEN + "[SLOT]");
    for (SlotInterface abstractSlot : slotList) {
      String[] slotLore = SlotManager.getLore(abstractSlot);
      for (String line : slotLore) {
        lore.add(line);
      }
    }
    lore.add("");
    ItemStackUtil.setLore(item, lore);
  }

}
