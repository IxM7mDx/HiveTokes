package de.alphahelix.hivetokens.instances;

import org.bukkit.Location;

/**
 * Created by AlphaHelixDev.
 */
class FakeEntity {

    private Location startLocation;
    private Location currentlocation;
    private String name;
    private Object fake;

    public FakeEntity(Location location, String name, Object fake) {
        this.startLocation = location;
        this.currentlocation = location;
        this.name = name;
        this.fake = fake;
    }

    public Location getCurrentlocation() {
        return currentlocation;
    }

    public void setCurrentlocation(Location currentlocation) {
        this.currentlocation = currentlocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getFake() {
        return fake;
    }

    public void setFake(Object fake) {
        this.fake = fake;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    @Override
    public String toString() {
        return "Name = (" + getName() + ") , CurrentLocation = (x[" + getCurrentlocation().getBlockX() + "] ; y[" + getCurrentlocation().getBlockY() + "] ; z[" + getCurrentlocation().getBlockZ() + "]) , StartLocation = (x[" + getStartLocation().getBlockX() + "] ; y[" + getStartLocation().getBlockY() + "] ; z[" + getStartLocation().getBlockZ() + "])";
    }
}
