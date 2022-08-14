package me.proton.initsync.minijoin.listeners;

import me.proton.initsync.minijoin.MiniJoin;
import me.proton.initsync.minijoin.api.UserEntryEvent;
import me.proton.initsync.minijoin.enums.Configuration;
import me.proton.initsync.minijoin.enums.Paths;
import me.proton.initsync.minijoin.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PlayerJoinListener implements Listener
{
	// A reference to the main class.
	private final MiniJoin plugin;
	
	// A reference to the `UserEntryEvent` class.
	private UserEntryEvent userEntryEvent;
	// It's a list of actions that will be executed when the player joins.
	private List<String> actions;
	
	// It's a constructor that is used to initialize the `plugin` variable.
	public PlayerJoinListener(@NotNull MiniJoin plugin)
	{
		this.plugin = Objects.requireNonNull(plugin, "Plugin is null at the constructor.");
	}
	
	/**
	 * It checks if the join-quit feature is enabled, then it gets the user's primary group, then it gets
	 * the join message from the configuration, then it sets the join message, then it executes the join
	 * actions
	 *
	 * @param event The event that is being called.
	 */
	@EventHandler (priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		
		if (Configuration.check(Paths.JOIN_QUIT_ALLOW))
		{
			this.userEntryEvent = new UserEntryEvent();
			
			final String userGroup = this.plugin
				 .luckPerms()
				 .getUserManager()
				 .getUser(player.getUniqueId())
				 .getPrimaryGroup();
			if (this.plugin
				 .configManager()
				 .get("config.yml")
				 .getConfigurationSection("config.join-quit.groups." + userGroup) == null)
			{
				throw new NullPointerException("Join Group is null at the configuration.");
			}
			else
			{
				final String joinMessage = this.plugin
					 .configManager()
					 .get("config.yml")
					 .getString("config.join-quit.groups." + userGroup + ".join");
				assert joinMessage != null;
				
				this.userEntryEvent.joinMessage(joinMessage);
				event.joinMessage(Utils.miniMessage(player,
					 this.userEntryEvent.joinMessage()
				));
				
				this.actions = this.plugin
					 .configManager()
					 .get("config.yml")
					 .getStringList("config.join-quit.groups." + userGroup + ".join-actions");
				this.actions
					 .forEach(action ->
					 {
						 this.plugin
								.actionHandler()
								.execute(player, action);
					 });
			}
		}
	}
}
