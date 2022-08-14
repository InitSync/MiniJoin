package me.proton.initsync.minijoin.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public interface Configuration
{
	/**
	 * Loads the file with the given name.
	 *
	 * @param fileName The name of the file to load.
	 */
	void load(@NotNull String fileName);
	
	/**
	 * Reloads the specified file
	 *
	 * @param fileName The name of the file to reload.
	 */
	void reload(@NotNull String fileName);
	
	/**
	 * Saves the file with the given name.
	 *
	 * @param fileName The name of the file to save the data to.
	 */
	void save(@NotNull String fileName);
	
	/**
	 * Gets the FileConfiguration for the given file name.
	 *
	 * @param fileName The name of the file to load.
	 * @return A FileConfiguration object.
	 */
	FileConfiguration get(@NotNull String fileName);
}
