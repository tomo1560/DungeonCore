package lbn.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lbn.npc.VillagerNpc;

import org.bukkit.entity.Player;

import com.google.common.collect.HashMultimap;

/**
 * NPCとクエストの情報を管理するクラス
 */
public class NpcQuestHolder {
	static HashMultimap<String, Quest> villagerNameQuestMap = HashMultimap.create();

	public static void regist(Quest q) {
		villagerNameQuestMap.put(q.getEndVillagerName(), q);
	}

	public static List<Quest> getAvailableQuestList(VillagerNpc npc, Player p) {
		ArrayList<Quest> availableQuestList = new ArrayList<Quest>();

		Set<Quest> set = villagerNameQuestMap.get(npc.getName());
		for (Quest quest : set) {
			if (QuestManager.getStartQuestStatus(quest, p).canStart()) {
				availableQuestList.add(quest);
			}
		}
		return availableQuestList;
	}

	/**
	 * 村人から全てのクエストを取得
	 * @param npc
	 * @param p
	 * @return
	 */
	public static Set<Quest> getQuestList(VillagerNpc npc, Player p) {
		return villagerNameQuestMap.get(npc.getName());
	}
}