package lbn.mobspawn.boss;

import org.bukkit.Location;

import lbn.mob.AbstractMob;
import lbn.mob.mob.BossMobable;
import lbn.mobspawn.SpawnLevel;
import lbn.mobspawn.gettter.SpletSheetSpawnMobGetter;
import lbn.mobspawn.point.MobSpawnerPoint;

public class SpredSheetSpawnBossGetter extends SpletSheetSpawnMobGetter{

	public SpredSheetSpawnBossGetter(int id) {
		super(id);
	}

	BossMobable boss;

	public BossMobable getBossMob() {
		if (boss != null) {
			return boss;
		}
		for (AbstractMob<?> abstractMob : moblist) {
			if (abstractMob instanceof BossMobable) {
				boss = (BossMobable) abstractMob;
				return boss;
			}
		}
		return null;
	}

	@Override
	public MobSpawnerPoint getMobSpawnerPoint(Location loc, int maxMobCount,
			SpawnLevel level) {
		return new BossSpawnPoint(id, loc, this);
	}

}