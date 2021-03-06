package net.l_bulb.dungeoncore.dungeon.contents.mob.animal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.l_bulb.dungeoncore.common.particle.ParticleData;
import net.l_bulb.dungeoncore.common.particle.ParticleType;
import net.l_bulb.dungeoncore.player.ItemType;
import net.l_bulb.dungeoncore.util.LivingEntityUtil;

public class SummonHealSheep extends SummonSheep {

  public SummonHealSheep(int level, int strengthLevel) {
    super(level, strengthLevel);
  }

  @Override
  public String getName() {
    return "SUMMON HEALTH SHEEP";
  }

  @Override
  public DyeColor getColor() {
    return DyeColor.RED;
  }

  ParticleData particleData = new ParticleData(ParticleType.heart, 10);

  @Override
  protected void executeRun(LivingEntity entity, Player owner) {
    List<MetadataValue> metadata = entity.getMetadata("availableLevel");
    int level = 0;
    if (metadata != null && metadata.size() != 0) {
      level = metadata.get(0).asInt();
    }

    ArrayList<Player> nearByPlayer = LivingEntityUtil.getNearByPlayer(entity, 5 + (level * 0.05), 5, 5 + (level * 0.05));
    for (Player player : nearByPlayer) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 3, getLevel(level)), true);
      particleData.run(player.getLocation().add(0, 2, 0));
    }
  }

  public int getLevel(int level) {
    if (level < 30) {
      return 0;
    } else if (level < 60) {
      return 1;
    } else {
      return 2;
    }
  }

  @Override
  public ItemType getUseItemType() {
    return ItemType.MAGIC;
  }

}
