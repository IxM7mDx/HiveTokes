package de.alphahelix.hivetokens.events;

import de.alphahelix.hivetokens.instances.FakeEndercrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by AlphaHelixDev.
 */
public class EndercrystalClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private FakeEndercrystal fakeEndercrystal;

    public EndercrystalClickEvent(Player player ,FakeEndercrystal fakeEndercrystal) {
        this.player = player;
        this.fakeEndercrystal = fakeEndercrystal;
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

    public FakeEndercrystal getFakeEndercrystal() {
        return fakeEndercrystal;
    }
}
