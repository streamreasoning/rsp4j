package it.polimi.rsp.baselines.jena.events.response;

import it.polimi.data.Collectable;
import it.polimi.heaven.rsp.rsp.querying.Query;
import it.polimi.streaming.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaselineResponse implements Response, Collectable {

	private String id;
	private long creation_timestamp, cep_timestamp;
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
