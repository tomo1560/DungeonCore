package lbn.dungeon.contents.item.other.strengthBase;

import org.bukkit.inventory.ItemStack;

import lbn.common.event.player.PlayerSetStrengthItemResultEvent;
import lbn.dungeon.contents.strength_template.ChangeStrengthItemTemplate;
import lbn.dungeon.contents.strength_template.StrengthTemplate;
import lbn.item.AbstractItem;
import lbn.item.ItemInterface;
import lbn.item.itemInterface.StrengthChangeItemable;
import lbn.util.Message;

public abstract class StrengthBaseItem extends AbstractItem implements StrengthChangeItemable{

	@Override
	public String[] getStrengthDetail(int level) {
		return null;
	}


	@Override
	public StrengthTemplate getStrengthTemplate() {
		return new ChangeStrengthItemTemplate(getItem(), 1000, 70);
	}

	@Override
	public void onSetStrengthItemResult(PlayerSetStrengthItemResultEvent event) {
		int nextLevel = event.getNextLevel();
		if (getMaxStrengthCount() == nextLevel) {
			ItemStack newItem = getLastStrengthResultItem().getItem();
			event.setItem(newItem);
		}
	}

	protected abstract ItemInterface getLastStrengthResultItem();

	@Override
	protected String[] getDetail() {
		return Message.getMessage("このアイテムを{0}回強化に成功すると, [{1}]になります。", getMaxStrengthCount(), getLastStrengthResultItem().getSimpleName()).split(",");
	}

}