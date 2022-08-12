package me.proton.initsync.minijoin.utils.actions;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Action
{
	/**
	 * Sound action method.
	 *
	 * @param player -> player for the sound.
	 * @param string -> action container.
	 */
	void sound(@NotNull Player player, @NotNull String string);
	
	/**
	 * Effect action method.
	 *
	 * @param player -> player for the effect.
	 * @param string -> action container.
	 */
	void effect(@NotNull Player player, @NotNull String string);
	
	/**
	 * Title action method.
	 *
	 * @param player -> player for the title.
	 * @param string -> action container.
	 */
	void title(@NotNull Player player, @NotNull String string);
	
	/**
	 * ActionBar action method.
	 *
	 * @param player -> player for the actionbar.
	 * @param string -> action container.
	 */
	void actionbar(@NotNull Player player, @NotNull String string);
	
	/**
	 * Bossbar action method.
	 *
	 * @param player -> player for the bossbar.
	 * @param string -> action container.
	 */
	void bossbar(@NotNull Player player, @NotNull String string);
	
	/**
	 * Check if the string starts with some action identifier, example: [sound], if like this has,
	 * will execute the action.
	 *
	 * @param player -> player for the actions.
	 * @param string -> action container.
	 */
	void execute(@NotNull Player player, @NotNull String string);
}
