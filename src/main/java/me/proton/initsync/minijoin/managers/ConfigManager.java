package me.proton.initsync.minijoin.managers;

import com.google.common.collect.Maps;
import me.proton.initsync.minijoin.MiniJoin;
import me.proton.initsync.minijoin.utils.Log;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ConfigManager implements Configuration
{
	// A reference to the main class of the plugin.
	private final MiniJoin plugin;
	// It's a map that contains the file name as a key and the file as a value.
	private final Map<String, File> fileMap;
	// It's a map that contains the file name as a key and the file as a value.
	private final Map<String, FileConfiguration> configurationMap;
	
	// It's a constructor that takes the plugin instance and the files to load.
	public ConfigManager(@NotNull MiniJoin plugin, @NotNull String... files)
	{
		this.plugin = Objects.requireNonNull(plugin, "Plugin at the constructor is null.");
		this.fileMap = Maps.newHashMap();
		this.configurationMap = Maps.newHashMap();
		
		for (String file : files) this.load(file);
	}
	
	/**
	 * Loads the file with the given name.
	 *
	 * @param fileName The name of the file to load.
	 */
	@Override
	public void load(@NotNull String fileName)
	{
		final File file = new File(
			 this.plugin.getDataFolder(),
			 fileName
		);
		if (!file.exists())
		{
			this.plugin.saveResource(fileName, false);
			
			Log.info(this.plugin, "Created file: <green>'" + fileName + "' <yellow>as successful.");
		}
		
		final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		Log.info(this.plugin, "Loaded file: <green>'" + fileName + "' <yellow>as successful.");
		
		if (!this.fileMap
			 .containsKey(fileName) && !this.configurationMap
			 .containsKey(fileName))
		{
			this.fileMap.put(fileName, file);
			this.configurationMap.put(fileName, configuration);
		}
	}
	
	/**
	 * Reloads the specified file
	 *
	 * @param fileName The name of the file to reload.
	 */
	@Override
	public void reload(@NotNull String fileName)
	{
		if (this.fileMap
			 .containsKey(fileName) && this.configurationMap
			 .containsKey(fileName))
		{
			try
			{
				this.configurationMap
					 .get(fileName)
					 .load(this.fileMap
							.get(fileName)
					 );
			}
			catch (IOException | InvalidConfigurationException e)
			{
				Log.error(this.plugin,
					 "Failed to reload the file: <red>'" + fileName + "'.",
					 "Printing error..."
				);
				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Saves the file with the given name.
	 *
	 * @param fileName The name of the file to save the data to.
	 */
	@Override
	public void save(@NotNull String fileName)
	{
		if (!this.fileMap
			 .containsKey(fileName) && !this.configurationMap
			 .containsKey(fileName))
		{
			try
			{
				this.configurationMap
					 .get(fileName)
					 .save(this.fileMap
							.get(fileName)
					 );
			}
			catch (IOException e)
			{
				Log.error(this.plugin,
					 "Failed to save the file: <red>'" + fileName + "'.",
					 "Printing error..."
				);
				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gets the FileConfiguration for the given file name.
	 *
	 * @param fileName The name of the file to load.
	 * @return A FileConfiguration object.
	 */
	@Override
	public FileConfiguration get(@NotNull String fileName)
	{
		if (this.configurationMap.containsKey(fileName))
		{
			return this.configurationMap.get(fileName);
		}
		else
		{
			Log.error(this.plugin, "The file to get has not been founded. Check your name.");
			return null;
		}
	}
}
