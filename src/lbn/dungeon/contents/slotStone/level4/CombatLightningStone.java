package lbn.dungeon.contents.slotStone.level4;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;

import lbn.common.event.player.PlayerCombatEntityEvent;
import lbn.item.slot.SlotLevel;
import lbn.item.slot.slot.CombatSlot;
import lbn.util.LivingEntityUtil;

public class CombatLightningStone extends CombatSlot{

	@Override
	public String getSlotName() {
		return "ライトニング +++";
	}

	@Override
	public String getSlotDetail() {
		return "攻撃をした時に、一定確率で雷を落とす";
	}

	@Override
	public String getId() {
		return "slot_combat_lightning";
	}

	@Override
	public ChatColor getNameColor() {
		return ChatColor.YELLOW;
	}

	@Override
	public SlotLevel getLevel() {
		return SlotLevel.LEVEL4;
	}

	static Random rnd = new Random();
	@Override
	public void onCombat(PlayerCombatEntityEvent e) {
		int nextInt = rnd.nextInt(4);
		if (nextInt == 0) {
			LivingEntity enemy = e.getEnemy();
			LivingEntityUtil.strikeLightningEffect(enemy.getLocation(), e.getPlayer());
			enemy.damage(4.0);
			enemy.setFireTicks(3 * 20);
		}
	}

}