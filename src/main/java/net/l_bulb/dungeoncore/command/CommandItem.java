package net.l_bulb.dungeoncore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.l_bulb.dungeoncore.dungeoncore.SpletSheet.ItemSheetRunnable;
import net.l_bulb.dungeoncore.dungeoncore.SpletSheet.SpletSheetExecutor;

public class CommandItem implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender paramCommandSender,
      Command paramCommand, String paramString,
      String[] paramArrayOfString) {
    SpletSheetExecutor.onExecute(new ItemSheetRunnable(paramCommandSender));
    return true;
  }

}
