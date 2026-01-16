package me.daoge.forcecape;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.server.PlayerJoinEvent;
import org.allaymc.api.player.Skin;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;

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
        var player = event.getPlayer().getControlledEntity();
        var skin = player.getSkin();
        var skinId = UUID.randomUUID().toString();
        var capeId = UUID.randomUUID().toString();
        var newSkin = skin.toBuilder()
                .capeData(toImageData(capeImage))
                .capeId(capeId)
                .skinId(skinId)
                .fullId(skinId)
                .build();
        player.setSkin(newSkin);
    }

    private static Skin.ImageData toImageData(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        byte[] data = new byte[width * height * 4];

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);

                // ARGB in int
                byte a = (byte) ((argb >> 24) & 0xFF);
                byte r = (byte) ((argb >> 16) & 0xFF);
                byte g = (byte) ((argb >> 8) & 0xFF);
                byte b = (byte) (argb & 0xFF);

                // RGBA order
                data[index++] = r;
                data[index++] = g;
                data[index++] = b;
                data[index++] = a;
            }
        }

        return new Skin.ImageData(width, height, data);
    }
}