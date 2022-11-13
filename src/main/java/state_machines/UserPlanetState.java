package state_machines;

import java.io.Serializable;

import api.planet.Planet;
import core.planet.GraphPlanet;

public class UserPlanetState implements Serializable {

	private static final long serialVersionUID = -1715642796126350328L;
	private String type;
	private Planet planet;
	private GraphPlanet graph;

	public UserPlanetState(String type, Planet planet, GraphPlanet graph) {
		this.type = type;
		this.planet = planet;
		this.graph = graph;
	}

	public Planet getPlanet() {
		return planet;
	}

	public Planet getGraph() {
		return graph;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "UserPlanetState [type=" + type + ", planet=" + planet + ", graph=" + graph + "]";
	}
}
