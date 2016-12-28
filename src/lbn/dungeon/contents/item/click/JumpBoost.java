package lbn.dungeon.contents.item.click;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import lbn.dungeoncore.Main;
import lbn.item.itemAbstract.RightClickItem;
import lbn.util.Message;
import lbn.util.particle.ParticleData;
import lbn.util.particle.ParticleType;

public class JumpBoost extends RightClickItem{
	@Override
	public String getItemName() {
		return "Jumping Feather";
	}

	@Override
	protected boolean excuteOnRightClick2(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		Location location = player.getLocation();
		location.setY(0);
		Material type = location.getBlock().getType();
		if (type == Material.LAPIS_BLOCK) {
			Message.sendMessage(player, "この場所はこのアイテムが使えないエリアです");
			return false;
		}


		player.setVelocity(new Vector(0, 2, 0));
		player.getWorld().playSound(player.getLocation(), Sound.BAT_LOOP, 1, 3);
		new ParticleData(ParticleType.crit, 100).setDispersion(0.5, 0.5, 0.5).run(player.getLocation());

		new BukkitRunnable() {
			int count = 0;
			@Override
			public void run() {
				if (count == 60 * 20) {
					cancel();
				}

				if (player.isDead() || !player.isOnline() || player.getGameMode() == GameMode.CREATIVE) {
					cancel();
				}

				if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
					cancel();
				}
				count++;
				player.setFallDistance(0);
			}
		}.runTaskTimer(Main.plugin, 20, 1);

		return true;
	}

	@Override
	protected Material getMaterial() {
		return  Material.FEATHER;
	}

	@Override
	protected String[] getDetail() {
		return new String[]{"右クリックをすることで", "真上に飛べます。"};
	}

	@Override
	public String getId() {
		return getItemName().toLowerCase();
	}

	@Override
	protected boolean isConsumeWhenUse() {
		return true;
	}

	@Override
	public int getBuyPrice(ItemStack item) {
		return 2000;
	}

}