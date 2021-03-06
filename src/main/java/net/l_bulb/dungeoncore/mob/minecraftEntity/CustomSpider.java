package net.l_bulb.dungeoncore.mob.minecraftEntity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.event.CraftEventFactory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import net.l_bulb.dungeoncore.mob.customMob.LbnMobTag;
import net.l_bulb.dungeoncore.util.JavaUtil;

import net.minecraft.server.v1_8_R1.Enchantment;
import net.minecraft.server.v1_8_R1.EnchantmentManager;
import net.minecraft.server.v1_8_R1.EntityArrow;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntitySpider;
import net.minecraft.server.v1_8_R1.IRangedEntity;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R1.World;
import net.minecraft.server.v1_8_R1.WorldServer;

public class CustomSpider extends EntitySpider implements ICustomEntity<Spider>, IRangedEntity {
  static final LbnMobTag DEFAULT_TAG = new LbnMobTag(EntityType.SPIDER);

  public CustomSpider(World world) {
    this(world, DEFAULT_TAG);
  }

  LbnMobTag tag;

  public CustomSpider(World world, LbnMobTag tag) {
    super(world);
    this.tag = tag;
    try {
      AttackAISetter.removeAllAi(this);

      this.goalSelector.a(1, new PathfinderGoalFloat(this));
      this.goalSelector.a(2, this.a);
      this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
      // 戦闘AIをセットする
      AttackAISetter.setAttackAI(this, tag);
      this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0.8D));
      this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

      // 攻撃対象のAIをセットする
      AttackAISetter.setTargetAI(this, tag);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public CustomSpider(org.bukkit.World world, LbnMobTag tag) {
    this(((CraftWorld) world).getHandle(), tag);
  }

  // @Override
  // protected Entity findTarget() {
  // return this.world.findNearbyVulnerablePlayer(this, 16d);
  // }

  @Override
  public Spider spawn(Location loc) {
    WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
    // 位置を指定
    setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    // ワールドにentityを追加
    world.addEntity(this, SpawnReason.CUSTOM);
    return (Spider) getBukkitEntity();
  }

  @Override
  public void setPosition(double d0, double d1, double d2) {
    super.setPosition(d0, d1, d2);
    spawnLocation = new Location(world.getWorld(), d0, d1, d2);
  }

  Location spawnLocation = null;

  @Override
  public boolean W() {
    if (!tag.isWaterMonster()) {
      return super.W();
    } else {
      inWater = false;
      return false;
    }
  }

  @Override
  public boolean V() {
    if (!tag.isWaterMonster()) {
      return super.V();
    } else {
      return false;
    }
  }

  @Override
  public void a(EntityLiving entityliving, float f) {
    EntityArrow entityarrow = new EntityArrow(this.world, this, entityliving, 1.6F, 14 - this.world.getDifficulty().a() * 4);
    int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bz());
    int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bz());

    entityarrow.b(f * 2.0F + this.random.nextGaussian() * 0.25D + this.world.getDifficulty().a() * 0.11F);
    if (i > 0) {
      entityarrow.b(entityarrow.j() + i * 0.5D + 0.5D);
    }
    if (j > 0) {
      entityarrow.setKnockbackStrength(j);
    }
    if ((EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, bz()) > 0)) {
      EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
      this.world.getServer().getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
        entityarrow.setOnFire(event.getDuration());
      }
    }
    EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(this, bz(), entityarrow, 0.8F);
    if (event.isCancelled()) {
      event.getProjectile().remove();
      return;
    }
    if (event.getProjectile() == entityarrow.getBukkitEntity()) {
      this.world.addEntity(entityarrow);
    }
    makeSound("random.bow", 1.0F, 1.0F / (bb().nextFloat() * 0.4F + 0.8F));
  }

  @Override
  public LbnMobTag getMobTag() {
    return tag;
  }

  int spawnCount = 0;

  @Override
  protected void D() {
    super.D();

    if (getMobTag() == null) { return; }

    // 指定した距離以上離れていたら殺す
    spawnCount++;
    if (spawnCount >= 60) {
      spawnCount = 0;
      if (spawnLocation == null) { return; }
      if (JavaUtil.getDistanceSquared(spawnLocation, locX, locY, locZ) < tag.getRemoveDistance() * tag.getRemoveDistance()) { return; }
      if (getMobTag().isBoss()) {
        getBukkitEntity().teleport(spawnLocation);
      } else {
        die();
      }
    }
  }

}
