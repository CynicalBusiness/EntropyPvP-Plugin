package me.capit.entropypvp;

import java.util.Map;
import java.util.TreeMap;

import me.capit.entropypvp.match.Match;

import org.bukkit.plugin.java.JavaPlugin;

public class EntropyPvP extends JavaPlugin {
	public final Map<String, Match> matches = new TreeMap<String, Match>();
	
	private String defaultMatch;
	
	@Override
	public void onEnable(){
		
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public Match getDefaultMatch(){
		return defaultMatch!=null && matches.containsKey(defaultMatch)
				? matches.get(defaultMatch)
				: matches.get(matches.keySet().iterator().next());
	}
}
