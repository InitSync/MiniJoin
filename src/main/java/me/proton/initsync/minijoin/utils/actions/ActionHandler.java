package me.proton.initsync.minijoin.utils.actions;

import me.proton.initsync.minijoin.MiniJoin;
import me.proton.initsync.minijoin.utils.Log;
import me.proton.initsync.minijoin.utils.Utils;
import net.kyori.adventure.bossbar.BossBar;
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
	private final MiniJoin plugin;
	
	/**
	 * Class Constructor.
	 *
	 * @param plugin -> JavaPlugin instance required for the actions methods.
	 */
	public ActionHandler(@NotNull MiniJoin plugin)
	{
		this.plugin = Objects.requireNonNull(plugin, "Plugin instance is null at the constructor.");
	}
	
	/**
	 * Sound action method.
	 *
	 * @param player -> player for the sound.
	 * @param string -> action container.
	 */
	@Override
	public void sound(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		// [sound]
		string = string.substring(8);
		
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
			Log.error(this.plugin,
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
	 * Effect action method.
	 *
	 * @param player -> player for the effect.
	 * @param string -> action container.
	 */
	@Override
	public void effect(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		// [effect]
		string = string.substring(9);
		
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
			Log.error(this.plugin,
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
				 .runTaskLater(plugin, () -> {
					 player.addPotionEffect(new PotionEffect(effectType, finalDuration, finalAmplifier));
				 }, 1L);
		}
	}
	
	/**
	 * Title action method.
	 *
	 * @param player -> player for the title.
	 * @param string -> action container.
	 */
	@Override
	public void title(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		// [title]
		string = string.substring(8);
		
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
			Log.error(this.plugin,
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
	 * ActionBar action method.
	 *
	 * @param player -> player for the actionbar.
	 * @param string -> action container.
	 */
	@Override
	public void actionbar(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		// [actionbar]
		string = string.substring(12);
		
		final String[] split = string.split(";", 2);
		final String message = split[0];
		final long duration = Long.parseLong(split[1]);
		
		Utils.actionBar(
			 this.plugin,
			 player,
			 message,
			 duration
		);
	}
	
	/**
	 * Bossbar action method.
	 *
	 * @param player -> player for the bossbar.
	 * @param string -> action container.
	 */
	@Override
	public void bossbar(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		// [bossbar]
		string  = string.substring(10);

		final String[] split = string.split(";", 5);
		final String message = split[0];
		final BossBar.Color color = BossBar.Color.valueOf(split[2]);
		final BossBar.Overlay overlay = BossBar.Overlay.valueOf(split[3]);
		final long duration = Long.parseLong(split[4]);
		final float[] progress = { Float.parseFloat(split[1]) };
		
		Utils.bossBar(
			 this.plugin,
			 player,
			 message,
			 color,
			 overlay,
			 progress,
			 duration
		);
	}
	
	/**
	 * Check if the string starts with some action identifier, example: [sound], if like this has,
	 * will execute the action.
	 *
	 * @param player -> player for the actions.
	 * @param string -> action container.
	 */
	@Override
	public void execute(@NotNull Player player, @NotNull String string)
	{
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(string, "String is null or has empty.");
		
		string = StringUtils.substringBetween(string, "[", "]");
		
		switch (string)
		{
			default ->
			{
				Log.error(this.plugin, "That action not exist or is invalid.");
				return;
			}
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
			case "bossbar" -> bossbar(player, string);
		}
		
		Log.error(this.plugin, "That action not exist or is invalid.");
	}
}
