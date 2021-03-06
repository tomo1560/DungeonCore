package net.l_bulb.dungeoncore.mob.minecraftEntity;

import org.bukkit.entity.LivingEntity;

public interface ICustomUndeadEntity<T extends LivingEntity> extends ICustomEntity<T> {
  public void setUndead(boolean isUndead);

  public boolean isUndead();

  public void setNonDayFire(boolean isNonDayFire);

  public boolean isNonDayFire();
}
