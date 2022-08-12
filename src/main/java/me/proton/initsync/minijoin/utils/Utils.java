package me.proton.initsync.minijoin.utils;

import me.proton.initsync.minijoin.MiniJoin;
import me.proton.initsync.minijoin.enums.Configuration;
import me.proton.initsync.minijoin.enums.Paths;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.title.Title;
import net.luckperms.api.model.group.Group;
import org.apache.commons.lang.Validate;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

public class Utils
{
	private static final MiniJoin PLUGIN = JavaPlugin.getPlugin(MiniJoin.class);
	private static final String PREFIX = Configuration.getString(Paths.PREFIX);
	
	/**
	 * Parse the text to Component using the MiniMessage API for the colors, gradient, hovers
	 * and more.
	 *
	 * @param player -> player for the placeholders.
	 * @param text -> text to parse to Component.
	 *
	 * @return -> a Component.
	 */
	public static Component miniMessage(@NotNull Player player, @NotNull String text)
	{
		Objects.requireNonNull(player, "Player is null");
		Validate.notEmpty(text, "Text is null or has empty.");
		
		final Group userGroup = PLUGIN.luckPerms()
			 .getGroupManager()
			 .getGroup(Objects.requireNonNull(
					PLUGIN.luckPerms()
						 .getUserManager()
						 .getUser(player.getUniqueId()))
				  .getPrimaryGroup()
			 );
		assert userGroup != null;
		
		return MiniMessage.miniMessage()
			 .deserialize(text,
				  Placeholder.parsed("player_name", player.getName()),
					Placeholder.parsed("player_rank",
						 Objects.requireNonNull(userGroup.getDisplayName())
					), Placeholder.parsed("prefix", PREFIX),
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
	 * @param player -> player to send the title.
	 * @param fadeIn -> first time title parameter.
	 * @param stay -> second time title parameter.
	 * @param fadeOut -> third time title parameter.
	 * @param title -> title message to parse to component.
	 * @param subtitle -> subtitle message to parse to component.
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
	 * Send an actionbar.
	 *
	 * @param plugin -> JavaPlugin instance required for the task.
	 * @param player -> player to send the actionbar.
	 * @param message -> message to parse to Component using MiniMessage API.
	 * @param duration -> duration for the actionbar runnable task.
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
	 * Create and show a BossBar to player.
	 *
	 * @param plugin -> JavaPlugin instance required for the task.
	 * @param player -> player to send the BossBar.
	 * @param message -> message to parse to Component using MiniMessage.
	 * @param color -> bossbar color enum.
	 * @param overlay -> bossbar overlay enum.
	 * @param progress -> bossbar progress amount float.
	 * @param duration -> bossbar update task duration.
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
	 * Send a message to the audience, also can send multiple messages.
	 *
	 * @param audience -> audience to send the messages.
	 * @param messages -> message or messages for send.
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
