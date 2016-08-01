package it.polimi.heaven.baselines.jena.events.response;

import it.polimi.heaven.core.teststand.collector.Collectable;
import it.polimi.heaven.core.teststand.rspengine.Query;
import it.polimi.heaven.core.teststand.rspengine.events.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaselineResponse implements Response, Collectable {

	private String id;
	private long creation_timestamp;
	private Query query;

	@Override
	public long getCreationTime() {
		return creation_timestamp;
	}

	@Override
	public String getQueryString() {
		return query.toString();
	}
}
