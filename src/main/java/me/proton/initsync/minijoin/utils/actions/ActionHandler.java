package me.proton.initsync.minijoin.utils.actions;

import me.proton.initsync.minijoin.MiniJoin;
import me.proton.initsync.minijoin.utils.Log;
import me.proton.initsync.minijoin.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ActionHandler implements Action
{
	// It's a reference to the main class.
	private final MiniJoin plugin;
	
	// It's a constructor.
	public ActionHandler()
	{
		this.plugin = MiniJoin.instance();
	}
	
	/**
	 * Plays a sound to a player.
	 *
	 * @param player The player to play the sound to.
	 * @param string The sound to play.
	 */
	@Override
	public void sound(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		string.replace("[sound] ", "");
		
		final String[] split = string.split(";", 3);
		final Sound sound = Sound.valueOf(split[0]);
		
		int volume;
		int pitch;
		try
		{
			volume = Integer.parseInt(split[1]);
			pitch = Integer.parseInt(split[2]);
		}
		catch (NumberFormatException e)
		{
			Log.error(
				 "Failed to parse the sound parameters.",
				 "Using the default values."
			);
			
			e.printStackTrace();
			
			volume = 1;
			pitch = 1;
		}
		
		player.playSound(
			 player.getLocation(),
			 sound,
			 volume,
			 pitch
		);
	}
	
	/**
	 * This function takes a player and a string, and does something with them.
	 *
	 * @param player The player who is being affected by the effect.
	 * @param string The string that the player will see in chat.
	 */
	@Override
	public void effect(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		string.replace("[effect] ", "");
		
		final String[] split = string.split(";", 3);
		final PotionEffectType effectType = PotionEffectType.getByName(split[0]);
		
		assert effectType != null;
		
		int duration;
		int amplifier;
		try
		{
			duration = Integer.parseInt(split[1]);
			amplifier = Integer.parseInt(split[2]);
		}
		catch (NumberFormatException e)
		{
			Log.error(
				 "Failed to parse the effect parameters.",
				 "Using the default values."
			);
			
			e.printStackTrace();
			
			duration = 1;
			amplifier = 1;
		}
		
		int finalDuration = duration;
		int finalAmplifier = amplifier;
		
		if (this.plugin
			 .getServer()
			 .isPrimaryThread())
		{
			player.addPotionEffect(new PotionEffect(effectType, duration, amplifier));
		}
		else
		{
			this.plugin
				 .getServer()
				 .getScheduler()
				 .runTaskLater(
					  MiniJoin.instance(),
					  () ->
					  {
							player.addPotionEffect(new PotionEffect(effectType, finalDuration, finalAmplifier));
						}, 1L);
		}
	}
	
	/**
	 * `Sets the title of the player's screen.`
	 *
	 * @param player The player to send the title to.
	 * @param string The title to be displayed
	 */
	@Override
	public void title(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		string.replace("[title] ", "");
		
		final String[] split = string.split(";", 5);
		final String title = split[0];
		final String subtitle = split[1];
		
		int fadeIn;
		int stay;
		int fadeOut;
		try
		{
			fadeIn = Integer.parseInt(split[2]);
			stay = Integer.parseInt(split[3]);
			fadeOut = Integer.parseInt(split[4]);
		}
		catch (NumberFormatException e)
		{
			Log.error(
				 "Failed to parse the title parameters.",
				 "Using the default values."
			);
			
			e.printStackTrace();
			
			fadeIn = 20;
			stay = 60;
			fadeOut = 20;
		}
		
		Utils.title(
			 player,
			 fadeIn,
			 stay,
			 fadeOut,
			 title,
			 subtitle
		);
	}
	
	/**
	 * Sends a message to the player's action bar.
	 *
	 * @param player The player you want to send the actionbar to.
	 * @param string The string you want to send to the player.
	 */
	@Override
	public void actionbar(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		string.replace("[actionbar] ", "");
		
		final String[] split = string.split(";", 2);
		final String message = split[0];
		final long duration = Long.parseLong(split[1]);
		
		Utils.actionBar(
			 player,
			 message,
			 duration
		);
	}
	
	/**
	 * Execute the actions.
	 *
	 * @param player The player you want to execute the actions.
	 * @param string Action container.
	 */
	@Override
	public void execute(@NotNull Player player, @NotNull String string)
	{
		StringUtils.substringBetween(string, "[", "]");
		
		switch (string)
		{
			case "sound" ->
			{
				sound(player, string);
				return;
			}
			case "effect" ->
			{
				effect(player, string);
				return;
			}
			case "title" ->
			{
				title(player, string);
				return;
			}
			case "actionbar" -> 
			{
			 	actionbar(player, string);
				return;
			}
			default -> Log.error("That action not exist or is invalid.");
		}
		
		Log.error("That action not exist or is invalid.");
	}
}
