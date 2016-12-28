package lbn.mob.mob;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import lbn.common.event.player.PlayerCustomMobSpawnEvent;
import lbn.mob.AbstractMob;
import lbn.mob.LastDamageManager;
import lbn.mob.MobHolder;
import lbn.mob.SummonPlayerManager;
import lbn.mob.attribute.Attribute;
import lbn.mob.attribute.AttributeNormal;
import lbn.mob.mobskill.MobSkillExcuteConditionType;
import lbn.mob.mobskill.MobSkillInterface;
import lbn.mob.mobskill.MobSkillManager;
import lbn.player.AttackType;
import lbn.util.BlockUtil;
import lbn.util.spawn.LbnNBTTag;
import lbn.util.spawn.MobSpawnByCommand;

public class CommandableMob extends AbstractMob<Entity>{

	protected CommandableMob(LbnNBTTag nbtTag, String[] command, String name) {
		this.command = command;
		this.nbtTag = nbtTag;
		this.name = name;
		isRiding = nbtTag.isRiding();
	}

	boolean isRiding = false;

	@Override
	public boolean isRiding() {
		return isRiding;
	}


	public static CommandableMob getInstance(String[] command, String name, CommandSender sender) {
		LbnNBTTag nbtTag = MobSpawnByCommand.getTBTTagByCommand(command, sender);

		if (nbtTag == null) {
			return null;
		}

		CommandableMob commandableMob = new CommandableMob(nbtTag, command, name);

		AbstractMob<?> mob2 = MobHolder.getMob(name);
		if (mob2 != null && !mob2.isNullMob() && !(mob2 instanceof CommandableMob)) {
			commandableMob.mob = mob2;
		}
		return commandableMob;
	}

	AbstractMob<?> mob = null;

	String name;

	String[] command;

	LbnNBTTag nbtTag;

	double health = -1;

