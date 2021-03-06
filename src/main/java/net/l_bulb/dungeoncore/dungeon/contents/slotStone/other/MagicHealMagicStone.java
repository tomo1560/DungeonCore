package net.l_bulb.dungeoncore.dungeon.contents.slotStone.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.l_bulb.dungeoncore.common.event.player.PlayerKillEntityEvent;
import net.l_bulb.dungeoncore.common.particle.CircleParticleData;
import net.l_bulb.dungeoncore.common.particle.ParticleData;
import net.l_bulb.dungeoncore.common.particle.ParticleType;
import net.l_bulb.dungeoncore.item.ItemInterface;
import net.l_bulb.dungeoncore.item.slot.SlotLevel;
import net.l_bulb.dungeoncore.item.slot.slot.KillSlot;
import net.l_bulb.dungeoncore.player.customplayer.MagicPointManager;

public class MagicHealMagicStone extends KillSlot {

  int mpHealLevel;
  SlotLevel sLevel;
  String HealValueString;

  public MagicHealMagicStone(int mpHealLevel, SlotLevel sLevel, String HealValueString) {
    this.mpHealLevel = mpHealLevel;
    this.sLevel = sLevel;
    this.HealValueString = HealValueString;
  }

  public static List<ItemInterface> getAllItem() {
    ArrayList<ItemInterface> arrayList = new ArrayList<>();
    arrayList.add(new MagicHealMagicStone(1, SlotLevel.LEVEL3, "小"));
    arrayList.add(new MagicHealMagicStone(2, SlotLevel.LEVEL4, "中"));
    arrayList.add(new MagicHealMagicStone(3, SlotLevel.LEVEL5, "大"));
    return arrayList;
  }

  @Override
  public String getSlotName() {
    return "ポーシング Level." + mpHealLevel;
  }

  @Override
  public String getSlotDetail() {
    return "敵を倒した時一定確率でMPを回復";
  }

  @Override
  public String getId() {
    return "ms_mp_heal_" + mpHealLevel;
  }

  @Override
  public ChatColor getNameColor() {
    return ChatColor.LIGHT_PURPLE;
  }

  @Override
  public SlotLevel getLevel() {
    return sLevel;
  }

  Random rnd = new Random();

  ParticleData particleData = new CircleParticleData(new ParticleData(ParticleType.enchantmenttable, 3), 1.3);

  @Override
  public void onKill(PlayerKillEntityEvent e) {
    int nextInt = rnd.nextInt(5);
    if (nextInt == 0) {
      Player player = e.getPlayer();
      MagicPointManager.addMagicPoint(player, mpHealLevel * 3);
      e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_DEATH, 1, 2);
      // エフェクト
      Location add = player.getLocation().add(0, 1, 0);
      particleData.run(add);
    }
  }

}
