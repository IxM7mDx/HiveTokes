package de.alphahelix.hivetokens.netty;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by AlphaHelixDev.
 */
public class PacketReader {

    private static Field channelField;

    static {
        for (Field f : ReflectionUtil.getNmsClass("NetworkManager").getDeclaredFields()) {
            if (f.getType().isAssignableFrom(Channel.class)) {
                channelField = f;
                channelField.setAccessible(true);
                break;
            }
        }
    }

    public ChannelHandler listen(final Player p, final PacketReceivingHandler handler) {
        Channel ch = getNettyChannel(p);
        ChannelPipeline pipe = ch.pipeline();

        ChannelHandler handle = new MessageToMessageDecoder<Object>() {
            @Override
            protected void decode(ChannelHandlerContext chc, Object packet, List<Object> out) throws Exception {
                if (packet.getClass().isAssignableFrom(ReflectionUtil.getNmsClass("PacketPlayInUseEntity"))) {
                    if (!handler.handle(p, packet)) {
                        out.add(packet);
                    }
                    return;
                }
                out.add(packet);
            }
        };
        pipe.addAfter("decoder", "alphaL", handle);
        return handle;
    }

    public boolean close(Player p, ChannelHandler handler) {
        try {
            ChannelPipeline pipe = getNettyChannel(p).pipeline();
            pipe.remove(handler);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public Channel getNettyChannel(Player p) {
        try {
            Object manager = ReflectionUtil.getNmsClass("PlayerConnection").getField("networkManager").get(ReflectionUtil.getNmsClass("EntityPlayer").getField("playerConnection").get(ReflectionUtil.getEntityPlayer(p)));
            return (Channel) channelField.get(manager);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
