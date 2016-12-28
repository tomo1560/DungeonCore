package lbn.dungeon.contents.mob.skelton;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import lbn.common.event.player.PlayerCustomMobSpawnEvent;
import lbn.mob.mob.SummonMobable;
import lbn.mob.mob.abstractmob.AbstractSkelton;
import lbn.util.particle.ParticleData;
import lbn.util.particle.ParticleType;

public class SummonSkelton extends AbstractSkelton implements SummonMobable{

	@Override
	public String getName() {
		return "SUMMON SKELETON";
	}

	@Override
	public void onSpawn(PlayerCustomMobSpawnEvent e) {
		EntityEquipment equipment = e.getEntity().getEquipment();
		ItemStack item = new ItemStack(Material.BOW);
		item.addEnchantment(Enchantment.ARROW_FIRE, 1);
		equipment.setItemInHand(item);

		e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ZOMBIE_UNFECT, 1, 1);
		new ParticleData(ParticleType.portal, 100).setDispersion(0.5, 0.5, 0.5).run(e.getEntity().getLocation().add(0, 1, 0));
	}

	@Override
	public void onAttack(LivingEntity mob, LivingEntity target, EntityDamageByEntityEvent e) {

	}

	@Override
	public void onDamage(LivingEntity mob, Entity damager, EntityDamageByEntityEvent e) {

	}

	@Override
	public void onDeathPrivate(EntityDeathEvent e) {

	}

	@Override
	public int getDeadlineTick() {
		return 120 * 20;
	}

	@Override
	public void onProjectileHitEntity(LivingEntity mob, LivingEntity target, EntityDamageByEntityEvent e) {
	}

}