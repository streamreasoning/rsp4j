package it.polimi.rsp.core.rsp.query.response;

import it.polimi.data.Collectable;
import it.polimi.sr.rsp.RSPQuery;
import it.polimi.streaming.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.jena.reasoner.InfGraph;

@Getter
@AllArgsConstructor
public abstract class InstantaneousResponse implements Response, Collectable {

	private String id;
	private long creation_timestamp, cep_timestamp;
	private RSPQuery query;

	@Override
	public long getCreationTime() {
		return creation_timestamp;
	}

	@Override
	public String getQueryString() {
		return query.toString();
	}

	public abstract InstantaneousResponse minus(InstantaneousResponse r);

    public abstract InstantaneousResponse and(InstantaneousResponse new_response);
}
