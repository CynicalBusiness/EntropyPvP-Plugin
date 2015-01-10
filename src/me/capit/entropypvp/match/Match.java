package me.capit.entropypvp.match;

import java.util.ArrayList;
import java.util.List;

import me.capit.entropypvp.EntropyPvP;
import me.capit.entropypvp.exception.MatchNotJoinableException;
import me.capit.entropypvp.exception.TeamNotJoinableException;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.jdom2.Element;
import org.jdom2.JDOMException;

public abstract class Match{
	protected final boolean enabled;
	protected final Team[] teams;
	protected final EntropyPvP plugin;
	protected final String name,displayName;
	protected final ChatColor color;
	protected final int delay, restart, playersPerTeam, playersOnScoreboard;
	
	public Match(EntropyPvP plugin, Element element) throws JDOMException, IllegalArgumentException, NullPointerException {
		this.plugin = plugin; 
		
		if (element.getAttribute("name")==null) throw new IllegalArgumentException("Match does not specify a name.");
		name = element.getAttributeValue("name");
		
		Element meta = element.getChild("meta");
			displayName = meta.getAttributeValue("display_name");
			color = ChatColor.valueOf(meta.getAttributeValue("color"));
		
		Element data = element.getChild("data");
			enabled = Boolean.parseBoolean(data.getAttributeValue("enabled"));
			delay = Integer.parseInt(data.getAttributeValue("start_delay"));
			restart = Integer.parseInt(data.getAttributeValue("restart_timer"));
		
		Element teams = element.getChild("teams");
			playersPerTeam = Integer.parseInt(teams.getAttributeValue("players_per_team"));
			playersOnScoreboard = Integer.parseInt(teams.getAttributeValue("players_on_scoreboard"));
			this.teams = new Team[teams.getChildren().size()];
			for (int i=0; i<this.teams.length; i++){
				this.teams[i] = new Team(plugin, playersPerTeam, teams.getChildren().get(i));
			}
	}
	
	public final boolean isActive(){
		return delay==0 && restart!=0;
	}
	public final boolean isJoinable(){
		return !isFull();
	}
	
	public final String getDisplayName(){
		return color+displayName;
	}
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
		
		return new ArrayList<String>(); // TODO
	}
}
