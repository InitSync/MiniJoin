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
	private final MiniJoin plugin;
	
	private UserEntryEvent userEntryEvent;
	private List<String> actions;
	
	/**
	 * Class Constructor.
	 *
	 * @param plugin -> JavaPlugin instance required.
	 */
	public PlayerJoinListener(@NotNull MiniJoin plugin)
	{
		this.plugin = Objects.requireNonNull(plugin, "Plugin is null at the constructor.");
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		
		if (Configuration.check(Paths.JOIN_QUIT_ALLOW))
		{
			this.userEntryEvent = new UserEntryEvent(player);
			
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
