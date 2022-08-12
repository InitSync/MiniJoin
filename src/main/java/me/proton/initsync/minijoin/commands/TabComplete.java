package me.proton.initsync.minijoin.commands;

import com.google.common.collect.Lists;
import me.proton.initsync.minijoin.enums.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter
{
	private final List<String> list;
	
	/* Class Constructor. */
	public TabComplete()
	{
		this.list = Lists.newArrayList();
	}
	
	/**
	 * Provides an argument completer for the commands.
	 *
	 * @param sender -> Source of the command. For players tab-completing a
	 *     command inside of a command block, this will be the player, not
	 *     the command block.
	 * @param command -> Command which was executed
	 * @param label -> Alias of the command which was used
	 * @param args -> The arguments passed to the command, including final partial argument to be
	 *              completed.
	 *
	 * @return -> a value or null.
	 */
	@Override
	public @Nullable List<String> onTabComplete(
		 @NotNull CommandSender sender,
		 @NotNull Command command,
		 @NotNull String label,
		 @NotNull String[] args
	)
	{
		if (args.length == 0)
		{
			if (sender.hasPermission(Permission.HELP_CMD.getPerm()))
			{
				this.list.add("help");
				return this.list;
			}
			
			if (sender.hasPermission(Permission.CONFIG_CMD.getPerm()))
			{
				this.list.add("config");
				return this.list;
			}
			
			return Collections.emptyList();
		}
		
		return null;
	}
}
