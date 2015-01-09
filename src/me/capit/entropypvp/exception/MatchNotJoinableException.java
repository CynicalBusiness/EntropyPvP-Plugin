package me.capit.entropypvp.exception;

import me.capit.entropypvp.match.Match;

public class MatchNotJoinableException extends Exception {
	private static final long serialVersionUID = -2819965438330272337L;

	private String reason;
	
	public MatchNotJoinableException(Match match, String reason){
		super(match.getDisplayName()+" could not be joined: "+reason);
		this.reason = reason;
	}
	
	public String getReason(){ return reason; }
	
}
