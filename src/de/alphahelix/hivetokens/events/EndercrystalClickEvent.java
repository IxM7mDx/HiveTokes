package de.alphahelix.hivetokens.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by AlphaHelixDev.
 */
public class EndercrystalClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;

    public EndercrystalClickEvent(Player player) {
        this.player = player;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    public final static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    @Override
    public final HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }
}
