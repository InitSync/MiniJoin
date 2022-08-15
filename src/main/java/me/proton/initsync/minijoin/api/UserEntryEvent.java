package me.proton.initsync.minijoin.api;

import org.apache.commons.lang.Validate;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;

public class UserEntryEvent extends Event
{
	// It's a list of handlers that will be called when the event is called.
	private final HandlerList handlers;
	
	// It's a variable that will be used to store the join message.
	private String joinMessage;
	
	// It's a constructor of the class.
	public UserEntryEvent()
	{
		this.handlers = new HandlerList();
	}
	
	/**
	 * It returns the join message
	 *
	 * @return The joinMessage variable.
	 */
	public String joinMessage()
	{
		return this.joinMessage;
	}
	
	/**
	 * It sets the join message
	 *
	 * @param joinMessage The message to send to the player when they join.
	 */
	public void joinMessage(@Nullable String joinMessage)
	{
		this.joinMessage = joinMessage;
	}
	
	/**
	 * Returns a list of event handlers, every event handler is a method that handles a certain event.
	 *
	 * @return The HandlerList for this event.
	 */
	@Override
	@NotNull
	public HandlerList getHandlers()
	{
		return this.handlers;
	}
}
