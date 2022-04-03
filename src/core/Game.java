package core;

import java.io.Serializable;

import core.entities.Life;
import state_machine.core.FiniteStateMachine;
import state_machines.UserPlanetState;
import utils.Pair;

public class Game implements Serializable {

	private static final long serialVersionUID = 962708223181482749L;
	private long l_chatId=0l;
	private UserPlanetState userPState;
	private Life entities;
	private boolean[] flags;
	private Pair<Long,Long> times;
	private Keyboard keyboard;
	private FiniteStateMachine literalNum;
	
	public Game(long l_chatId) {
		init();
		this.l_chatId=l_chatId;
	}
	public Game() {
		init();
		l_chatId=0l;
	}
	public long getL_chatId() {
		return l_chatId;
	}
	public void setL_chatId(long l_chatId) {
		this.l_chatId = l_chatId;
	}

	public UserPlanetState getUserPState() {
		return userPState;
	}

	public void setUserPState(UserPlanetState userPState) {
		this.userPState = userPState;
	}

	public Life getEntities() {
		return entities;
	}

	public void setEntities(Life entities) {
		this.entities = entities;
	}

	public boolean[] getFlags() {
		return flags;
	}

	public void setFlags(boolean[] flags) {
		this.flags = flags;
	}

	public Pair<Long, Long> getTimes() {
		return times;
	}

	public void setTimes(Pair<Long, Long> times) {
		this.times = times;
	}

	public Keyboard getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(Keyboard keyboard) {
		this.keyboard = keyboard;
	}

	public FiniteStateMachine getLiteralNum() {
		return literalNum;
	}
	public void setLiteralNum(FiniteStateMachine literalNum) {
		this.literalNum = literalNum;
	}
	public boolean equals(Object o) {
		if (!(o instanceof Game || o == null))
			return false;
		Game game = (Game) o;
		if (game.l_chatId == this.l_chatId)return true;
		return false;

	}
	private void init() {
		userPState = new UserPlanetState(null,null,null);
		entities = new Life();
		flags = new boolean[4];
		times = new Pair<>(0l,0l);
		keyboard=new Keyboard();
	}
	@Override
	public String toString() {
		return "Game [l_chatId=" + l_chatId + ", userPState=" + userPState + ", entities="
				+ entities + "]";
	}

}
