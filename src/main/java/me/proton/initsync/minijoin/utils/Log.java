package me.proton.initsync.minijoin.utils;

import me.proton.initsync.minijoin.MiniJoin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class Log
{
	/**
	 * It sends a message to the console
	 */
	public static void info(@NotNull String... strings) {
		for (String log : strings) {
			log = "<gradient:blue:aqua>MiniJoin</gradient> <green>[INFO] <dark_gray><b>></b> "
				 + "<yellow>" + log;
			
			MiniJoin.instance()
				 .getServer()
				 .getConsoleSender()
				 .sendMessage(MiniMessage.miniMessage()
						.deserialize(log)
				 );
		}
	}
	
	/**
	 * It sends a warning message to the console
	 */
	@SuppressWarnings("unused")
	public static void warn(@NotNull String... strings) {
		for (String log : strings) {
			log = "<gradient:blue:aqua>MiniJoin</gradient> <yellow>[WARN] <dark_gray><b>></b> "
				 + "<yellow>" + log;
			
			MiniJoin.instance()
				 .getServer()
				 .getConsoleSender()
				 .sendMessage(MiniMessage.miniMessage()
					  .deserialize(log)
				 );
		}
	}
	
	/**
	 * It sends a message to the console with the prefix "MiniJoin" and the tag "[ERROR]".
	 */
	public static void error(@NotNull String... strings) {
		for (String log : strings) {
			log = "<gradient:blue:aqua>MiniJoin</gradient> <red>[ERROR] <dark_gray><b>></b> "
				 + "<yellow>" + log;
			
			MiniJoin.instance()
				 .getServer()
				 .getConsoleSender()
				 .sendMessage(MiniMessage.miniMessage()
						.deserialize(log)
				 );
		}
	}
	
	private Log() {}
}
