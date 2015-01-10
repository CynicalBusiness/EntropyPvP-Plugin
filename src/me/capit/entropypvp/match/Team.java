package me.capit.entropypvp.match;

import me.capit.entropypvp.EntropyPvP;
import me.capit.entropypvp.exception.TeamNotJoinableException;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.jdom2.Element;
import org.jdom2.JDOMException;

public class Team {
	private final Player[] players;
	private final String name,displayName;
	private final ChatColor color;
	
	public Team(EntropyPvP plugin, int size, Element element) throws JDOMException, IllegalArgumentException, NullPointerException{
		players = new Player[size];
		name = element.getAttributeValue("name");
		color = ChatColor.valueOf(element.getAttributeValue("color"));
		displayName = element.getAttributeValue("display_name");
	}
	
	public int size(){
		return players.length;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDisplayName(){
		return color+displayName;
	}
	
	public final int getFirstEmptySlot(){
		for (int i=0; i<players.length; i++) if (players[i]==null) return i;
		return -1;
	}
	public final boolean isFull(){
		return getFirstEmptySlot()<0;
	}
	
	public boolean hasPlayer(Player player){
		for (Player p : players) if (p.getUniqueId().equals(player.getUniqueId())) return true;
		return false;
	}
	
	public final void addPlayer(Player player) throws TeamNotJoinableException {
		if (isFull()) throw new TeamNotJoinableException(this, "Team is full");
		if (hasPlayer(player)) throw new TeamNotJoinableException(this, "Player already on team.");
		players[getFirstEmptySlot()] = player;
	}
	public final void removePlayer(Player player) {
		if (!hasPlayer(player)) return;
		for (int i=0; i<players.length; i++)
			if (players[i].getUniqueId().equals(player.getUniqueId())){ players[i]=null; return; }
	}
}
