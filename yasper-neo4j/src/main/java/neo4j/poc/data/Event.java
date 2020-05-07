package neo4j.poc.data;

public class Event {
    private String initiated;
    private String accepted;
    private String date;

    public Event(String initiated, String accepted, String date) {
        this.initiated = initiated;
        this.accepted = accepted;
        this.date = date;
    }

    public String getInitiated() {
        return initiated;
    }

    public String getAccepted() {
        return accepted;
    }

    public String getDate() {
        return date;
    }
}
