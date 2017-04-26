package lbn.item.customItem.attackitem.weaponSkill;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

import lbn.common.projectile.ProjectileInterface;
import lbn.common.projectile.ProjectileManager;
import lbn.item.customItem.attackitem.weaponSkill.imple.WeaponSkillForOneType;
import lbn.item.customItem.attackitem.weaponSkill.imple.all.WeaponSkillBlastOff;
import lbn.item.customItem.attackitem.weaponSkill.imple.all.WeaponSkillBlastOffLevel2;
import lbn.item.customItem.attackitem.weaponSkill.imple.all.WeaponSkillCancel;
import lbn.item.customItem.attackitem.weaponSkill.imple.bow.ArrowStorm;
import lbn.item.customItem.attackitem.weaponSkill.imple.bow.BlindEye;
import lbn.item.customItem.attackitem.weaponSkill.imple.bow.Finale;
import lbn.item.customItem.attackitem.weaponSkill.imple.bow.IceArrow;
import lbn.item.customItem.attackitem.weaponSkill.imple.bow.ReleaseAura;
import lbn.item.customItem.attackitem.weaponSkill.imple.magic.Explosion;
import lbn.item.customItem.attackitem.weaponSkill.imple.magic.GravityField;
import lbn.item.customItem.attackitem.weaponSkill.imple.magic.HealRain;
import lbn.item.customItem.attackitem.weaponSkill.imple.magic.LeafFlare;
import lbn.item.customItem.attackitem.weaponSkill.imple.magic.MeteoStrike;
import lbn.item.customItem.attackitem.weaponSkill.imple.sword.BloodyHeal;
import lbn.item.customItem.attackitem.weaponSkill.imple.sword.BurstFlame;
import lbn.item.customItem.attackitem.weaponSkill.imple.sword.GrandSpike;
import lbn.item.customItem.attackitem.weaponSkill.imple.sword.LightningOrder;
import lbn.item.customItem.attackitem.weaponSkill.imple.sword.Lump;
import lbn.item.customItem.attackitem.weaponSkill.imple.sword.ProtectionArmor;
import lbn.util.DungeonLogger;

public class WeaponSkillFactory {
  private static HashMap<String, WeaponSkillInterface> skillMap = new HashMap<String, WeaponSkillInterface>();

  private static TreeSet<WeaponSkillInterface> skillLevelSkillMap = new TreeSet<WeaponSkillInterface>(new Comparator<WeaponSkillInterface>() {
    @Override
    public int compare(WeaponSkillInterface o1, WeaponSkillInterface o2) {
      // スキル解除は一番最初に表示する
      if (WeaponSkillCancel.isThisSkill(o1)) { return -1; }
      if (WeaponSkillCancel.isThisSkill(o2)) { return 1; }

      if (o1.getSkillLevel() != o2.getSkillLevel()) { return o1.getSkillLevel() - o2.getSkillLevel(); }
      return o1.getId().compareTo(o2.getId());
    }
  });

  /**
   * SkillListを取得
   * 
   * @return
   */
  public static Collection<WeaponSkillInterface> getSortedSkillList() {
    return skillLevelSkillMap;
  }

  /**
   * 武器スキルを登録する
   * 
   * @param weaponSkill
   */
  public static void regist(WeaponSkillData data) {
    WeaponSkillInterface weaponSkill = tempInstanceMap.get(data.getId());
    if (weaponSkill == null) {
      DungeonLogger.development("武器スキル：" + data.getName() + "がプログラム内に存在しません");
      return;
    }

    if (weaponSkill instanceof WeaponSkillForOneType) {
      ((WeaponSkillForOneType) weaponSkill).setData(data);
    }
    regist(weaponSkill);
  }

  public static void regist(WeaponSkillInterface weaponSkill) {
    skillMap.put(weaponSkill.getId(), weaponSkill);
    skillLevelSkillMap.add(weaponSkill);

    if (weaponSkill instanceof ProjectileInterface) {
      ProjectileManager.regist((ProjectileInterface) weaponSkill);
    }
  }

  /**
   * 武器スキルを取得する
   * 
   * @param id 武器スキルID
   */
  public static WeaponSkillInterface getWeaponSkill(String id) {
    return skillMap.get(id);
  }

  // 一時的なインスンタンスを保持するためのクラス
  static HashMap<String, WeaponSkillInterface> tempInstanceMap = new HashMap<String, WeaponSkillInterface>();

  public static void registTempData(WeaponSkillInterface skill) {
    tempInstanceMap.put(skill.getId(), skill);
  }

  public static void allRegist() {
    regist(new WeaponSkillBlastOff());
    regist(new WeaponSkillBlastOffLevel2());
    regist(new WeaponSkillCancel());
    registTempData(new ArrowStorm());
    registTempData(new LightningOrder());
    registTempData(new BloodyHeal());
    registTempData(new MeteoStrike());
    registTempData(new IceArrow());
    registTempData(new HealRain());
    registTempData(new ProtectionArmor());
    registTempData(new Explosion());
    registTempData(new Lump());
    registTempData(new BlindEye());
    registTempData(new BurstFlame());
    registTempData(new GrandSpike());
    registTempData(new Finale());
    registTempData(new LeafFlare());
    registTempData(new ReleaseAura());
    registTempData(new GravityField());
  }
}
