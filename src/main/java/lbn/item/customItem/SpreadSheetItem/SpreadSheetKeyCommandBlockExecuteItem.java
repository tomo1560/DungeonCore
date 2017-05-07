package lbn.item.customItem.SpreadSheetItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lbn.item.customItem.key.CommandBlockExceteKey;
import lbn.util.ItemStackUtil;

public class SpreadSheetKeyCommandBlockExecuteItem extends CommandBlockExceteKey {

  public SpreadSheetKeyCommandBlockExecuteItem(String name, String id, int price,
      String command, String dungeon, Location dungeonLoc) {
    this.name = name;
    this.id = id;
    this.price = price;
    this.command = command;
    this.dungeon = dungeon;
    this.dungeonLoc = dungeonLoc;
    ItemStack itemStackByCommand = ItemStackUtil.getItemStackByCommand(command);
    m = itemStackByCommand.getType();
    lore = ItemStackUtil.getLore(itemStackByCommand);
  }

  String name;
  String id;
  int price;
  Material m;
  List<String> lore;
  String command;
  String dungeon;
  Location dungeonLoc;

  @Override
  public String getItemName() {
    return name;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  protected String getDungeonName() {
    return dungeon;
  }

  @Override
  protected ItemStack getItemStackBase() {
    ItemStack itemStack = ItemStackUtil.getItemStackByCommand(command);
    return itemStack;
  }

  @Override
  protected Material getMaterial() {
    return m;
  }

  @Override
  public String[] getDetail() {
    String[] detail = super.getDetail();
    if (detail == null) {
      return lore.toArray(new String[0]);
    } else {
      ArrayList<String> loreList = new ArrayList<>(lore);
      loreList.addAll(Arrays.asList(detail));
      return loreList.toArray(new String[0]);
    }
  }

  @Override
  protected Location getDungeonLocation() {
    return dungeonLoc;
  }
}
