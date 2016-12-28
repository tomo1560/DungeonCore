package lbn.item.strength;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lbn.common.event.ChangeStrengthLevelItemEvent;
import lbn.item.ItemInterface;
import lbn.item.ItemManager;
import lbn.item.itemInterface.Strengthenable;
import lbn.util.ItemStackUtil;

public class StrengthOperator {
	/**
	 * 指定されたアイテムのレベルを取得
	 * @param strength
	 * @param item
	 * @return
	 */
	public static int getLevel(ItemStack item) {
		ItemInterface customItem = ItemManager.getCustomItem(item);
		//強化できないアイテムなら何もしない
		if (customItem == null || !(customItem instanceof Strengthenable)) {
			return 0;
		}
		Strengthenable strength = (Strengthenable) customItem;

		String dispName = ItemStackUtil.getName(item);
		String replace = dispName.replace(strength.getItemName(), "").replace( ChatColor.RESET.toString() + ChatColor.RED +  " +", "").trim();

		if (!replace.isEmpty() && NumberUtils.isDigits(replace)) {
			return Integer.parseInt(replace);
		}
		return 0;
	}

	/**
	 * 指定されたアイテムを指定されたレベルに強化する
	 * @param strength
	 * @param item
	 * @param toLevel
	 */
	public static void updateLore(ItemStack item, int toLevel) {
		//もとのアイテム
		ItemStack clone = item.clone();

		ItemInterface customItem = ItemManager.getCustomItem(item);
		//強化できないアイテムなら何もしない
		if (customItem == null || !(customItem instanceof Strengthenable)) {
			return;
		}
		Strengthenable strength = (Strengthenable) customItem;

		if (toLevel < 0) {
			toLevel = 0;
		}
		if (toLevel > strength.getMaxStrengthCount()) {
			toLevel = strength.getMaxStrengthCount();
		}

		List<String> lore = ItemStackUtil.getLore(item);
		//強化の情報を削除する
		removedStrengthLore(lore);

		//強化の情報を追加する
		if (strength.getStrengthDetail(toLevel) == null) {
			addStrengthLore(null, lore);
		} else {
			addStrengthLore(Arrays.asList(strength.getStrengthDetail(toLevel)), lore);
		}

		ItemStackUtil.setLore(item, lore);

		//nameを変更する
		if (toLevel == 0) {
			ItemStackUtil.setDispName(item, strength.getItemName());
		} else {
			ItemStackUtil.setDispName(item, strength.getItemName() + ChatColor.RESET + ChatColor.RED +  " +" + toLevel);
		}

		//TODO getAfterで更新させる
		ChangeStrengthLevelItemEvent event = new ChangeStrengthLevelItemEvent(clone, item, StrengthOperator.getLevel(clone), toLevel);
		Bukkit.getServer().getPluginManager().callEvent(event);
	}

	public static void addStrengthLore(List<String> strengthLore, List<String> lore) {
		lore.add(ChatColor.GREEN + "[強化性能]");
		if (strengthLore == null || strengthLore.size() == 0) {
			lore.add(ChatColor.YELLOW + "    なし");
		} else {
			for (String detail : strengthLore) {
				if (detail.contains("ADD:")) {
					lore.add(ChatColor.YELLOW + "    " + detail);
				} else {
					lore.add(ChatColor.YELLOW + "    ADD:" + detail);
				}
			}
		}
		lore.add("");
	}

	public static void removedStrengthLore(List<String> lore) {
		boolean inLine = false;
		//Loreを変更する
		Iterator<String> iterator = lore.iterator();
		while (iterator.hasNext()) {
			String line = iterator.next();
			if (line.contains("[強化性能]")) {
				iterator.remove();
				inLine = true;
			} else if (inLine) {
				if (line.isEmpty()) {
					inLine = false;
					iterator.remove();
					break;
				}
				iterator.remove();
			}
		}
	}

	public static ItemStack getItem(ItemStack item, int level) {
		//強化できるアイテムか確認
		ItemInterface itemInterface = ItemManager.getCustomItem(item);
		if (itemInterface == null) {
			return item;
		}

		if (itemInterface instanceof Strengthenable) {
			updateLore(item, level);
		}
		return item;
	}

	/**
	 * 強化できるアイテムならTRUE
	 * @param item
	 * @return
	 */
	public static boolean canStrength(ItemStack item) {
		ItemInterface customItem = ItemManager.getCustomItem(item);
		if (customItem == null) {
			return false;
		} else {
			return customItem instanceof Strengthenable;
		}
	}

	public static boolean allowWithStrength(ItemStack item) {
		if (item == null) {
			return true;
		}

		if (item.getType() == Material.AIR) {
			return true;
		}
		return false;
	}

}