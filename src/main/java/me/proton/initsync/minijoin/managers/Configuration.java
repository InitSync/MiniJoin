package me.proton.initsync.minijoin.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public interface Configuration
{
	/**
	 * Create if not exists, or load the file specified.
	 *
	 * @param fileName -> name of file.
	 */
	void load(@NotNull String fileName);
	
	/**
	 * Reload the file specified.
	 *
	 * @param fileName -> name of file.
	 */
	void reload(@NotNull String fileName);
	
	/**
	 * Save the file specified.
	 *
	 * @param fileName -> save of file.
	 */
	void save(@NotNull String fileName);
	
	/**
	 * Returns the file specified, if the map not contains it (not exist), will return null.
	 *
	 * @param fileName -> name of file.
	 *
	 * @return -> a FileConfiguration or null.
	 */
	FileConfiguration get(@NotNull String fileName);
}
