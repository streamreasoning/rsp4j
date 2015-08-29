package it.polimi.heaven.baselines.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SerializedTripleEvent {
	protected String s, p, o;
	protected long timestamp, app_timestamp;

	@Override
	public String toString() {
		return "SerializedTripleEvent [s=" + s + ", p=" + p + ", o=" + o + "ts=" + timestamp + "app_ts=" + app_timestamp + "]";
	}

	public String[] getSs() {
		return new String[] { s };
	}

	public String[] getPs() {
		return new String[] { p };
	}

	public String[] getOs() {
		return new String[] { o };
	}

}