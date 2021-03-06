package net.l_bulb.dungeoncore.quest;

import java.util.Set;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import net.l_bulb.dungeoncore.common.event.player.PlayerStrengthFinishEvent;
import net.l_bulb.dungeoncore.common.event.quest.ComplateQuestEvent;
import net.l_bulb.dungeoncore.common.event.quest.DestructionQuestEvent;
import net.l_bulb.dungeoncore.common.event.quest.StartQuestEvent;
import net.l_bulb.dungeoncore.item.ItemInterface;
import net.l_bulb.dungeoncore.item.ItemManager;
import net.l_bulb.dungeoncore.mob.AbstractMob;
import net.l_bulb.dungeoncore.mob.LastDamageManager;
import net.l_bulb.dungeoncore.mob.MobHolder;
import net.l_bulb.dungeoncore.quest.abstractQuest.KillMobQuest;
import net.l_bulb.dungeoncore.quest.abstractQuest.PickItemQuest;
import net.l_bulb.dungeoncore.quest.abstractQuest.QuestType;
import net.l_bulb.dungeoncore.quest.abstractQuest.StrengthItemQuest;
import net.l_bulb.dungeoncore.quest.questData.PlayerQuestSession;
import net.l_bulb.dungeoncore.quest.questData.PlayerQuestSessionManager;

public class QuestListener implements Listener {
  @EventHandler
  public void onPickUp(PlayerPickupItemEvent e) {
    Item item = e.getItem();
    if (item == null || !item.isValid()) { return; }
    ItemStack itemStack = item.getItemStack();
    if (itemStack.getAmount() == 0) { return; }
    ItemInterface customItem = ItemManager.getCustomItem(itemStack);
    if (customItem == null) { return; }

    Player player = e.getPlayer();
    PlayerQuestSession session = PlayerQuestSessionManager.getQuestSession(player);

    Set<PickItemQuest> quest = PickItemQuest.getQuest(customItem);
    for (PickItemQuest pickItemQuest : quest) {
      boolean isProcessing = session.getProcessingStatus(pickItemQuest) == QuestProcessingStatus.PROCESSING;
      pickItemQuest.onPickUp(e, session);
      // 終了条件を満たしているなら終了する
      if (isProcessing) {
        onSatisfyCondition(player, pickItemQuest);
      }
    }
  }

  @EventHandler
  public void onStrength(PlayerStrengthFinishEvent e) {
    Player player = e.getPlayer();
    PlayerQuestSession questSession = PlayerQuestSessionManager.getQuestSession(player);
    // 今実行中のクエスト中からStrengthItemQuestを探しだす
    Set<Quest> doingQuest = questSession.getDoingQuestListByType(QuestType.STRENGTH_ITEM_QUEST);
    for (Quest quest : doingQuest) {
      // 実行中ならクエストの処理を行う
      if (questSession.getProcessingStatus(quest) == QuestProcessingStatus.PROCESSING) {
        ((StrengthItemQuest) quest).onStrength(e, questSession);
        // 終了条件を満たしているなら終了する
        onSatisfyCondition(player, quest);
      }

    }
  }

  @EventHandler
  public void onKillMob(EntityDeathEvent e) {
    AbstractMob<?> mob = MobHolder.getMob(e);
    // 死んだのがnull mobなら何もしない
    if (mob.isNullMob()) { return; }

    // 最後に倒したのがPlayerでないなら何もしない
    Player p = LastDamageManager.getLastDamagePlayer(e.getEntity());
    if (p == null) { return; }

    PlayerQuestSession questSession = PlayerQuestSessionManager.getQuestSession(p);

    // 今実行中のクエスト中からStrengthItemQuestを探しだす
    Set<Quest> doingQuest = questSession.getDoingQuestListByType(QuestType.KILL_MOB_QUEST);
    for (Quest quest : doingQuest) {
      // 実行中ならクエストの処理を行う
      if (questSession.getProcessingStatus(quest) == QuestProcessingStatus.PROCESSING) {
        ((KillMobQuest) quest).onDeath(e, questSession, mob);
        // 終了条件を満たしたなら村人の場所へ帰らせる
        onSatisfyCondition(p, quest);
      }

    }
  }

  public void onSatisfyCondition(Player p, Quest quest) {
    PlayerQuestSession questSession = PlayerQuestSessionManager.getQuestSession(p);
    if (quest.isComplate(questSession.getQuestData(quest))) {
      quest.onSatisfyComplateCondtion(p);
    }
  }

  @EventHandler
  public void onStartQuest(StartQuestEvent e) {}

  @EventHandler
  public void onComplateQuest(ComplateQuestEvent e) {}

  @EventHandler
  public void onRemoveQuest(DestructionQuestEvent e) {}

  @EventHandler
  public void onDrop(PlayerDropItemEvent e) {
    QuestInventory.drop(e);
  }

  @EventHandler
  public void onClick(InventoryDragEvent e) {
    QuestInventory.onDrag(e);
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
    QuestInventory.onClick(e);
  }
}
