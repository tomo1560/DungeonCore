package lbn.item.itemInterface;

import lbn.api.player.TheLowLevelType;

import org.bukkit.entity.Player;

public interface AvailableLevelItemable {
	int getAvailableLevel();

	boolean isAvilable(Player player);

	TheLowLevelType getLevelType();;
}
