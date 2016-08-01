package it.polimi.rsp.baselines.run.citybench;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphUtil;
import it.polimi.rsp.baselines.jena.events.stimuli.GraphStimulus;
import it.polimi.heaven.citybench.ssnobservations.SensorObservation;
import it.polimi.heaven.core.teststand.data.Line;
import it.polimi.heaven.core.teststand.events.HeavenInput;
import it.polimi.heaven.core.teststand.rspengine.events.Stimulus;
import it.polimi.heaven.core.teststand.streamer.Encoder;

import java.util.HashMap;
import java.util.Map;

public class 	CB2GraphStimulusEncoder implements Encoder {

	private Map<String, GraphStimulus> streams_models;
	private String streamID;
	long obTimeStamp;

	@Override
	public Stimulus[] encode(HeavenInput e) {
		streams_models = new HashMap<String, GraphStimulus>();
		for (Line tc : e.getLines()) {
			SensorObservation so = (SensorObservation) tc;
			streamID = so.getStreamID();
			Graph base = so.toModel().getGraph();
			if (streams_models.containsKey(streamID)) {

				GraphStimulus graphStimulus = streams_models.get(streamID);
				graphStimulus.setAppTimestamp(obTimeStamp = so.getObTimeStamp());

				Graph content = graphStimulus.getContent();
				GraphUtil.addInto(content, base);
				streams_models.put(streamID, graphStimulus);

			} else {
				obTimeStamp = so.getObTimeStamp();
				streams_models.put(streamID, new GraphStimulus(obTimeStamp, base, streamID));

			}

		}

		return streams_models.values().toArray(new GraphStimulus[streams_models.size()]);
	}
}
