package me.proton.initsync.minijoin;

import me.proton.initsync.minijoin.commands.MainCommand;
import me.proton.initsync.minijoin.commands.TabComplete;
import me.proton.initsync.minijoin.managers.ConfigManager;
import me.proton.initsync.minijoin.utils.Log;
import me.proton.initsync.minijoin.utils.actions.ActionHandler;
import net.luckperms.api.LuckPerms;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiniJoin extends JavaPlugin
{
	public final String author = String.join("",
		 this.getDescription().getAuthors()
	);
	public final String version = this.getDescription().getVersion();
	
	private ActionHandler actionHandler;
	private LuckPerms luckPerms;
	private ConfigManager configManager;
	
	/**
	 * Returns the LuckPerms instance, will throw a IllegalStateException if the instance is null.
	 *
	 * @return -> this.luckPerms
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
	 * Returns the ConfigManager instance, will throw a IllegalStateException if the instance is null.
	 *
	 * @return -> this.configManager
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
	 * Returns the ActionHandler instance, will throw a IllegalStateException if the instance is null.
	 *
	 * @return -> this.actionHandler
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
		
		RegisteredServiceProvider<LuckPerms> provider = this.getServer()
			 .getServicesManager()
			 .getRegistration(LuckPerms.class);
		if (provider != null)
		{
			this.luckPerms = provider.getProvider();
			
			Log.info(this, "Loaded LuckPerms Dependency as successful.");
		}
		
		this.configManager = new ConfigManager(this, "config.yml");
		this.actionHandler = new ActionHandler(this);
		
		final PluginCommand command = this.getCommand("minijoin");
		command.setExecutor(new MainCommand(this));
		command.setTabCompleter(new TabComplete());
		
		Log.info(this,
			 "Loaded ActionHandler as successful.",
			 "Loaded plugin as successful, running at <dark_gray>(<aqua>" + this.getServer().getMinecraftVersion() +
			 "<dark_gray>)",
			 "<white>Developed by <green>" + this.author + " <dark_gray>| <aqua>v" + this.version
		);
	}
	
	@Override
	public void onDisable()
	{
		// Plugin shutdown logic.
		
		if (this.actionHandler != null) this.actionHandler = null;
		if (this.configManager != null) this.configManager = null;
		if (this.luckPerms != null) this.luckPerms = null;
		
		Log.info(this,
			 "Unloaded plugin as successful",
			 "<white>Developed by <green>" + this.author + " <dark_gray>| <aqua>v" + this.version
		);
	}
}
