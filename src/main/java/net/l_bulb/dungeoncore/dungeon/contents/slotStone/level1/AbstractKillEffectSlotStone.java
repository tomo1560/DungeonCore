package net.l_bulb.dungeoncore.dungeon.contents.slotStone.level1;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import net.l_bulb.dungeoncore.common.event.player.PlayerKillEntityEvent;
import net.l_bulb.dungeoncore.common.particle.ParticleData;
import net.l_bulb.dungeoncore.item.slot.SlotLevel;
import net.l_bulb.dungeoncore.item.slot.slot.KillSlot;

public abstract class AbstractKillEffectSlotStone extends KillSlot {

  @Override
  public String getSlotName() {
    return "キルエフェクト " + getEffectName();
  }

  abstract protected String getEffectName();

  abstract protected String getEffectId();

  @Override
  public String getSlotDetail() {
    return "キルエフェクト" + getEffectName() + "を追加";
  }

  @Override
  public String getId() {
    return "slot_ke_" + getEffectId();
  }

  @Override
  public ChatColor getNameColor() {
    return ChatColor.GOLD;
  }

  @Override
  public SlotLevel getLevel() {
    return SlotLevel.LEVEL1;
  }

  @Override
  public void onKill(PlayerKillEntityEvent e) {
    LivingEntity enemy = e.getEnemy();
    Location location = enemy.getLocation();
    getParticleData().run(location.add(0, 1, 0));
    playSound(location);
  }

  abstract protected void playSound(Location location);

  abstract protected ParticleData getParticleData();
}
