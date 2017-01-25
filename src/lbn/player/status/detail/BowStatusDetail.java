package lbn.player.status.detail;

import lbn.player.TheLowLevelType;
import lbn.player.TheLowPlayer;
import lbn.player.status.IStatusDetail;

import org.bukkit.Material;

public class BowStatusDetail extends IStatusDetail{
	public BowStatusDetail(TheLowPlayer p) {
		super(p);
	}

	@Override
	public String[] getIndexDetail() {
		return new String[]{"弓で敵を倒すたびに", "レベルが上がっていきます。"};
	}

	@Override
	public String[] getDetailByLevel(int level) {
		return new String[]{};
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
	public TheLowLevelType getLevelType() {
		return TheLowLevelType.BOW;
	}
}