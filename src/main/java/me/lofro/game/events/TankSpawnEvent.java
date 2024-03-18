package me.lofro.game.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

@Getter
public class TankSpawnEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player controller;

    public TankSpawnEvent(Player controller) {
        this.controller = controller;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
