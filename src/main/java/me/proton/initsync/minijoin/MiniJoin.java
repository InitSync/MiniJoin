package me.proton.initsync.minijoin;

import me.proton.initsync.minijoin.commands.MainCommand;
import me.proton.initsync.minijoin.commands.TabComplete;
import me.proton.initsync.minijoin.listeners.PlayerJoinListener;
import me.proton.initsync.minijoin.listeners.PlayerQuitListener;
import me.proton.initsync.minijoin.managers.ConfigManager;
import me.proton.initsync.minijoin.utils.Log;
import me.proton.initsync.minijoin.utils.actions.ActionHandler;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class MiniJoin extends JavaPlugin
{
	// It's creating a new variable called `instance` and it's private.
	private static MiniJoin instance;
	
	// It's getting the author of the plugin from the plugin.yml file.
	public final String author = String.join("",
		 this.getDescription().getAuthors()
	);
	// It's getting the version of the plugin from the plugin.yml file.
	public final String version = this.getDescription().getVersion();
	
	// It's creating a new variable called `actionHandler` and it's private.
	private ActionHandler actionHandler;
	// It's creating a new variable called `luckPerms` and it's private.
	private LuckPerms luckPerms;
	// It's creating a new variable called `configManager` and it's private.
	private ConfigManager configManager;
	
	/**
	 * If the instance is null, throw an exception, otherwise return the instance.
	 *
	 * @return The instance of the plugin.
	 */
	public static MiniJoin instance()
	{
		if (instance == null)
		{
			throw new IllegalStateException("Tried to access instance when the plugin was disabled!");
		}
		return instance;
	}
	
	/**
	 * If the plugin is disabled, throw an exception. Otherwise, return the plugin.
	 *
	 * @return LuckPerms
	 */
	public LuckPerms luckPerms()
	{
		if (this.luckPerms == null)
		{
			throw new IllegalStateException("Tried to access LuckPerms when the plugin was disabled!");
		}
		return this.luckPerms;
	}
	
	/**
	 * If the configManager is null, throw an exception. Otherwise, return the configManager
	 *
	 * @return The configManager object.
	 */
	public ConfigManager configManager()
	{
		if (this.configManager == null)
		{
			throw new IllegalStateException("Tried to access ConfigManager when the plugin was disabled!");
		}
		return this.configManager;
	}
	
	/**
	 * If the action handler is null, throw an exception. Otherwise, return the action handler
	 *
	 * @return The actionHandler
	 */
	public ActionHandler actionHandler()
	{
		if (this.actionHandler == null)
		{
			throw new IllegalStateException("Tried to access ActionHandler when the plugin was disabled!");
		}
		return this.actionHandler;
	}
	
	@Override
	public void onEnable()
	{
		// Plugin startup logic.
		
		instance = this;
		
		RegisteredServiceProvider<LuckPerms> provider = this.getServer()
			 .getServicesManager()
			 .getRegistration(LuckPerms.class);
		if (provider != null)
		{
			this.luckPerms = provider.getProvider();
			
			Log.info("Loaded LuckPerms Dependency as successful.");
		}
		
		this.configManager = new ConfigManager("config.yml");
		this.actionHandler = new ActionHandler();
		
		Log.info("Loaded ActionHandler as successful.");
		
		final PluginCommand command = this.getCommand("minijoin");
		assert command != null;
		command.setExecutor(new MainCommand());
		command.setTabCompleter(new TabComplete());
		
		registerListeners(
			 new PlayerJoinListener(),
			 new PlayerQuitListener()
		);
		
		Log.info(
			 "Loaded plugin as successful, running at <dark_gray>(<aqua>" + Bukkit.getMinecraftVersion() +
			 "<dark_gray>)",
			 "<white>Developed by <green>" + this.author + " <dark_gray>| <aqua>v" + this.version
		);
	}
	
	/**
	 * It registers a list of listeners to the plugin
	 */
	private void registerListeners(@NotNull Listener... listeners)
	{
		Objects.requireNonNull(listeners, "Listeners to register is null.");
		
		for (Listener listener : listeners)
		{
			this.getServer()
				 .getPluginManager()
				 .registerEvents(listener, this);
		}
	}
	
	@Override
	public void onDisable()
	{
		// Plugin shutdown logic.
		
		if (this.actionHandler != null) this.actionHandler = null;
		if (this.configManager != null) this.configManager = null;
		if (this.luckPerms != null) this.luckPerms = null;
		
		Log.info(
			 "Unloaded plugin as successful",
			 "<white>Developed by <green>" + this.author + " <dark_gray>| <aqua>v" + this.version
		);
		
		if (instance != null) instance = null;
	}
}
