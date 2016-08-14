package it.polimi.rsp.baselines.jena.events.response;

import it.polimi.heaven.core.teststand.collector.Collectable;
import it.polimi.heaven.core.teststand.rsp.data.Response;
import it.polimi.heaven.core.teststand.rsp.querying.Query;
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
