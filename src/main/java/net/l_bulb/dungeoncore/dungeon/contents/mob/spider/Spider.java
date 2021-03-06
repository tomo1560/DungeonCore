package net.l_bulb.dungeoncore.dungeon.contents.mob.spider;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import net.l_bulb.dungeoncore.common.event.player.PlayerCustomMobSpawnEvent;
import net.l_bulb.dungeoncore.mob.customMob.abstractmob.AbstractSpider;

public class Spider extends AbstractSpider {

  @Override
  public String getName() {
    return "Spider";
  }

  @Override
  public void onSpawn(PlayerCustomMobSpawnEvent e) {

  }

  @Override
  public void onAttack(LivingEntity mob, LivingEntity target,
      EntityDamageByEntityEvent e) {

  }

  @Override
  public void onDamage(LivingEntity mob, Entity damager,
      EntityDamageByEntityEvent e) {

  }

  @Override
  public void onDeathPrivate(EntityDeathEvent e) {

  }

}
