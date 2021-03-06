package net.l_bulb.dungeoncore.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.l_bulb.dungeoncore.dungeoncore.SpletSheet.AbstractComplexSheetRunable;
import net.l_bulb.dungeoncore.dungeoncore.SpletSheet.SpletSheetExecutor;
import net.l_bulb.dungeoncore.dungeoncore.SpletSheet.VillagerSheetRunnable;
import net.l_bulb.dungeoncore.npc.villagerNpc.VillagerNpc;
import net.l_bulb.dungeoncore.npc.villagerNpc.VillagerNpcManager;

public class VillagerCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
    if (arg3.length == 0) { return false; }

    String targetID = null;
    if (arg3.length >= 2) {
      targetID = arg3[1];
    }

    if (arg3[0].equalsIgnoreCase("spawn")) {
      spawnVillager(arg0, targetID);
    } else if (arg3[0].equalsIgnoreCase("remove")) {
      removeVillager(arg0, targetID);
    } else if (arg3[0].equalsIgnoreCase("reload")) {
      reloadVillager(arg0, targetID);
    } else {
      arg0.sendMessage(arg3[0] + "は認められていません。[spawn, remove, reload, reset]のみ可能です。");
      return false;
    }
    return true;
  }

  public static void reloadAllVillager(CommandSender arg0, boolean init) {
    try {
      VillagerSheetRunnable villagerSheetRunnable = new VillagerSheetRunnable(arg0);
      villagerSheetRunnable.getData(null);
      SpletSheetExecutor.onExecute(villagerSheetRunnable);
    } catch (Exception e) {
      e.printStackTrace();
      arg0.sendMessage("エラーが発生しました。");
    }
  }

  protected void reloadVillager(CommandSender arg0, String targetName) {
    VillagerSheetRunnable villagerSheetRunnable = new VillagerSheetRunnable(arg0);
    if (villagerSheetRunnable.isTransaction()) {
      arg0.sendMessage("現在実行中です");
    }
    try {
      if (targetName == null || targetName.isEmpty()) {
        villagerSheetRunnable.getData(null);
      } else {
        villagerSheetRunnable.getData("name=" + targetName);
      }
      SpletSheetExecutor.onExecute(villagerSheetRunnable);
    } catch (Exception e) {
      e.printStackTrace();
      arg0.sendMessage("エラーが発生しました。");
    }
  }

  protected void removeVillager(CommandSender sender, String targetName) {
    ArrayList<VillagerNpc> npcList = new ArrayList<>();

    // 名前が指定されていない時はプレイヤーの周囲のNPCを調べる
    if (targetName == null) {
      List<Entity> nearbyEntities = ((Player) sender).getNearbyEntities(1, 1, 1);
      for (Entity entity : nearbyEntities) {
        // NPCならリストに追加
        VillagerNpc villagerNpc = VillagerNpcManager.getVillagerNpc(entity);
        if (villagerNpc != null) {
          npcList.add(villagerNpc);
        }
      }
    } else {
      List<Entity> nearbyEntities = ((Player) sender).getNearbyEntities(5, 2, 5);
      for (Entity entity : nearbyEntities) {
        VillagerNpc villagerNpc = VillagerNpcManager.getVillagerNpc(entity);
        // NPCでないなら無視する
        if (villagerNpc == null) {
          continue;
        }
        // 名前が違うなら無視する
        if (!villagerNpc.getName().equals(targetName)) {
          continue;
        }
        npcList.add(villagerNpc);
      }
    }

    // スプレットシートの内容を変更する
    VillagerSheetRunnable villagerSheetRunnable = new VillagerSheetRunnable(sender);
    for (VillagerNpc villagerNpc : npcList) {
      // locationを更新
      HashMap<String, Object> map = new HashMap<>();
      map.put("location", "");
      villagerSheetRunnable.updateData(map, "name=" + villagerNpc.getName());
    }

    try {
      SpletSheetExecutor.onExecute(villagerSheetRunnable);
    } catch (Exception e) {
      e.printStackTrace();
      sender.sendMessage("エラーが発生しました。");
    }

    // ワールドから削除する
    for (VillagerNpc villagerNpc : npcList) {
      villagerNpc.remove();
    }
  }

  protected void spawnVillager(CommandSender arg0, String targetID) {
    if (targetID == null) {
      arg0.sendMessage("spawn対象の村人が選択されていません。\n /villager spawn 村人名");
      return;
    }

    VillagerNpc villagerNpc = VillagerNpcManager.getVillagerNpcById(targetID);
    if (villagerNpc == null) {
      arg0.sendMessage(targetID + "という名前の村人が存在しません。");
      return;
    }

    Location location = ((Player) arg0).getLocation();
    location.setX(location.getBlockX() + 0.5);
    location.setY(location.getBlockY());
    location.setZ(location.getBlockZ() + 0.5);

    // NPCが存在してないとき
    VillagerNpcManager.spawnNpc(villagerNpc, location);

    // スプレットシートに書きこむ
    VillagerSheetRunnable villagerSheetRunnable = new VillagerSheetRunnable(arg0);
    HashMap<String, Object> map = new HashMap<>();
    map.put("location", AbstractComplexSheetRunable.getLocationString(location));
    villagerSheetRunnable.updateData(map, "id=" + villagerNpc.getId());
    SpletSheetExecutor.onExecute(villagerSheetRunnable);
  }
}
