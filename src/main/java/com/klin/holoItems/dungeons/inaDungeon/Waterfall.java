package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class Waterfall implements Listener, Resetable {
    //waterfall buildteam 713 54 345
    //y:61 -> 721 70 361
    //waterfall world -6 60 -285
    private final Location center;
    private final List<Block> pond;
    private final Set<Block> rapids;

    public Waterfall(World world, int x, int y, int z){
        center = new Location(world, x, y, z);
        pond = Utility.vacuum(center.getBlock(), Material.WATER, 3000, true);
        rapids = new HashSet<>();
        for(int i=0; i<=8; i++){
            for(int j=7; j<=16; j++){
                for(int k=0; k<=16; k++){
                    Block block = world.getBlockAt(x+i, y+j, z+k);
                    if(block.getType()==Material.COBWEB) {
                        block.setType(Material.AIR);
                        rapids.add(block);
                    }
                }
            }
        }
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
    }

    @EventHandler
    public void fill(WeatherChangeEvent event){
        new BukkitRunnable(){
            public void run(){
                if(event.getWorld().hasStorm())
                    reset();
            }
        }.runTask(HoloItems.getInstance());
    }

    public void reset(){
        WeatherChangeEvent.getHandlerList().unregister(this);
        for(Block block : pond)
            block.setType(Material.WATER);
        Block block = center.getBlock();
        new Task(HoloItems.getInstance(), 20, 20){
            int increment = 0;
            public void run(){
                if(increment>=20 || block.getType()==Material.WATER){
                    new Task(HoloItems.getInstance(), 0, 2){
                        int y = block.getY()+7;
                        final int increment = y+16;
                        public void run(){
                            if(y>increment || rapids.isEmpty()){
                                for(Block block : rapids)
                                    block.setType(Material.COBWEB);
                                rapids.clear();
                                cancel();
                                return;
                            }
                            Set<Block> remove = new HashSet<>();
                            for(Block block : rapids) {
                                if(y==block.getY()) {
                                    block.setType(Material.COBWEB);
                                    remove.add(block);
                                }
                            }
                            rapids.removeAll(remove);
                            y++;
                        }
                    };
                    cancel();
                    return;
                }
                increment++;
            }
        };
    }
}
