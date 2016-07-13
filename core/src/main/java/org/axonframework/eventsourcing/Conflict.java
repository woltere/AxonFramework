package org.axonframework.eventsourcing;

/**
 * @author Joris van der Kallen
 * @since 3.0
 */
public class Conflict {

    private String aggregateIdentifier;
    private Long actualVersion;
    private Long expectedVersion;

    private boolean resolved = false;

    public Conflict(String aggregateIdentifier, Long actualVersion) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.actualVersion = actualVersion;
    }

    public Conflict(String aggregateIdentifier, Long actualVersion, Long expectedVersion) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.actualVersion = actualVersion;
        this.expectedVersion = expectedVersion;
    }

    public void resolve() {
        resolved = true;
    }

    public boolean isResolved() {
        return resolved;
    }
}
