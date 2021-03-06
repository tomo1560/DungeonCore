package net.l_bulb.dungeoncore.api.other;

import static java.lang.String.format;
import static org.bukkit.Bukkit.broadcastMessage;
import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.WHITE;

/**
 * This class has utility methods for message broadcasting.
 */
public final class Announcement {

  /**
   * Format string for announcement
   */
  private static final String FORMAT_ANNOUNCE = format("%s[%sAnnounce%s]:%s%%s", DARK_GRAY, GREEN, DARK_GRAY, AQUA);

  /**
   * Format string for attention
   */
  private static final String FORMAT_ATTENTION = format("%s[%sAttention!%s]:%s%%s", DARK_GRAY, DARK_RED, DARK_GRAY, WHITE);

  /**
   * Broadcast messages with "Announce" prefixes.
   *
   * @param message Message for broadcasting
   */
  public static void announce(String message) {
    broadcastMessage(format(FORMAT_ANNOUNCE, message));
  }

  /**
   * Broadcast messages with "Attention" prefixes.
   *
   * @param message Message for broadcasting
   */
  public static void attention(String message) {
    broadcastMessage(format(FORMAT_ATTENTION, message));
  }

}