	@EventHandler
	protected Entity spawnPrivate(Location loc) {
		Entity spawn = MobSpawnByCommand.spawn(loc, command);

		if (health == -1) {
			if (spawn.getType().isAlive()) {
				health = ((Damageable)spawn).getHealth();
			}
		}
		return spawn;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onSpawn(PlayerCustomMobSpawnEvent e) {
		if (redstone != null) {
			redstone.getBlock().setType(Material.AIR);
		}

		//mobskill発動
		executeMobSkill(e.getEntity(), null, MobSkillExcuteConditionType.MOB_SPAWN);

		if (this.mob == null) {
			return;
		}
		this.mob.onSpawn(e);

	}

	@Override
	public void onProjectileHitEntity(LivingEntity mob, LivingEntity target,
			EntityDamageByEntityEvent e) {

		//mobskill
		if (target.getType() == EntityType.PLAYER || SummonPlayerManager.isSummonMob(target)) {
			executeMobSkill(mob, target, MobSkillExcuteConditionType.MOB_ATTACK);
		}
		super.onProjectileHitEntity(mob, target, e);

		//攻撃ポイント
		e.setDamage(e.getDamage() * attackPoint);
	}

	@Override
	public void onAttack(LivingEntity mob, LivingEntity target,
			EntityDamageByEntityEvent e) {
		if (mob == target) {
			e.setCancelled(true);
			return;
		}

		//skill
		executeMobSkill(mob, target, MobSkillExcuteConditionType.MOB_ATTACK);

		//攻撃力ポイント
		e.setDamage(e.getDamage() * attackPoint);

		//ジャイアントの攻撃範囲調整
		if (getEntityType() == EntityType.GIANT) {
			Location location = mob.getLocation();
			location.setY(0);
			Location location2 = target.getLocation();
			location2.setY(0);
			if (location.distance(location2) > 2.5) {
				e.setCancelled(true);
			}
		} else if (getEntityType() == EntityType.GUARDIAN) {
		//ガーディアンの攻撃速度調整
			if (rnd.nextInt(4) != 0) {
				e.setCancelled(true);
			}
		}

		//もとのmobの効果
		if (this.mob == null) {
			return;
		}
		this.mob.onAttack(mob, target, e);
	}

	@Override
	public void onDamage(LivingEntity mob, Entity damager,
			EntityDamageByEntityEvent e) {
		if (mob == damager) {
			e.setCancelled(true);
			return;
		}

		//skill
		if (damager.getType().isAlive()) {
			executeMobSkill(mob, (LivingEntity) damager, MobSkillExcuteConditionType.MOB_DAMAGED);
		} else if (damager.getType() == EntityType.ARROW) {
			ProjectileSource shooter = ((Arrow)damager).getShooter();
			if (shooter instanceof Player) {
				executeMobSkill(mob, (LivingEntity) shooter, MobSkillExcuteConditionType.MOB_DAMAGED);
			}
		}

		//耐性
		switch (LastDamageManager.getLastDamageAttackType(mob)) {
		case SWORD:
			if (swordRegistance == 100) {
				e.setCancelled(true);
			}
			e.setDamage(e.getDamage() * (1 - swordRegistance / 100.0));
			break;
		case MAGIC:
			if (magicRegistance == 100) {
				e.setCancelled(true);
			}
			e.setDamage(e.getDamage() * (1- magicRegistance / 100.0));
			break;
		case BOW:
			if (bowRegistance == 100) {
				e.setCancelled(true);
			}
			e.setDamage(e.getDamage() * (1 - bowRegistance / 100.0));
			break;
		default:
			break;
		}

		//防御ポイント
		e.setDamage(e.getDamage() * defencePoint);

		if (this.mob == null) {
			return;
		}
		this.mob.onDamage(mob, damager, e);

	}

	@Override
	public void onOtherDamage(EntityDamageEvent e) {
		if (this.mob == null) {
			return;
		}
		this.mob.onOtherDamage(e);
	}

	@Override
	public void onDeathPrivate(EntityDeathEvent e) {
		//RSを設置
		if (redstone != null) {
			BlockUtil.setRedstone(redstone);
		}

		//mobskill発動
		executeMobSkill(e.getEntity(), e.getEntity().getKiller(), MobSkillExcuteConditionType.MOB_DEATH);

		if (this.mob == null) {
			return;
		}
		this.mob.onDeathPrivate(e);
	}

	@Override
	public EntityType getEntityType() {
		return nbtTag.type;
	}

	private int index = 0;

	private double[] dropPerList = {0.0, 0.0};
	private ItemStack[] dropItemList = {null, null};

	public void setDropItem(ItemStack item, double dropPer) {
		dropPerList[index] = dropPer;
		dropItemList[index] = item;
		index++;
	}

	@Override
	public List<ItemStack> getDropItem(Player lastDamagePlayer) {
		List<ItemStack> dropItem = new ArrayList<ItemStack>();
		if (this.mob != null) {
			dropItem.addAll(this.mob.getDropItem(lastDamagePlayer));
		}
		Random random = new Random();
		for (int i = 0; i < index; i++) {
			if (random.nextInt(1000) < dropPerList[i] * 10) {
				dropItem.add(dropItemList[i]);
			}
		}
		return dropItem;
	}

	/**
	 * ターゲット時
	 */
	public void onTarget(EntityTargetLivingEntityEvent event) {
		LivingEntity target = event.getTarget();
		if (target == null || target == event.getEntity()) {
			event.setCancelled(true);
			return;
		}

		if (target.getType() == EntityType.PLAYER || SummonPlayerManager.isSummonMob(target)) {
			executeMobSkill((LivingEntity) event.getEntity(), event.getTarget(), MobSkillExcuteConditionType.TARGET_PLAYER);
		}
	}

	HashSet<String> skillNameSet = new HashSet<String>();

	public void addSkill(String skillName) {
		skillNameSet.add(skillName);
	}

	static protected Random rnd = new Random();

	public void executeMobSkill(LivingEntity mob, LivingEntity target, MobSkillExcuteConditionType type) {
		Set<MobSkillInterface> skillList = MobSkillManager.getSkill(skillNameSet, type);
		for (MobSkillInterface skill : skillList) {
			MobSkillExecutor(mob, target, skill);
		}
	}


	public static void MobSkillExecutor(Entity mob, Entity target, MobSkillInterface skill) {
		//発動タイミングを調べる
		if (!skill.getTiming().canExecute(mob)) {
			return;
		}

		//確立を調べる
		if (rnd.nextInt(100) + 1 > skill.excutePercent()) {
			return;
		}
		skill.execute(target, mob);
	}

	 int money = 10;
	 int exp = -1;

	 public void setExp(int exp) {
		 if (exp >= 0) {
			 this.exp = exp;
		 }
	}

	 public void setMoney(int money) {
		 if (money >= 0) {
			 this.money = money;
		 }
	}

	 @Override
	public int getExp(AttackType type) {
		 return exp;
	}

	 @Override
	public int getDropGalions() {
		 return money;
	}

	 double swordRegistance = 0;
	 double bowRegistance = 0;
	 double magicRegistance = 0;

	public void setSwordRegistance(double swordRegistance) {
		if (swordRegistance < 0) {
			swordRegistance = 0;
		} else if (swordRegistance > 200) {
			swordRegistance = 200;
		}
		this.swordRegistance = swordRegistance;
	}


	public void setBowRegistance(double bowRegistance) {
		if (bowRegistance < 0) {
			bowRegistance = 0;
		} else if (bowRegistance > 200) {
			bowRegistance = 200;
		}
		this.bowRegistance = bowRegistance;
	}


	public void setMagicRegistance(double magicRegistance) {
		if (magicRegistance < 0) {
			magicRegistance = 0;
		} else if (magicRegistance > 200) {
			magicRegistance = 200;
		}
		this.magicRegistance = magicRegistance;
	}

	Location redstone = null;
	public void setRedstoneLocation(Location loc) {
		redstone = loc;
	}

	Attribute attribute = new AttributeNormal();
	public void setAttribute(Attribute attribute) {
		if (attribute != null) {
			this.attribute = attribute;
		}
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	double attackPoint = 1;
	public void setAttackPoint(double attackPoint) {
		this.attackPoint = attackPoint;
	}

	double defencePoint = 1;
	public void setDefencePoint(double defencePoint) {
		this.defencePoint = defencePoint;
	}

}