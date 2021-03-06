package net.l_bulb.dungeoncore.mobspawn.gettter;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.entity.EntityType;

import net.l_bulb.dungeoncore.dungeon.contents.mob.NormalMob;
import net.l_bulb.dungeoncore.mob.AbstractMob;
import net.l_bulb.dungeoncore.mob.MobHolder;
import net.l_bulb.dungeoncore.mob.customMob.NullMob;

public class SpawnMobGetterManager {
  static HashMap<String, SpawnMobGetterInterface> spawnMobList = new HashMap<>();

  /**
   * SpawnMobGetterを登録する
   *
   * @param spawnMob
   */
  public static void registSpawnMobGetter(SpawnMobGetterInterface spawnMob) {
    spawnMobList.put(spawnMob.getName().toUpperCase(), spawnMob);
  }

  /**
   * SpawnMobGetterをnameから取得する
   *
   * @param name
   * @return
   */
  public static SpawnMobGetterInterface getSpawnMobGetter(String name) {
    try {
      // もしLivingEntity名と一致するならそれを返す
      EntityType valueOf = EntityType.valueOf(name.toUpperCase());
      if (valueOf != null && valueOf.isAlive()) {
        OneMonsterGetter mobGetter = new OneMonsterGetter(new NormalMob(valueOf));
        return mobGetter;
      }
    } catch (Exception e) {}

    AbstractMob<?> mob = MobHolder.getMob(name);
    if (mob != null && !mob.getClass().equals(NullMob.class)) {
      OneMonsterGetter mobGetter = new OneMonsterGetter(mob);
      return mobGetter;
    }
    return spawnMobList.get(name.toUpperCase());
  }

  public static Collection<String> getNames() {
    return spawnMobList.keySet();
  }

  public static boolean contains(String name) {
    return spawnMobList.containsKey(name.toUpperCase());
  }
}
