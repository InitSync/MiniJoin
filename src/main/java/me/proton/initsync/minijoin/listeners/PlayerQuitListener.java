package me.proton.initsync.minijoin.listeners;

import me.proton.initsync.minijoin.MiniJoin;
import me.proton.initsync.minijoin.api.UserQuitEvent;
import me.proton.initsync.minijoin.enums.Configuration;
import me.proton.initsync.minijoin.enums.Paths;
import me.proton.initsync.minijoin.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlayerQuitListener implements Listener
{
	// A private variable that is used to store the plugin instance.
	private final MiniJoin plugin;
	
	// Used to store the UserQuitEvent instance.
	private UserQuitEvent userQuitEvent;
	
	// Checking if the plugin is null.
	public PlayerQuitListener(@NotNull MiniJoin plugin)
	{
		this.plugin = Objects.requireNonNull(plugin, "Plugin is null at the constructor.");
	}
	
	/**
	 * It checks if the join-quit feature is enabled, if it is, it gets the user's group, and then it gets the
	 * quit message from the configuration, and then it sets the quit message to the event
	 *
	 * @param event The event that is being called.
	 */
	@EventHandler (priority = EventPriority.HIGH)
	public void onQuit(PlayerQuitEvent event)
	{
		final Player player = event.getPlayer();
		
		if (Configuration.check(Paths.JOIN_QUIT_ALLOW))
		{
			this.userQuitEvent = new UserQuitEvent();
			
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
				final String quitMessage = this.plugin
					 .configManager()
					 .get("config.yml")
					 .getString("config.join-quit.groups." + userGroup + ".quit");
				assert quitMessage != null;
				
				this.userQuitEvent.quitMessage(quitMessage);
				event.quitMessage(Utils.miniMessage(player,
					 this.userQuitEvent.quitMessage()
				));
			}
		}
	}
}
