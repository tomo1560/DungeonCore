package lbn.mob.mobskill.skillrunnable;

import lbn.dungeoncore.Main;
import lbn.mob.mobskill.MobSkillRunnable;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MobSkillUpperTarget extends MobSkillRunnable {

  public MobSkillUpperTarget(String data) {
    super(data);
  }

  @Override
  public void execute(Entity target, Entity mob) {
    new BukkitRunnable() {
      @Override
      public void run() {
        target.setVelocity(new Vector(0, 0.6, 0));
      }
    }.runTaskLater(Main.plugin, 2);
  }

}
