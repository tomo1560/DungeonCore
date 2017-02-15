package lbn.util.spawn;

import lbn.mob.AIType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class LbnMobTag {
	public LbnMobTag(Entity bukkitEntity) {
		type = bukkitEntity.getType();
	}

	public LbnMobTag(EntityType type) {
		this.type = type;
	}

	EntityType type;

	boolean isRiding = false;

	boolean isWaterMonster = false;

	int attackCountPerSec = 1;

	float attackReach = -1;

	int shotTarm = 1;

	boolean isJumpAttack = false;

	boolean isAvoidPlayer = false;

	double nearingSpeed = 1;

	boolean isSummonMob = false;

	AIType aiType = AIType.NORMAL;

	public void setRiding(boolean isRiding) {
		this.isRiding = isRiding;
	}

	public boolean isRiding() {
		return isRiding;
	}

	/**
	 * EntityTypeを取得
	 * @return
	 */
	public EntityType getType() {
		return type;
	}

	/**
	 * 水を無視するならTRUE
	 * @return
	 */
	public boolean isWaterMonster() {
		return isWaterMonster;
	}

	/**
	 * 水を無視するならTRUE
	 * @param isWaterMonster
	 */
	public void setWaterMonster(boolean isWaterMonster) {
		this.isWaterMonster = isWaterMonster;
	}

	/**
	 * 一秒間の攻撃速度を取得
	 * @return
	 */
	public int getAttackCountPerSec() {
		return attackCountPerSec;
	}

	/**
	 * 一秒間の攻撃速度をセットする
	 * @param attackCountPerSec
	 */
	public void setAttackCountPerSec(int attackCountPerSec) {
		this.attackCountPerSec = attackCountPerSec;
	}

	/**
	 * 攻撃範囲を取得
	 * @return
	 */
	public float getAttackReach() {
		return attackReach;
	}

	/**
	 * 攻撃範囲をセットする
	 * @param attackReach
	 */
	public void setAttackReach(float attackReach) {
		this.attackReach = attackReach;
	}

	/**
	 * ジャンプ斬りをするか取得
	 * @return
	 */
	public boolean isJumpAttack() {
		return isJumpAttack;
	}

	/**
	 * ジャンプ斬りをするかどうかセットする
	 * @param isJumpAttack
	 */
	public void setJumpAttack(boolean isJumpAttack) {
		this.isJumpAttack = isJumpAttack;
	}

	/**
	 * Playerを避ける動きをするならTRUE
	 * @return
	 */
	public boolean isAvoidPlayer() {
		return isAvoidPlayer;
	}

	/**
	 * Playerを避ける動きをするかどうかセットする
	 * @param isAvoidPlayer
	 */
	public void setAvoidPlayer(boolean isAvoidPlayer) {
		this.isAvoidPlayer = isAvoidPlayer;
	}

	/**
	 * Playerに近づく速度を取得
	 * @return
	 */
	public double getNearingSpeed() {
		return nearingSpeed;
	}

	/**
	 * Playerに近づく速度をセットする
	 * @param nearingSpeed
	 */
	public void setNearingSpeed(double nearingSpeed) {
		this.nearingSpeed = nearingSpeed;
	}

	/**
	 * サモンゾンビならTRUE
	 * @return
	 */
	public boolean isSummonMob() {
		return isSummonMob;
	}

	/**
	 * サモンゾンビかどうかセットする
	 * @param isSummonMob
	 */
	public void setSummonMob(boolean isSummonMob) {
		this.isSummonMob = isSummonMob;
	}

	/**
	 * AIのTypeをセットする
	 * @param aiType
	 */
	public void setAiType(AIType aiType) {
		if (aiType != null) {
			this.aiType = aiType;
		}
	}

	/**
	 * AITypeを取得
	 * @return
	 */
	public AIType getAiType() {
		return aiType;
	}

	/**
	 * 一秒間に何回打つかセットする
	 * @param shotTarm
	 */
	public void setShotTarm(int shotTarm) {
		this.shotTarm = shotTarm;
	}

	/**
	 * 一秒間に何回打つか取得
	 * @return
	 */
	public int getShotTarm() {
		return shotTarm;
	}
}