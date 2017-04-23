package lbn.common.event.player;

import lbn.api.LevelType;
import lbn.api.player.TheLowPlayer;

import org.bukkit.event.HandlerList;

public class PlayerChangeStatusLevelEvent extends TheLowPlayerEvent{
	private static final HandlerList handlers = new HandlerList();

	int level;
	LevelType type;

	public PlayerChangeStatusLevelEvent(TheLowPlayer player, int level, LevelType type) {
		super(player);
		this.level = level;
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public int getNowExp() {
		return player.getExp(type);
	}

	public LevelType getLevelType() {
		return type;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
