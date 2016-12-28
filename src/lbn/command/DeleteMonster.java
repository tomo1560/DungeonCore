package lbn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import lbn.mob.AbstractMob;
import lbn.mob.MobHolder;
import lbn.mob.mob.SaveMobEntity;

public class DeleteMonster implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender paramCommandSender,
			Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		if (paramArrayOfString.length == 0) {
			return false;
		}

		String name = paramArrayOfString[0];
		AbstractMob<?> mob = MobHolder.getMob(name);
		if (mob == null || !(mob instanceof SaveMobEntity)) {
			return false;
		}

		((SaveMobEntity)mob).clearAllMob();
		return true;
	}

}