package me.capit.entropypvp.match;

import java.util.List;

import me.capit.entropypvp.EntropyPvP;
import me.capit.entropypvp.exception.MatchNotJoinableException;
import me.capit.entropypvp.exception.TeamNotJoinableException;

import org.bukkit.entity.Player;

public abstract class Match{
	protected final Team[] teams;
	protected final EntropyPvP plugin;
	protected final String name;
	protected final int delay, restart;
	
	public Match(EntropyPvP plugin, List<String> teams, int teamSize, String name,
			int startupDelay, int restartTimer){
		this.plugin = plugin; this.name = name;
		
		this.teams = new Team[teams.size()];
		for (int i=0; i<teams.size(); i++){
			this.teams[i] = new Team(teams.get(i), teamSize>=0 ? teamSize : plugin.getServer().getMaxPlayers());
		}
		
		delay = startupDelay;
		restart = restartTimer;
	}
	
	public final boolean isActive(){
		return delay==0 && restart!=0;
	}
	public final boolean isJoinable(){
		return !isFull();
	}
	
	public abstract String getDisplayName();
	public final String getName(){
		return name;
	}
	
	public final Team getFirstTeamWithEmptySlot(){
		for (int i=0; i<teams.length; i++) if (teams[i]==null && !teams[i].isFull()) return teams[i];
		return null;
	}
	public final Team getSmallestTeam(){
		Team smallest = null;
		for (Team team : teams){
			if (team.isFull()) continue;
			if (smallest==null || smallest.size()<=team.size()) smallest = team;
		}
		return smallest;
	}
	public final boolean isFull(){
		return getSmallestTeam()!=null;
	}
	public final boolean hasPlayer(Player player){
		return getPlayerTeam(player)!=null;
	}
	public final Team getPlayerTeam(Player player){
		for (Team team : teams) if (team.hasPlayer(player)) return team;
		return null;
	}
	
	// Player stuff
	public final void addPlayer(Player player) throws MatchNotJoinableException, TeamNotJoinableException {
		if (isFull()) throw new MatchNotJoinableException(this, "Match is full.");
		if (hasPlayer(player)) throw new MatchNotJoinableException(this, "Already in match.");
		getSmallestTeam().addPlayer(player);
		playerAdded(player);
	}
	public final void removePlayer(Player player) {
		if (!hasPlayer(player)) return;
		getPlayerTeam(player).removePlayer(player);
		playerRemoved(player);
	}
	protected abstract void playerAdded(Player player);
	protected abstract void playerRemoved(Player player);
	
	public final int getNumTeams(){
		return getTeamNames().length;
	}
	public final String[] getTeamNames(){
		String[] names = new String[teams.length];
		for (int i=0; i<names.length; i++){
			names[i] = teams[i].getName();
		}
		return names;
	}
	public abstract int getPlayersToDisplayPerTeam();
	
	public abstract boolean chatDisabled();
	
	public List<String> getScoreboardText(){
		
	}
}
