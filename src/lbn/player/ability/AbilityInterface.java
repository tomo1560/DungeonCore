package lbn.player.ability;

import java.util.HashMap;

import lbn.api.PlayerStatusType;

/**
 *{@link PlayerStatusType}と値を指定してPlayerのAbilityを変更させる。 <br />
 *{@literal TheLowPlayer#addAbility(AbilityInterface)}でAbilityを追加 <br />
 *{@literal TheLowPlayer#removeAbility(AbilityInterface)}でAbilityを削除する <br />
 *{@link AbilityInterface#getId()}が同じなら内容が異なっても同じAbilityを示すように実装する必要があります
 */
public interface AbilityInterface {
	String getId();

	HashMap<PlayerStatusType, Double> getAbilityMap();

	AbilityType getAbilityType();
}
