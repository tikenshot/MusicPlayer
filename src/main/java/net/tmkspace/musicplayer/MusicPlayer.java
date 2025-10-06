package net.tmkspace.musicplayer;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import github.scarsz.configuralize.DynamicConfig;
import github.scarsz.configuralize.ParseException;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;

import net.tmkspace.musicplayer.command.MusicPlayerCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.List;

public final class MusicPlayer extends JavaPlugin {

    private final DynamicConfig config;
    @Nullable
    private VoicePlugin voicePlugin;
    private final Logger logger;
    private final @Getter File configFile = new File(getDataFolder(), "config.yml");
    private final @Getter File musicDir = new File(getDataFolder(), "music");

    @Override
    public void onEnable() {
        BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        if (service != null) {
            voicePlugin = new VoicePlugin();
            service.registerPlugin(voicePlugin);
            logger.info("Successfully registered voice plugin!");
        } else {
            logger.info("сервис нулл");
            logger.info("Failed to register voice plugin!");
        }

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(MusicPlayerCommand.createCommand("musicplayer"), "music player command", List.of("mp"));
        });
    }

    @Override
    public void onDisable() {
        if (voicePlugin != null) {
            getServer().getServicesManager().unregister(voicePlugin);
            logger.info("Successfully unregistered voice plugin!");
        }
    }

    public static MusicPlayer getInstance() {
        return getPlugin(MusicPlayer.class);
    }

    public static DynamicConfig config() {
        return getInstance().config;
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

        logger = getSLF4JLogger();
        getDataFolder().mkdirs();
        getMusicDir().mkdirs();
        config = new DynamicConfig();
        config.addSource(MusicPlayer.class, "config", getConfigFile());
        try {
            config.saveAllDefaults();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save default config file ", e);
        }
        try {
            config.loadAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config, ", e);
        }
    }
}
