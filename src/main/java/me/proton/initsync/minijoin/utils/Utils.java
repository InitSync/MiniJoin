package me.proton.initsync.minijoin.utils;

import me.proton.initsync.minijoin.enums.Configuration;
import me.proton.initsync.minijoin.enums.Paths;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.title.Title;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

public class Utils
{
	// It's getting the prefix from the configuration file.
	private static final String PREFIX = Configuration.getString(Paths.PREFIX);
	
	/**
	 * It takes a player and a string, and returns a component that has the string parsed with the
	 * player's name, prefix, level, exp, world, ping, and kills
	 *
	 * @param player The player to use for the placeholders.
	 * @param text The text to be parsed.
	 * @return A Component object.
	 */
	public static Component miniMessage(@NotNull Player player, @NotNull String text)
	{
		Objects.requireNonNull(player, "Player is null");
		
		return MiniMessage.miniMessage()
			 .deserialize(text,
				  Placeholder.parsed("player_name", player.getName()),
				  Placeholder.parsed("prefix", PREFIX),
				  Placeholder.parsed("player_level", player.getLevel() + ""),
				  Placeholder.parsed("player_exp", player.getTotalExperience() + ""),
				  Placeholder.parsed("player_world", player.getWorld().getName()),
				  Placeholder.parsed("player_ping", player.getPing() + ""),
				  Placeholder.parsed("player_kills", player.getStatistic(
						 Statistic.PLAYER_KILLS
				  ) + "")
			 );
	}
	
	/**
	 * Send a title.
	 *
	 * @param player The player to send the title to.
	 * @param fadeIn The time it takes for the title to fade in.
	 * @param stay The amount of time the title will stay on the screen.
	 * @param fadeOut The time it takes for the title to fade out.
	 * @param title The title to display.
	 * @param subtitle The subtitle to display.
	 */
	public static void title(
		 @NotNull Player player,
		 int fadeIn,
		 int stay,
		 int fadeOut,
		 @NotNull String title,
		 @NotNull String subtitle
	)
	{
		Objects.requireNonNull(player, "Player is null");
		Validate.notEmpty(title, "Title is null or has empty.");
		Validate.notEmpty(subtitle, "Subtitle is null or has empty.");
		
		player.showTitle(Title.title(
			 miniMessage(player, title),
			 miniMessage(player, subtitle),
			 Title.Times
				  .times(
					   Duration.ofSeconds(fadeIn),
					   Duration.ofSeconds(stay),
					   Duration.ofSeconds(fadeOut)
				  )
		));
	}
	
	/**
	 * It sends an action bar message to a player for a certain duration
	 *
	 * @param plugin The plugin that is running the action bar.
	 * @param player The player to send the action bar to.
	 * @param message The message you want to send to the player.
	 * @param duration The duration of the action bar in milliseconds.
	 */
	public static void actionBar(
		 @NotNull JavaPlugin plugin,
		 @NotNull Player player,
		 @NotNull String message,
		 long duration
	)
	{
		Objects.requireNonNull(plugin, "Plugin is null");
		Objects.requireNonNull(player, "Player is null");
		Validate.notEmpty(message, "Message is null or has empty.");
		
		new BukkitRunnable()
		{
			long repeater = duration;
			
			@Override
			public void run()
			{
				player.sendActionBar(miniMessage(player, message));
				
				repeater -= 40L;
				if (repeater - 20L < 40L) cancel();
			}
		}.runTaskTimerAsynchronously(plugin, 0L, 40L);
	}
	
	/**
	 * It displays a boss bar with a message that is formatted with the MiniMessage API, and it disappears
	 * after a certain amount of time
	 *
	 * @param plugin The plugin that is running the boss bar.
	 * @param player The player to send the boss bar to.
	 * @param message The message to be displayed.
	 * @param color The color of the boss bar.
	 * @param overlay The overlay of the boss bar.
	 * @param progress The progress of the boss bar.
	 * @param duration The duration of the boss bar in milliseconds.
	 */
	public static void bossBar(
		 @NotNull JavaPlugin plugin,
		 @NotNull Player player,
		 @NotNull String message,
		 @NotNull BossBar.Color color,
		 @NotNull BossBar.Overlay overlay,
		 float[] progress,
		 long duration
	)
	{
		Objects.requireNonNull(plugin, "Plugin is null");
		Objects.requireNonNull(player, "Player is null");
		Validate.notEmpty(message, "Message is null or has empty.");
		Objects.requireNonNull(color, "Color is null");
		Objects.requireNonNull(overlay, "Overlay is null");
		
		final Component msg = miniMessage(player, message);
		final BossBar bossBar = BossBar.bossBar(
			 msg,
			 progress[0],
			 color,
			 overlay
		);
		
		new BukkitRunnable()
		{
			long repeater = duration;
			
			@Override
			public void run()
			{
				player.showBossBar(bossBar);
				
				progress[0]++;
				repeater -= 40L;
				if (repeater - 20L < 40L)
				{
					player.hideBossBar(bossBar);
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(plugin, 0L, 40L);
	}
	
	/**
	 * > This function sends a message to the audience
	 *
	 * @param audience The audience to send the message to.
	 */
	public static void message(@NotNull Audience audience, @NotNull String... messages)
	{
		Objects.requireNonNull(audience, "Audience is null");
		Validate.notEmpty(messages, "Messages is null or has empty.");
		
		for (String msg : messages)
		{
			audience.sendMessage(MiniMessage.miniMessage()
				 .deserialize(msg,
					  Placeholder.parsed("prefix", PREFIX)
				 ));
		}
	}
	
	private Utils() {}
}
