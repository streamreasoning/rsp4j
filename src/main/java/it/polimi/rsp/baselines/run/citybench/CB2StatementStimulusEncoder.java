package it.polimi.rsp.baselines.run.citybench;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import it.polimi.rsp.baselines.jena.events.stimuli.StatementStimulus;
import it.polimi.heaven.citybench.ssnobservations.SensorObservation;
import it.polimi.heaven.core.teststand.data.Line;
import it.polimi.heaven.core.teststand.events.HeavenInput;
import it.polimi.heaven.core.teststand.rspengine.events.Stimulus;
import it.polimi.heaven.core.teststand.streamer.Encoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CB2StatementStimulusEncoder implements Encoder {

	private List<Stimulus> stimuli;
	private Map<String, Model> streams_models;
	private SensorObservation so;
	private String streamID;
	private Stimulus[] a;

	@Override
	public Stimulus[] encode(HeavenInput e) {
		streams_models = new HashMap<String, Model>();
		for (Line tc : e.getLines()) {
			so = (SensorObservation) tc;
			streamID = so.getStreamID();
			if (streams_models.containsKey(streamID)) {
				streams_models.put(streamID, streams_models.get(streamID).add(so.toModel()));
			} else {
				streams_models.put(streamID, so.toModel());

			}

		}

		stimuli = new ArrayList<Stimulus>();

		for (String stream : streams_models.keySet()) {
			for (Statement statement : streams_models.get(stream).listStatements().toList()) {
				stimuli.add(new StatementStimulus(e.getStimuli_application_timestamp(), statement, stream));
			}
		}
		a = new Stimulus[stimuli.size()];
		return stimuli.toArray(a);
	}
}
