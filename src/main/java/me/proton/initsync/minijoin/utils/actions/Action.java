package me.proton.initsync.minijoin.utils.actions;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Action
{
	
	/**
	 * Plays a sound to a player.
	 *
	 * @param player The player to play the sound to.
	 * @param string The sound to play.
	 */
	void sound(@NotNull Player player, @NotNull String string);
	
	/**
	 * This function takes a player and a string, and does something with them.
	 *
	 * @param player The player who is being affected by the effect.
	 * @param string The string that the player will see in chat.
	 */
	void effect(@NotNull Player player, @NotNull String string);
	
	/**
	 * `Sets the title of the player's screen.`
	 *
	 * @param player The player to send the title to.
	 * @param string The title to be displayed
	 */
	void title(@NotNull Player player, @NotNull String string);
	
	/**
	 * Sends a message to the player's action bar.
	 *
	 * @param player The player you want to send the actionbar to.
	 * @param string The string you want to send to the player.
	 */
	void actionbar(@NotNull Player player, @NotNull String string);
	
	/**
	 * Execute the actions.
	 *
	 * @param player The player you want to execute the actions.
	 * @param string Action container.
	 */
	void execute(@NotNull Player player, @NotNull String string);
}
