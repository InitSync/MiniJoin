package me.proton.initsync.minijoin.api;

import org.apache.commons.lang.Validate;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UserQuitEvent extends Event
{
	// It's a variable that will be used to store the quit message.
	private final HandlerList handlers;
	// It's a variable that will be used to store the quit message.
	private String quitMessage;
	
	// It's a constructor that will be used to create a new instance of the class.
	public UserQuitEvent()
	{
		this.handlers = new HandlerList();
	}
	
	/**
	 * This function returns the quit message.
	 *
	 * @return The quitMessage variable is being returned.
	 */
	public String quitMessage()
	{
		return this.quitMessage;
	}
	
	/**
	 * Sets the quit message to the given quit message.
	 *
	 * @param quitMessage The message to send to the player when they quit.
	 */
	public void quitMessage(@NotNull String quitMessage)
	{
		Validate.notEmpty(quitMessage, "Quit message to set is null or has empty.");
		
		this.quitMessage = quitMessage;
	}
	
	/**
	 * Returns a list of event handlers, used to register the event.
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
