package net.l_bulb.dungeoncore.common.sound;

import java.util.HashMap;

public class SoundManager {
  static HashMap<String, SoundData> soundMap = new HashMap<>();

  public static void regist(String id, SoundData data) {
    soundMap.put(id, data);
  }

  public static SoundData fromId(String id) {
    if (id == null || id.isEmpty()) { return null; }
    return soundMap.get(id);
  }

  public static void clear() {
    soundMap.clear();
  }
}
