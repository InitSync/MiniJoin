package me.proton.initsync.minijoin.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Log
{
	/**
	 * It sends a message to the console.
	 *
	 * @param plugin The plugin instance.
	 */
	public static void info(@NotNull JavaPlugin plugin, @NotNull String... strings) {
		Objects.requireNonNull(plugin, "Plugin instance is null.");
		
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
	 * It sends a warning message to the console
	 *
	 * @param plugin The plugin instance.
	 */
	@SuppressWarnings("unused")
	public static void warn(@NotNull JavaPlugin plugin, @NotNull String... strings) {
		Objects.requireNonNull(plugin, "Plugin instance is null.");
		
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
	 * It sends a message to the console with the prefix "MiniJoin" and the tag "[ERROR]".
	 *
	 * @param plugin The plugin instance.
	 */
	public static void error(@NotNull JavaPlugin plugin, @NotNull String... strings) {
		Objects.requireNonNull(plugin, "Plugin instance is null.");
		
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
