package me.proton.initsync.minijoin.api;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class UserQuitEvent extends Event
{
	private final HandlerList handlers;
	private final Player user;
	
	private String quitMessage;
	
	/**
	 * Class Constructor.
	 *
	 * @param user -> player that leave the server.
	 */
	public UserQuitEvent(@NotNull Player user)
	{
		this.handlers = new HandlerList();
		this.user = Objects.requireNonNull(user, "User at the constructor is null.");
	}
	
	/**
	 * Returns the user than leave the server.
	 *
	 * @return -> a Player.
	 */
	public Player user()
	{
		return this.user;
	}
	
	/**
	 * Returns the quit message of user.
	 *
	 * @return -> a String.
	 */
	public String quitMessage()
	{
		return this.quitMessage;
	}
	
	/**
	 * Set or modify the quit message.
	 *
	 * @param quitMessage -> quit message to set.
	 */
	public void quitMessage(@NotNull String quitMessage)
	{
		Validate.notEmpty(quitMessage, "Quit message to set is null or has empty.");
		
		this.quitMessage = quitMessage;
	}
	
	/**
	 * Returns the handlers list.
	 *
	 * @return -> a HandlerList.
	 */
	@Override
	@NotNull
	public HandlerList getHandlers()
	{
		return this.handlers;
	}
}
