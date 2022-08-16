package me.proton.initsync.minijoin.enums;

import me.proton.initsync.minijoin.MiniJoin;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class Configuration
{
	// It's just a reference to the plugin instance.
	private static final MiniJoin PLUGIN = MiniJoin.instance();
	
	/**
	 * It gets a string from the config.yml file
	 *
	 * @param path The path to the value you want to get.
	 * @return A string.
	 */
	public static String getString(@NotNull Paths path)
	{
		Objects.requireNonNull(path, "Path inserted is null at the method.");
		
		return PLUGIN.configManager()
			 .get("config.yml")
			 .getString(path.pathString());
	}
	
	/**
	 * It gets an integer from the config.yml file
	 *
	 * @param path The path to the value you want to get.
	 * @return An integer.
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
	 * It gets a list of strings from the config.yml file
	 *
	 * @param path The path to the list in the config.yml file.
	 * @return A list of strings.
	 */
	public static List<String> getList(@NotNull Paths path)
	{
		Objects.requireNonNull(path, "Path inserted is null at the method.");
		
		return PLUGIN.configManager()
			 .get("config.yml")
			 .getStringList(path.pathString());
	}
	
	/**
	 * It returns a ConfigurationSection from the config.yml file
	 *
	 * @param path The path to the section.
	 * @return A ConfigurationSection object.
	 */
	@SuppressWarnings ("unused")
	public static ConfigurationSection getSection(@NotNull Paths path)
	{
		Objects.requireNonNull(path, "Path inserted is null at the method.");
		
		return PLUGIN.configManager()
			 .get("config.yml")
			 .getConfigurationSection(path.pathString());
	}
	
	/**
	 * It checks if the path is not null, and if it is not, it returns the boolean value of the path
	 *
	 * @param path The path to the boolean in the config.yml file.
	 * @return A boolean value.
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
