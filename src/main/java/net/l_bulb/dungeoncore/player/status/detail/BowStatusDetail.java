package net.l_bulb.dungeoncore.player.status.detail;

import org.bukkit.Material;

import net.l_bulb.dungeoncore.api.LevelType;
import net.l_bulb.dungeoncore.api.player.TheLowPlayer;
import net.l_bulb.dungeoncore.player.status.IStatusDetail;

public class BowStatusDetail extends IStatusDetail {
  public BowStatusDetail(TheLowPlayer p) {
    super(p);
  }

  @Override
  public String[] getIndexDetail() {
    return new String[] { "弓で敵を倒すたびに", "レベルが上がっていきます。" };
  }

  @Override
  public String[] getDetailByLevel(int level) {
    return new String[] {};
  }

  @Override
  public String getDisplayName() {
    return "BOW LEVEL";
  }

  @Override
  public Material getViewIconMaterial() {
    return Material.BOW;
  }

  @Override
  public LevelType getLevelType() {
    return LevelType.BOW;
  }
}
