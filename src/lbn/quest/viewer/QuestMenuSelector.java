package lbn.quest.viewer;

import java.util.List;

import lbn.common.menu.MenuSelecor;
import lbn.common.menu.SelectRunnable;
import lbn.quest.Quest;
import lbn.util.Message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuestMenuSelector extends MenuSelecor{
	public static QuestMenuSelectorRunnable run = new QuestMenuSelectorRunnable();

	static {
		new QuestMenuSelector().regist();
	}

	List<Quest> canStartQuestList;

	public QuestMenuSelector(List<Quest> canStartQuestList) {
		super("Quest Selector");
		this.canStartQuestList = canStartQuestList;
	}

	private QuestMenuSelector() {
		super("Quest Selector");
	}

	@Override
	public void open(Player p) {
		for (Quest quest : canStartQuestList) {
			createInventory.addItem(QuestSelectViewIcon.getItemStack(quest));
		}
		p.openInventory(createInventory);
	}

	@Override
	public void onSelectItem(Player p, ItemStack item) {
		run.run(p, item);
	}
}

class QuestMenuSelectorRunnable implements SelectRunnable{
	static {
		MenuSelecor menuSelecor = new MenuSelecor("quest_confirm");
		menuSelecor.regist();
		new MenuSelecor("quest_confirm").regist();
	}

	@Override
	public void run(Player p, ItemStack item) {
		//クエストアイテムでないとき
		if (!QuestSelectViewIcon.isThisItem(item)) {
//			Message.sendMessage(p, ChatColor.RED + "エラーが発生しました。このクエストを開始できません。(1)");
//			p.closeInventory();
			//何もしない
			return;
		}

		//クエストが存在しないとき
		Quest questByItem = QuestSelectViewIcon.getQuestByItem(item);
		if (questByItem == null) {
			Message.sendMessage(p, ChatColor.RED + "エラーが発生しました。このクエストを開始できません。(2)");
			p.closeInventory();
			return;
		}

		long currentTimeMillis = System.currentTimeMillis();
		MenuSelecor menuSelecor = new QuestConfirmMenu(questByItem);
		menuSelecor.open(p);
		System.out.println(System.currentTimeMillis() - currentTimeMillis + " ms");
	}
}
