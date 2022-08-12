package me.proton.initsync.minijoin.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Log
{
	/**
	 * Send a log of info level.
	 *
	 * @param plugin -> JavaPlugin instance required.
	 * @param strings -> log or logs to send.
	 */
	public static void info(@NotNull JavaPlugin plugin, @NotNull String... strings) {
		Objects.requireNonNull(plugin, "Plugin instance is null.");
		Validate.notEmpty(strings, "Strings is null or has empty.");
		
		for (String log : strings) {
			log = "<gradient:blue:dark_aqua>MiniJoin</gradient> <green>[INFO] <dark_gray><b>></b> "
				 + "<yellow>" + log;
			
			plugin.getServer()
				 .getConsoleSender()
				 .sendMessage(MiniMessage.miniMessage()
						.deserialize(log)
				 );
		}
	}
	
	/**
	 * Send a log of warning level.
	 *
	 * @param plugin -> JavaPlugin instance required.
	 * @param strings -> log or logs to send.
	 */
	public static void warn(@NotNull JavaPlugin plugin, @NotNull String... strings) {
		Objects.requireNonNull(plugin, "Plugin instance is null.");
		Validate.notEmpty(strings, "Strings is null or has empty.");
		
		for (String log : strings) {
			log = "<gradient:blue:dark_aqua>MiniJoin</gradient> <yellow>[WARN] <dark_gray><b>></b> "
				 + "<yellow>" + log;
			
			plugin.getServer()
				 .getConsoleSender()
				 .sendMessage(MiniMessage.miniMessage()
					  .deserialize(log)
				 );
		}
	}
	
	/**
	 * Send a log of error level.
	 *
	 * @param plugin -> JavaPlugin instance required.
	 * @param strings -> log or logs to send.
	 */
	public static void error(@NotNull JavaPlugin plugin, @NotNull String... strings) {
		Objects.requireNonNull(plugin, "Plugin instance is null.");
		Validate.notEmpty(strings, "Strings is null or has empty.");
		
		for (String log : strings) {
			log = "<gradient:blue:dark_aqua>MiniJoin</gradient> <red>[ERROR] <dark_gray><b>></b> "
				 + "<yellow>" + log;
			
			plugin.getServer()
				 .getConsoleSender()
				 .sendMessage(MiniMessage.miniMessage()
						.deserialize(log)
				 );
		}
	}
	
	private Log() {}
}
