package me.daoge.forcecape;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.player.PlayerJoinEvent;
import org.allaymc.api.network.ProtocolInfo;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;
import org.cloudburstmc.protocol.bedrock.data.skin.ImageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
public final class ForceCape extends Plugin {

    private static final String CAPE_FILE_NAME = "cape.png";
    private BufferedImage capeImage;

    @Override
    @SneakyThrows
    public void onEnable() {
        var path = this.getPluginContainer().dataFolder().resolve(CAPE_FILE_NAME);
        if (!Files.exists(path)) {
            log.error("Cape file '{}' does not exist in plugin data folder. Please add a cape image to enable the feature.", CAPE_FILE_NAME);
            return;
        }

        this.capeImage = ImageIO.read(Files.newInputStream(path));
        Server.getInstance().getEventBus().registerListener(this);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var skin = player.getSkin();
        var skinId = UUID.randomUUID().toString();
        var capeId = UUID.randomUUID().toString();
        var newSkin = skin.toBuilder()
                .capeData(ImageData.from(capeImage))
                .capeId(capeId)
                .skinId(skinId)
                .fullSkinId(skinId)
                // TODO: waiting for https://github.com/CloudburstMC/Protocol/pull/292, missing fields in SerializedSkin.toBuilder() method
                .geometryDataEngineVersion(ProtocolInfo.getMinecraftVersionStr())
                .build();
        player.setSkin(newSkin);
    }
}