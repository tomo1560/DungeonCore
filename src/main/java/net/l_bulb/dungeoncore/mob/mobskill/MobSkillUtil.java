package net.l_bulb.dungeoncore.mob.mobskill;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import net.l_bulb.dungeoncore.common.particle.ParticleData;
import net.l_bulb.dungeoncore.dungeoncore.Main;

public class MobSkillUtil {
  /**
   * MobSkill用のパーティクルを実行する
   *
   * @param particleData
   * @param targetList
   * @param mob
   * @param particleLocType
   */
  public static void playParticle(ParticleData particleData, ArrayList<Entity> targetList, Entity mob, ParticleLocationType particleLocType) {
    if (particleData != null) {
      // 軽量化のため、順次実行
      new BukkitRunnable() {
        int i = 0;

        @Override
        public void run() {
          if (i >= targetList.size()) {
            cancel();
            return;
          }
          particleData.run(particleLocType.getLocation(mob, targetList.get(i)));
          i++;
        }
      }.runTaskTimer(Main.plugin, 0, 1);
    }

  }
}
