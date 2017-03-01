package lbn.dungeoncore.SpletSheet;

import lbn.item.ItemManager;
import lbn.item.attackitem.SpreadSheetAttackItem;
import lbn.item.attackitem.SpreadSheetWeaponData;
import lbn.item.itemAbstract.BowItem;
import lbn.item.itemAbstract.MagicItem;
import lbn.item.itemAbstract.SwordItem;

import org.bukkit.command.CommandSender;

public class WeaponSheetRunnable extends AbstractSheetRunable{

	public WeaponSheetRunnable(CommandSender sender) {
		super(sender);
	}

	@Override
	protected String getQuery() {
		return null;
	}

	@Override
	public String getSheetName() {
		return "weapon";
	}

	@Override
	public String[] getTag() {
		return new String[]{"id", "name", "material", "detail", "damageparcent", "type", "skilllevel", "defaultslot", "maxslot", "rank", "uselevel", //10
				"craftmatrial1", "craftcount1", "craftmatrial2", "craftcount2", "craftmatrial2", "craftcount2", "durability"//17
				};
	}

	@Override
	protected void excuteOnerow(String[] row) {
		SpreadSheetWeaponData data = new SpreadSheetWeaponData();
		data.setId(row[0]);
		data.setName(row[1]);
		data.setItemMaterial(row[2], sender);
		data.setDetail(row[3]);
		data.setDamageParcent(row[4]);
		String type = row[5];
		data.setSkillLevel(row[6]);
		data.setDefaultSlot(row[7]);
		data.setMaxSlot(row[8]);
		data.setRank(row[9]);
		data.setAvailableLevel(row[10]);
		data.setCraftItem(row[11], row[12]);
		data.setCraftItem(row[13], row[14]);
		data.setCraftItem(row[15], row[16]);
		data.setMaxDurability(row[17]);

		if (!data.check(sender)) {
			return;
		}

		SpreadSheetAttackItem item = null;
		if ("剣".equals(type)) {
			item = new SwordItem(data);
		} else if ("弓".equals(type)) {
			item = new BowItem(data);
		} else if ("魔法".equals(type)) {
			item = new MagicItem(data);
		}

		if (item == null) {
			sendMessage("武器のタイプが不正です");
			return;
		}

		//Itemを登録する
		ItemManager.registItem(item);
	}
}