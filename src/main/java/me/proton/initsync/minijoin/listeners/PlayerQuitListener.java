package me.proton.initsync.minijoin.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.proton.initsync.minijoin.MiniJoin;
import me.proton.initsync.minijoin.api.UserQuitEvent;
import me.proton.initsync.minijoin.enums.Configuration;
import me.proton.initsync.minijoin.enums.Paths;
import me.proton.initsync.minijoin.utils.Utils;
import me.proton.initsync.minijoin.utils.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener
{
	// It's creating a new instance of the MiniJoin class.
	private final MiniJoin plugin;
	
	// It's a constructor.
	public PlayerQuitListener()
	{
		this.plugin = MiniJoin.instance();
	}
	
	/**
	 * It checks if the join-quit feature is enabled, if it is, it gets the user's group, and then it gets the
	 * quit message from the configuration, and then it sets the quit message to the event
	 *
	 * @param event The event that is being called.
	 */
	@EventHandler (priority = EventPriority.LOW)
	public void onQuit(PlayerQuitEvent event)
	{
		final Player player = event.getPlayer();
		
		if (Configuration.check(Paths.JOIN_QUIT_ALLOW))
		{
			final UserQuitEvent userQuitEvent = new UserQuitEvent();
			
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
				Log.error("Join Group is null at the configuration.");
			}
			else
			{
				final String quitMessage = this.plugin
					 .configManager()
					 .get("config.yml")
					 .getString("config.join-quit.groups." + userGroup + ".quit");
				assert quitMessage != null;
				
				userQuitEvent.quitMessage(quitMessage);
				event.quitMessage(Utils.miniMessage(player,
					 PlaceholderAPI.setPlaceholders(player,
						  userQuitEvent.quitMessage()
					 )
				));
			}
		}
	}
}
