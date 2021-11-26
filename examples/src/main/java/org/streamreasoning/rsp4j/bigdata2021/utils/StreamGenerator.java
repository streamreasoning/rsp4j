package org.streamreasoning.rsp4j.bigdata2021.utils;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


public class StreamGenerator {
    private static final String PREFIX = "http://test/";
    private static final Long TIMEOUT = 1000l;

    private DataStream<Graph> observationStream;
    private DataStream<Graph> covidStream;
    private DataStream<Graph> contactStream;
    private final AtomicBoolean isStreaming;
    private final Random randomGenerator;
    private AtomicLong streamIndexCounter;

    private enum Person {Alice, Bob, Elena, Carl, David, John};
    private enum Room {Red, Blue};
    private enum EventType {RFID, Facebook, ContactTracing, HospitalResult};

    Map<Person, EventType> personsEventTypesMap =
      Map.of(Person.Alice, EventType.RFID,
              Person.John, EventType.RFID,
              Person.Bob, EventType.Facebook,
              Person.Elena, EventType.Facebook);

    Map<Person, Person> isWithMap =
            Map.of(Person.Carl, Person.Bob,
                    Person.David, Person.Elena);

    public StreamGenerator() {
        this.observationStream = new DataStreamImpl<>(PREFIX+"observations");
        this.covidStream = new DataStreamImpl<>(PREFIX+"testResults");
        this.contactStream = new DataStreamImpl<>(PREFIX+"tracing");
        this.streamIndexCounter = new AtomicLong(0);
        this.isStreaming = new AtomicBoolean(false);
        randomGenerator = new Random(1337);
    }

    public static String getPREFIX() {
        return StreamGenerator.PREFIX;
    }

    public DataStream<Graph> getObservationStream() {

        return observationStream;
    }
    public DataStream<Graph> getCovidStream(){
        return covidStream;
    }
    public DataStream<Graph> getContactStream(){
        return contactStream;
    }

    public void startStreaming() {
        if (!this.isStreaming.get()) {
            this.isStreaming.set(true);
      Runnable task =
          () -> {
            long ts = 0;
            while (this.isStreaming.get()) {
              long finalTs = ts;
              observationStream.put(createRandomObservationEvent(), ts);
              if (randomGenerator.nextDouble()>0.9){
                  covidStream.put(createRandomCovidEvent(),ts);
              }
                if (randomGenerator.nextDouble()>=0.5){
                    contactStream.put(createRandomContactTracingEvent(),ts);
                }
              ts += 5*60*1000;
              try {
                Thread.sleep(TIMEOUT);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          };

            Thread thread = new Thread(task);
            thread.start();
        }
    }


    private Person selectRandomPerson(Person[] persons){
        int randomIndex = randomGenerator.nextInt((persons.length));
        return persons[randomIndex];
    }
    private Person selectRandomPerson(){
       return selectRandomPerson(Person.values());
    }
    private Room selectRandomRoom(){
        int randomIndex = randomGenerator.nextInt((Room.values().length));
        return Room.values()[randomIndex];
    }
    public Graph createRandomObservationEvent(){
        Person randomPerson = selectRandomPerson(personsEventTypesMap.keySet().toArray(new Person[0]));
        Room randomRoom = selectRandomRoom();
        EventType selectedType = personsEventTypesMap.get(randomPerson);

        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI a = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        long eventID = streamIndexCounter.incrementAndGet();
        switch (selectedType){
            case RFID:
                graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), a, instance.createIRI(PREFIX + "RFIDObservation")));
                graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), instance.createIRI(PREFIX + "where"), instance.createIRI(PREFIX + randomRoom)));
                graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), instance.createIRI(PREFIX + "who"), instance.createIRI(PREFIX + randomPerson)));
                graph.add(instance.createTriple(instance.createIRI(PREFIX + randomPerson), instance.createIRI(PREFIX + "isIn"), instance.createIRI(PREFIX + randomRoom)));
                break;
            case Facebook:
                graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), a, instance.createIRI(PREFIX + "FacebookPost")));
                graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), instance.createIRI(PREFIX + "where"), instance.createIRI(PREFIX + randomRoom)));
                graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), instance.createIRI(PREFIX + "who"), instance.createIRI(PREFIX + randomPerson)));
                graph.add(instance.createTriple(instance.createIRI(PREFIX + randomPerson), instance.createIRI(PREFIX + "isIn"), instance.createIRI(PREFIX + randomRoom)));
                break;
            default:
                break;
        }
        return graph;
    }
    public Graph createRandomCovidEvent(){
        Person randomPerson = selectRandomPerson();


        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI a = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        long eventID = streamIndexCounter.incrementAndGet();

        graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), a, instance.createIRI(PREFIX + "TestResultPost")));
        graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), instance.createIRI(PREFIX + "who"), instance.createIRI(PREFIX + randomPerson)));

        return graph;
    }

    public Graph createRandomContactTracingEvent(){
        Person randomPerson = selectRandomPerson(isWithMap.keySet().toArray(new Person[0]));


        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI a = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        long eventID = streamIndexCounter.incrementAndGet();

        graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), a, instance.createIRI(PREFIX + "ContactTracingPost")));
        graph.add(instance.createTriple(instance.createIRI(PREFIX + "_observation" + eventID), instance.createIRI(PREFIX + "who"), instance.createIRI(PREFIX + randomPerson)));
        graph.add(instance.createTriple(instance.createIRI(PREFIX + randomPerson), instance.createIRI(PREFIX + "isWith"), instance.createIRI(PREFIX + isWithMap.get(randomPerson))));

        return graph;
    }

    public void stopStreaming() {
        this.isStreaming.set(false);
    }

    public static void main(String[] args){
        StreamGenerator gen = new StreamGenerator();
    for (int i = 0; i < 1000; i++) {
      System.out.println("New event:");
      System.out.println(gen.createRandomObservationEvent());
        }
    }
}
