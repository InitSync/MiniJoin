package me.proton.initsync.minijoin.enums;

import me.proton.initsync.minijoin.MiniJoin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class Configuration
{
	private static final MiniJoin PLUGIN = JavaPlugin.getPlugin(MiniJoin.class);
	
	/**
	 * Returns a string from the config.
	 *
	 * @param path -> path enum.
	 *
	 * @return -> a string value.
	 */
	public static String getString(@NotNull Paths path)
	{
		Objects.requireNonNull(path, "Path inserted is null at the method.");
		
		return PLUGIN.configManager()
			.get("config.yml")
			.getString(path.pathString());
	}
	
	/**
	 * Returns an int value from the config.
	 * <p>
	 * NOTE: Unused Method.
	 *
	 * @param path -> path enum.
	 *
	 * @return -> a int value.
	 */
	@SuppressWarnings ("unused")
	public static int getInt(@NotNull Paths path)
	{
		Objects.requireNonNull(path, "Path inserted is null at the method.");
		
		return PLUGIN.configManager()
			.get("config.yml")
			.getInt(path.pathString());
	}
	
	/**
	 * Returns a string list.
	 *
	 * @param path -> path enum.
	 *
	 * @return -> a List<String>.
	 */
	public static List<String> getList(@NotNull Paths path)
	{
		Objects.requireNonNull(path, "Path inserted is null at the method.");
		
		return PLUGIN.configManager()
			.get("config.yml")
			.getStringList(path.pathString());
	}
	
	/**
	 * Returns a ConfigurationSection from the enum.
	 *
	 * @param path -> path enum.
	 *
	 * @return -> a ConfigurationSection.
	 */
	public static ConfigurationSection getSection(@NotNull Paths path)
	{
		Objects.requireNonNull(path, "Path inserted is null at the method.");
		
		return PLUGIN.configManager()
			 .get("config.yml")
			 .getConfigurationSection(path.pathString());
	}
	
	/**
	 * Returns a boolean value from the enum specified.
	 *
	 * @param path -> path enum.
	 *
	 * @return -> boolean value. True or false.
	 */
	public static boolean check(@NotNull Paths path)
	{
		Objects.requireNonNull(path, "Path inserted is null at the method.");
		
		return PLUGIN.configManager()
			 .get("config.yml")
			 .getBoolean(path.pathString());
	}
	
	private Configuration() {}
}
