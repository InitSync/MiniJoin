package me.proton.initsync.minijoin.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	@Nullable
	public String quitMessage()
	{
		return this.quitMessage;
	}
	
	/**
	 * Sets the quit message to the given quit message.
	 *
	 * @param quitMessage The message to send to the player when they quit.
	 */
	public void quitMessage(@Nullable String quitMessage)
	{
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
