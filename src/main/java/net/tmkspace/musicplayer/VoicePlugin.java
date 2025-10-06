package net.tmkspace.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.VolumeCategory;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import lombok.Getter;

import static net.tmkspace.musicplayer.MusicPlayer.getInstance;
import static net.tmkspace.musicplayer.Util.getIconAsArray;

public class VoicePlugin implements VoicechatPlugin {

    public static final String MUSIC_DISC_CATEGORY = "music_discs";
    public static final String GOAT_HORN_CATEGORY = "goat_horns";

    public static VolumeCategory musicDisc;
    public static VolumeCategory goatHorn;

    private static @Getter VoicechatApi voicechatApi;
    private static @Getter VoicechatServerApi voicechatServerApi;

    private static @Getter AudioPlayerManager manager = new DefaultAudioPlayerManager();

    @Override
    public void initialize(VoicechatApi api) {
        voicechatApi = api;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        getInstance().getSLF4JLogger().info("Voicechat API initialized!");
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
    }


    public void onServerStarted(VoicechatServerStartedEvent event) {
        voicechatServerApi = event.getVoicechat();

        musicDisc = voicechatServerApi.volumeCategoryBuilder()
                .setId(MUSIC_DISC_CATEGORY)
                .setName("Music Discs") // cfg
                .setDescription("The volume of music discs") //cfg
                .setIcon(getIconAsArray("music_disc.png"))
                .build();
        voicechatServerApi.registerVolumeCategory(musicDisc);

        goatHorn = voicechatServerApi.volumeCategoryBuilder()
                .setId(GOAT_HORN_CATEGORY)
                .setName("Goat Horn") // cfg
                .setDescription("123") // cfg
                .setIcon(getIconAsArray("goat_horn.png"))
                .build();
        voicechatServerApi.registerVolumeCategory(goatHorn);
    }

    @Override
    public String getPluginId() {
        return "music_player";
    }
}
