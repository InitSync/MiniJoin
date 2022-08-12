package me.proton.initsync.minijoin.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum Permission
{
	HELP_CMD ("command.help"),
	CONFIG_CMD ("command.config");
	
	final String perm;
	
	/**
	 * Class Constructor.
	 *
	 * @param perm -> permission string.
	 */
	Permission(@NotNull String perm)
	{
		this.perm = Objects.requireNonNull(perm, "Permission inserted is null.");
	}
	
	/**
	 * Return the permission specified, example: Permission.CONFIG_CMD.getPerm()
	 *
	 * @return -> permission specified.
	 */
	public String getPerm()
	{
		return "minijoin." + this.perm;
	}
}
