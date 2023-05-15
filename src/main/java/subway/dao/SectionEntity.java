package subway.dao;

public class SectionEntity {

    private final Long id;
    private final String currentStationName;
    private final String nextStationName;
    private final int distance;
    private final Long lineId;

    public SectionEntity(final Long id,
                         final String currentStationName, final String nextStationName,
                         final int distance, final Long lineId) {
        this.id = id;
        this.currentStationName = currentStationName;
        this.nextStationName = nextStationName;
        this.distance = distance;
        this.lineId = lineId;
    }

    public SectionEntity(final String currentStationName,
                         final String nextStationName,
                         final int distance, final Long lineId) {
        this(null, currentStationName, nextStationName, distance, lineId);
    }

    public Long getId() {
        return id;
    }

    public String getCurrentStationName() {
        return currentStationName;
    }

    public String getNextStationName() {
        return nextStationName;
    }

    public int getDistance() {
        return distance;
    }

    public Long getLineId() {
        return lineId;
    }
}
