package me.proton.initsync.minijoin.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum Permission
{
	HELP_CMD ("command.help"),
	CONFIG_CMD ("command.config");
	
	// It's a variable, it's used to store a value.
	final String perm;
	
	// It's a constructor, it's used to create a new object of the class.
	Permission(@NotNull String perm)
	{
		this.perm = Objects.requireNonNull(perm, "Permission inserted is null.");
	}
	
	/**
	 * It returns the permission node for the command
	 *
	 * @return The permission node for the command.
	 */
	public String getPerm()
	{
		return "minijoin." + this.perm;
	}
}
