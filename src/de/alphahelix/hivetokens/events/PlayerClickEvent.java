package de.alphahelix.hivetokens.events;

import de.alphahelix.hivetokens.instances.FakePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by AlphaHelixDev.
 */
public class PlayerClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private FakePlayer fakePlayer;

    public PlayerClickEvent(Player player ,FakePlayer fakePlayer) {
        this.player = player;
        this.fakePlayer = fakePlayer;
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

    public FakePlayer getFakePlayer() {
        return fakePlayer;
    }

}
