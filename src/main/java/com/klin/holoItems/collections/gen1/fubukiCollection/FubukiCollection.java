package com.klin.holoItems.collections.gen1.fubukiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class FubukiCollection extends Collection {
    public static final String name = "Fubuki";
    public static final String desc = "";
    public static final String theme = "Damage dealt";
    public static final String ign = "shirakamifubuki";
    public static final String uuid = "703a3d75-8988-4d96-a24e-1f2cc83c5439";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDUwNzk2NywKICAicHJvZmlsZUlkIiA6ICI3MDNhM2Q3NTg5ODg0ZDk2YTI0ZTFmMmNjODNjNTQzOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGlyYWthbWlmdWJ1a2kiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVjOTc1ZGViMjhkZTMzNjQ3ODc5MDhmYjBmNjkzYjIwMGViMTZjNTBmZmQ3ZmNmNjJjZGJlMjM2NjViOGYzOSIKICAgIH0KICB9Cn0=";

    public static final char key = 'g';

    public FubukiCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}