package lbn.item.itemInterface;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import lbn.item.Cooltimable;


public interface MagicExcuteable extends Cooltimable{
	ItemStack getItem();

	int getNeedMagicPoint();

	boolean isShowMessageIfUnderCooltime();

	void excuteMagic(Player p, PlayerInteractEvent e);
}