package subway.service;

import org.springframework.stereotype.Service;
import subway.dao.LineEntity;
import subway.domain.Line;
import subway.domain.Section;
import subway.domain.Station;
import subway.domain.Stations;
import subway.service.dto.StationDeleteRequest;
import subway.service.dto.StationRegisterRequest;

@Service
public class StationService {

    private final SectionService sectionService;

    private final CommonService commonService;
    private final LineService lineService;

    public StationService(
            final SectionService sectionService,
            final CommonService commonService,
            final LineService lineService
    ) {
        this.sectionService = sectionService;
        this.commonService = commonService;
        this.lineService = lineService;
    }

    public void registerStation(final StationRegisterRequest stationRegisterRequest) {

        final String lineName = stationRegisterRequest.getLineName();

        final Line line = commonService.mapToLineFrom(lineName);
        line.add(mapToSectionFrom(stationRegisterRequest));

        sectionService.updateLine(commonService.getLineEntity(lineName), line);
    }

    private Section mapToSectionFrom(final StationRegisterRequest stationRegisterRequest) {
        final Stations newStations = new Stations(
                new Station(stationRegisterRequest.getCurrentStationName()),
                new Station(stationRegisterRequest.getNextStationName()),
                stationRegisterRequest.getDistance()
        );

        return new Section(newStations);
    }

    public void deleteStation(final StationDeleteRequest stationDeleteRequest) {

        final String lineName = stationDeleteRequest.getLineName();
        final LineEntity lineEntity = commonService.getLineEntity(lineName);
        final Line line = commonService.mapToLineFrom(lineName);

        line.delete(new Station(stationDeleteRequest.getStationName()));

        if (line.isDeleted()) {
            sectionService.deleteAll(lineEntity.getId());
            lineService.deleteLine(lineEntity.getId());
            return;
        }

        sectionService.updateLine(lineEntity, line);
    }
}
