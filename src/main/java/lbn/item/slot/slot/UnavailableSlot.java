package lbn.item.slot.slot;

import org.bukkit.ChatColor;

import lbn.item.slot.SlotInterface;
import lbn.item.slot.SlotLevel;
import lbn.item.slot.SlotType;

public class UnavailableSlot implements SlotInterface {

  @Override
  public String getSlotName() {
    return "使用不可";
  }

  @Override
  public String getSlotDetail() {
    return "このスロットは利用できません";
  }

  @Override
  public String getId() {
    return "unavaliable";
  }

  @Override
  public ChatColor getNameColor() {
    return ChatColor.GRAY;
  }

  @Override
  public SlotType getSlotType() {
    return SlotType.UNAVAILABLE;
  }

  @Override
  public SlotLevel getLevel() {
    return SlotLevel.UNUSE;
  }

  @Override
  public boolean isSame(SlotInterface slot) {
    if (slot != null) { return getId().equals(slot.getId()); }
    return false;
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj != null && obj instanceof SlotInterface) {
      return getId().equals(((SlotInterface) obj).getId());
    } else {
      return false;
    }
  }
}