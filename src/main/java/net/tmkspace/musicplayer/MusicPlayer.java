package net.tmkspace.musicplayer;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import github.scarsz.configuralize.DynamicConfig;
import github.scarsz.configuralize.ParseException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class MusicPlayer extends JavaPlugin {

    private final DynamicConfig config;

    @Override
    public void onEnable() {
        BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        if (service != null) {
            service.registerPlugin(new MusicPlayerVoicePlugin());
        }

    }

    @Override
    public void onDisable() {

    }

    public static MusicPlayer getPlugin() {
        return getPlugin(MusicPlayer.class);
    }

    public static DynamicConfig config() {
        return getPlugin().config;
    }

    public void reloadConfig() {
        try {
            config().loadAll();
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to load config: ", e);
        }
    }

    public MusicPlayer() {
        super();

        getDataFolder().mkdirs();
        config = new DynamicConfig();
    }
}
