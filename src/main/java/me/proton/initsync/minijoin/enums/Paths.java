package me.proton.initsync.minijoin.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum Paths
{
  PREFIX ("config.prefix"),
	
	SOUNDS_ALLOW ("config.sounds.allow"),
	SOUNDS_NO_PERM ("config.sounds.no-perm"),
	SOUNDS_RELOAD ("config.sounds.reload"),
	
	JOIN_QUIT_ALLOW ("config.join-quit.allow"),
	JOIN_QUIT_ALLOW_MOTD ("config.join-quit.allow-motd"),
	JOIN_QUIT_MOTD ("config.join-quit.motd"),
	JOIN_QUIT_GROUPS ("config.join-quit.groups"),
	
	MESSAGE_NO_PERM ("messages.no-perm"),
	MESSAGE_NO_COMMAND ("messages.no-command"),
	
	MESSAGE_HELP ("messages.help"),
	
	MESSAGE_CONFIG ("messages.config");
	
	// Declaring a variable that is final, meaning it cannot be changed.
	final String pathString;
	
	// A constructor that is setting the pathString variable to the pathString parameter.
	Paths(@NotNull String pathString)
	{
		this.pathString = Objects.requireNonNull(pathString, "Path inserted is null.");
	}
	
	/**
	 * `pathString()` returns the path string of the current directory
	 *
	 * @return The pathString variable is being returned.
	 */
	public String pathString()
	{
		return this.pathString;
	}
}
