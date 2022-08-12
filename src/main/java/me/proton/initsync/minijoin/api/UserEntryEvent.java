package me.proton.initsync.minijoin.api;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class UserEntryEvent extends Event
{
	private final HandlerList handlers;
	private final Player user;
	
	private String joinMessage;
	
	/**
	 * Class Constructor.
	 *
	 * @param user -> player that join to server.
	 */
	public UserEntryEvent(@NotNull Player user)
	{
		this.handlers = new HandlerList();
		this.user = Objects.requireNonNull(user, "User at the constructor is null.");
	}
	
	/**
	 * Returns the user than join to server.
	 *
	 * @return -> a Player.
	 */
	public Player user()
	{
		return this.user;
	}
	
	/**
	 * Returns the join message of user.
	 *
	 * @return -> a String.
	 */
	public String joinMessage()
	{
		return this.joinMessage;
	}
	
	/**
	 * Set or modify the join message.
	 *
	 * @param joinMessage -> join message to set.
	 */
	public void joinMessage(@NotNull String joinMessage)
	{
		Validate.notEmpty(joinMessage, "Join message to set is null or has empty.");
		
		this.joinMessage = joinMessage;
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
