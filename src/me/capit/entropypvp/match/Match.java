package me.capit.entropypvp.match;

import me.capit.entropypvp.EntropyPvP;
import me.capit.entropypvp.exception.MatchNotJoinableException;

import org.bukkit.entity.Player;

public abstract class Match{
	protected final Player[] players;
	protected final EntropyPvP plugin;
	
	public Match(EntropyPvP plugin, int size){
		this.plugin = plugin;
		players = new Player[size];
	}
	
	public abstract boolean isActive();
	public abstract boolean isJoinable();
	
	public abstract String getDisplayName();
	
	public final int getFirstEmptySlot(){
		int slot = -1;
		for (int i=0; i<players.length; i++) if (players[i]==null){ slot = i; break; }
		return slot;
	}
	public final boolean isFull(){
		return getFirstEmptySlot()>=0;
	}
	
	// Player stuff
	public final void addPlayer(Player player) throws MatchNotJoinableException {
		
	}
	public final void movePlayer(Player player, Match match) throws MatchNotJoinableException {
		
	}
	public final void kickPlayer(Player player) throws MatchNotJoinableException {
		movePlayer(player, plugin.getDefaultMatch());
	}
	protected abstract void playerAdded(Player player);
	protected abstract void playerRemoved(Player player);
	
	public final int getNumTeams(){
		return getTeamNames().length;
	}
	public abstract String[] getTeamNames();
	public abstract int getPlayersToDisplayPerTeam();
	
	public abstract boolean chatDisabled();
}
