package me.proton.initsync.minijoin.commands;

import me.proton.initsync.minijoin.MiniJoin;
import me.proton.initsync.minijoin.enums.Configuration;
import me.proton.initsync.minijoin.enums.Paths;
import me.proton.initsync.minijoin.enums.Permission;
import me.proton.initsync.minijoin.utils.Utils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MainCommand implements CommandExecutor
{
	private final MiniJoin plugin;
	private final String prefix;
	private final Sound permSound;
	private final Sound reloadSound;
	
	/**
	 * Class Constructor.
	 *
	 * @param plugin -> JavaPlugin instance required.
	 */
	public MainCommand(@NotNull MiniJoin plugin)
	{
		this.plugin = Objects.requireNonNull(plugin, "Plugin at the constructor is null.");
		this.prefix = Configuration.getString(Paths.PREFIX);
		this.permSound = Sound.valueOf(Configuration.getString(
			 Paths.SOUNDS_NO_PERM
		));
		this.reloadSound = Sound.valueOf(Configuration.getString(
			 Paths.SOUNDS_RELOAD
		));
	}
	
	/**
	 * Call to this method when a command is executed.
	 *
	 * @param sender -> Source of the command
	 * @param command -> Command which was executed
	 * @param label -> Alias of the command which was used
	 * @param args -> Passed command arguments
	 *
	 * @return -> a boolean value depending on execution result.
	 */
	@Override
	public boolean onCommand(
		 @NotNull CommandSender sender,
		 @NotNull Command command,
		 @NotNull String label,
		 @NotNull String[] args
	)
	{
		if (!(sender instanceof Player))
		{
			if (args.length == 0)
			{
				Utils.message(sender,
					 this.prefix + "Running at <dark_gray>(<aqua>" + Bukkit.getMinecraftVersion() +
							"<dark_gray>)",
					 this.prefix + "<white>Developed by <green>" + this.plugin.author + " <dark_gray>| "
							+ "<aqua>v" + this.plugin.version
				);
				return true;
			}
			
			switch (args[0])
			{
				case "help" -> {
					if (sender.hasPermission(Permission.HELP_CMD.getPerm()))
					{
						helpMessage(sender);
						return true;
					}
					
					sender.sendMessage(MiniMessage.miniMessage()
						 .deserialize(
								Configuration.getString(
									 Paths.MESSAGE_NO_PERM
								),
							  Placeholder.parsed("prefix", this.prefix)
						 ));
					return false;
				}
				case "config" -> {
					if (sender.hasPermission(Permission.CONFIG_CMD.getPerm()))
					{
						this.plugin
							 .configManager()
							 .reload("config.yml");
						
						sender.sendMessage(MiniMessage.miniMessage()
							 .deserialize(
								  Configuration.getString(
										 Paths.MESSAGE_CONFIG
								  ),
								  Placeholder.parsed("prefix", this.prefix)
							 ));
						return true;
					}
					
					sender.sendMessage(MiniMessage.miniMessage()
						 .deserialize(
								Configuration.getString(
									 Paths.MESSAGE_NO_PERM
								),
							  Placeholder.parsed("prefix", this.prefix)
						 ));
					return false;
				}
			}
			
			sender.sendMessage(MiniMessage.miniMessage()
				 .deserialize(
						Configuration.getString(
							 Paths.MESSAGE_NO_COMMAND
						),
						Placeholder.parsed("prefix", this.prefix)
				 ));
			return true;
		}
		
		final Player player = (Player) sender;
		
		if (args.length == 0)
		{
			Utils.message(player,
				 this.prefix + " <white>Running at <dark_gray>(<aqua>" + Bukkit.getMinecraftVersion() +
					  "<dark_gray>)",
				 this.prefix + " <white>Developed by <green>" + this.plugin.author + " <dark_gray>| "
					  + "<aqua>v" + this.plugin.version
			);
			return true;
		}
		
		switch (args[0])
		{
			case "help" ->
			{
				if (player.hasPermission(Permission.HELP_CMD.getPerm()))
				{
					helpMessage(player);
					return true;
				}
				
				if (Configuration.check(Paths.SOUNDS_ALLOW))
				{
					this.plugin
						 .actionHandler()
						 .sound(player, "[sound] " + permSound + ";1;1");
				}
				
				player.sendMessage(Utils.miniMessage(player,
					 Configuration.getString(
							Paths.MESSAGE_NO_PERM
					 )
				));
				return false;
			}
			case "config" ->
			{
				if (player.hasPermission(Permission.CONFIG_CMD.getPerm()))
				{
					this.plugin
						 .configManager()
						 .reload("config.yml");
					
					if (Configuration.check(Paths.SOUNDS_ALLOW))
					{
						this.plugin
							 .actionHandler()
							 .sound(player, "[sound] " + reloadSound + ";1;1");
					}
					
					player.sendMessage(Utils.miniMessage(player,
						 Configuration.getString(
								Paths.MESSAGE_CONFIG
						 )
					));
					return true;
				}
				
				if (Configuration.check(Paths.SOUNDS_ALLOW))
				{
					this.plugin
						 .actionHandler()
						 .sound(player, "[sound] " + permSound + ";1;1");
				}
				
				player.sendMessage(Utils.miniMessage(player,
					 Configuration.getString(
							Paths.MESSAGE_NO_PERM
					 )
				));
				return false;
			}
		}
		
		player.sendMessage(Utils.miniMessage(player,
			 Configuration.getString(
					Paths.MESSAGE_NO_COMMAND
			 )
		));
		return false;
	}
	
	/**
	 * Sends the Plugin Help Message to sender.
	 *
	 * @param sender -> sender of command.
	 */
	private void helpMessage(@NotNull CommandSender sender)
	{
		Objects.requireNonNull(sender, "Sender is null.");
		
		final List<String> list = Configuration.getList(Paths.MESSAGE_HELP);
		list.forEach(string -> {
			sender.sendMessage(MiniMessage.miniMessage()
				 .deserialize(string,
						Placeholder.parsed("prefix", this.prefix)
				 )
			);
		});
	}
}
