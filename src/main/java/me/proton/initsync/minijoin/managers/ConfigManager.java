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
	private final MiniJoin plugin;
	private final Map<String, File> fileMap;
	private final Map<String, FileConfiguration> configurationMap;
	
	/**
	 * Class Constructor.
	 *
	 * @param plugin -> JavaPlugin instance required.
	 * @param files -> file or files to create and load white the plugin startup.
	 */
	public ConfigManager(@NotNull MiniJoin plugin, @NotNull String... files)
	{
		this.plugin = Objects.requireNonNull(plugin, "Plugin at the constructor is null.");
		this.fileMap = Maps.newHashMap();
		this.configurationMap = Maps.newHashMap();
		
		for (String file : files) this.load(file);
	}
	
	/**
	 * Create if not exists, or load the file specified.
	 *
	 * @param fileName -> name of file.
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
	 * Reload the file specified.
	 *
	 * @param fileName -> name of file.
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
	 * Save the file specified.
	 *
	 * @param fileName -> save of file.
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
	 * Returns the file specified, if the map not contains it (not exist), will return null.
	 *
	 * @param fileName -> name of file.
	 *
	 * @return -> a FileConfiguration or null.
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
