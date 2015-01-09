package me.capit.entropypvp.exception;

import me.capit.entropypvp.match.Team;

public class TeamNotJoinableException extends Exception {
	private static final long serialVersionUID = -2819965438330272337L;

	private String reason;
	
	public TeamNotJoinableException(Team team, String reason){
		super(team.getName()+" could not be joined: "+reason);
		this.reason = reason;
	}
	
	public String getReason(){ return reason; }
}
