package me.lofro.utils.runnable;

import lombok.Setter;
import org.bukkit.Bukkit;

public abstract class CancellableBukkitRunnable implements Runnable {

    private @Setter int taskID = 0;

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
