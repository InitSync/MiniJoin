package me.proton.initsync.minijoin.commands;

import com.google.common.collect.Lists;
import me.proton.initsync.minijoin.enums.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TabComplete implements TabCompleter
{
	private final List<String> list;
	private final List<String> result;
	
	// Creating a new list.
	public TabComplete()
	{
		this.list = Lists.newArrayList();
		this.result = Lists.newArrayList();
	}
	
	/**
	 * If the player has permission to use the command, add it to the list of possible tab completions
	 *
	 * @param sender The CommandSender who executed the command.
	 * @param command The command that was executed.
	 * @param label The command label.
	 * @param args The arguments that were passed to the command.
	 * @return A list of strings.
	 */
	@Override
	@Nullable
	public List<String> onTabComplete(
		 @NotNull CommandSender sender,
		 @NotNull Command command,
		 @NotNull String label,
		 @NotNull String[] args
	)
	{
		if (this.list.isEmpty())
		{
			this.list.add("help");
			this.list.add("config");
		}
		
		if (args.length == 0)
		{
			if (sender.hasPermission(Permission.HELP_CMD.getPerm()) ||
				 sender.hasPermission(Permission.CONFIG_CMD.getPerm()))
			{
				for (String a : list)
				{
					if (a.toLowerCase()
						 .startsWith(args[0].toLowerCase()))
					{
						this.result.add(a);
					}
					return this.result;
				}
				return null;
			}
			return null;
		}
		return null;
	}
}
