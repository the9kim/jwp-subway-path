package subway.service;

import org.springframework.stereotype.Service;
import subway.domain.Line;
import subway.domain.RouteMap;
import subway.service.dto.response.LineResponse;
import subway.service.dto.request.RouteFindingRequest;
import subway.service.dto.response.RouteFindingResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {

    LineService lineService;
    LineMakerService lineMakerService;


    public RouteService(LineService lineService, LineMakerService lineMakerService) {
        this.lineService = lineService;
        this.lineMakerService = lineMakerService;
    }

    public RouteFindingResponse findShortestPath(RouteFindingRequest routeFindingRequest) {
        List<LineResponse> lineEntities = lineService.searchAllLines();
        List<Line> lines = lineEntities.stream()
                .map(lineEntity -> lineMakerService.mapToLineFrom(lineEntity.getLineName()))
                .collect(Collectors.toList());

        RouteMap routeMap = RouteMap.GenerateRouteMap(lines);

        List<String> shortestPath = routeMap.findShortestPath(routeFindingRequest.getStartStation(), routeFindingRequest.getEndStation());
        double shortestDistance = routeMap.findShortestDistance(routeFindingRequest.getStartStation(), routeFindingRequest.getEndStation());
        int fare = routeMap.calculateFare(shortestDistance);

        return new RouteFindingResponse(shortestPath, shortestDistance, fare);
    }
}
