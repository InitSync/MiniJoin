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

public class ConfigManager implements Configuration
{
	// It's a reference to the plugin instance.
	private final MiniJoin plugin;
	// It's a map that contains the file name as a key and the file as a value.
	private final Map<String, File> fileMap;
	// It's a map that contains the file name as a key and the file as a value.
	private final Map<String, FileConfiguration> configurationMap;
	
	// It's a constructor that takes the plugin instance and the files to load.
	public ConfigManager(@NotNull String... files)
	{
		this.plugin = MiniJoin.instance();
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
			
			Log.info("Created file: <green>'" + fileName + "' <yellow>as successful.");
		}
		
		final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		Log.info("Loaded file: <green>'" + fileName + "' <yellow>as successful.");
		
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
				Log.error(
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
				Log.error(
					 "Failed to save the file: <red>'" + fileName + "'.",
					 "Printing error..."
				);
				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * It gets the file configuration of the file with the given name
	 *
	 * @param fileName The name of the file to get.
	 * @return The file configuration of the file name.
	 */
	@Override
	public FileConfiguration get(@NotNull String fileName)
	{
		if (!this.configurationMap.containsKey(fileName))
		{
			Log.error("The file to get has not been found. Check the name.");
			return null;
		}
		return this.configurationMap.get(fileName);
	}
}
