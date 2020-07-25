package nms;

import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import namecheck.NMS;
import net.minecraft.server.v1_13_R1.ChatMessageType;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.minecraft.server.v1_13_R1.IChatBaseComponent;
import net.minecraft.server.v1_13_R1.PacketPlayOutChat;
import net.minecraft.server.v1_13_R1.IChatBaseComponent.ChatSerializer;

public class V1_13 implements NMS{
	public void sendActionBar(Player p, String msg) {
		
		EntityPlayer entity = ((CraftPlayer) p).getHandle();
		IChatBaseComponent ichatbasecomponent = ChatSerializer.a("{\"text\":\"" + msg + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(ichatbasecomponent, ChatMessageType.GAME_INFO);
		
		entity.playerConnection.sendPacket(packet);
	}
}
