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
	JOIN_QUIT_GROUPS ("config.join-quit.groups"),
	
	MESSAGE_NO_PERM ("messages.no-perm"),
	MESSAGE_NO_COMMAND ("messages.no-command"),
	
	MESSAGE_HELP ("messages.help"),
	
	MESSAGE_CONFIG ("messages.config");
	
	final String pathString;
	
	/**
	 * Class Constructor.
	 *
	 * @param pathString -> path string.
	 */
	Paths(@NotNull String pathString)
	{
		this.pathString = Objects.requireNonNull(pathString, "Path inserted is null.");
	}
	
	/**
	 * Returns the path string.
	 *
	 * @return -> string of path.
	 */
	public String pathString()
	{
		return this.pathString;
	}
}
