package me.proton.initsync.minijoin.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
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

import java.util.List;

public class PlayerJoinListener implements Listener
{
	// It's a variable that is used to get the instance of the plugin.
	private final MiniJoin plugin;
	
	// It's a constructor.
	public PlayerJoinListener()
	{
		this.plugin = MiniJoin.instance();
	}
	
	/**
	 * > When a player joins the server, the plugin checks if the join/quit messages are enabled, if they
	 * are, it checks if the MOTD is enabled, if it is, it sends the MOTD to the player, then it checks if
	 * the player's group is in the configuration, if it is, it sends the join message to the player and
	 * executes the actions
	 *
	 * @param event The event that is being called.
	 */
	@EventHandler (priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		
		if (Configuration.check(Paths.JOIN_QUIT_ALLOW))
		{
			final UserEntryEvent userEntryEvent = new UserEntryEvent();
			
			if (Configuration.check(Paths.JOIN_QUIT_ALLOW_MOTD))
			{
				Configuration.getList(Paths.JOIN_QUIT_MOTD)
					 .forEach(string ->
					 {
						 player.sendMessage(Utils.miniMessage(player,
							  PlaceholderAPI.setPlaceholders(player, string)
						 ));
					 });
			}
			
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
				
				userEntryEvent.joinMessage(joinMessage);
				event.joinMessage(Utils.miniMessage(player,
					 PlaceholderAPI.setPlaceholders(player,
						  userEntryEvent.joinMessage()
					 )
				));
				
				final List<String> actions = this.plugin
					 .configManager()
					 .get("config.yml")
					 .getStringList("config.join-quit.groups." + userGroup + ".join-actions");
				actions.forEach(action ->
				{
					this.plugin
						 .actionHandler()
						 .execute(player, action);
				});
			}
		}
	}
}
