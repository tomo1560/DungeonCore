package lbn.dungeon.contents.slotStone.other;

import org.bukkit.Location;
import org.bukkit.Sound;

import lbn.dungeon.contents.slotStone.level1.AbstractKillEffectSlotStone;
import lbn.item.slot.SlotLevel;
import lbn.util.particle.ParticleData;
import lbn.util.particle.ParticleType;

public class KillEffectTutorial extends AbstractKillEffectSlotStone{

	ParticleData particleData = new ParticleData(ParticleType.flame, 50);
	{
		particleData.setDispersion(0.7, 0.7, 0.7);
	}

	@Override
	protected String getEffectName() {
		return "Tutorial kill Effect";
	}

	@Override
	protected String getEffectId() {
		return "turorial_1";
	}

	@Override
	protected void playSound(Location location) {
		location.getWorld().playSound(location, Sound.VILLAGER_DEATH, (float) 0.75, 1);
	}

	@Override
	protected ParticleData getParticleData() {
		return particleData;
	}

	@Override
	public SlotLevel getLevel() {
		return SlotLevel.TUTORIAL;
	}
}