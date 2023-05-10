package subway.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.controller.dto.AddLineRequest;
import subway.controller.dto.AddLineResponse;
import subway.controller.dto.AddStationRequest;
import subway.controller.dto.AddStationResponse;
import subway.controller.dto.RemoveStationRequest;
import subway.domain.InterStation;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.BusinessException;
import subway.repository.InterStationRepository;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final InterStationRepository interStationRepository;

    @Transactional
    public AddLineResponse saveLine(final AddLineRequest request) {
        final Line line = new Line(request.getName(), request.getColor());
        final Station frontStation = getStation(request.getFrontStationName());
        final Station backStation = getStation(request.getBackStationName());
        line.initStation(frontStation, backStation, request.getDistance());
        final Line savedLine = lineRepository.save(line);
        return AddLineResponse.from(savedLine);
    }

    @Transactional
    public AddStationResponse addStation(final AddStationRequest addStationRequest) {
        final Line line = lineRepository.findByName(addStationRequest.getLineName())
            .orElseThrow(() -> new BusinessException("존재하지 않는 호선입니다."));
        if (addStationRequest.getIsEnd()) {
            final Station existedStation = getStation(addStationRequest.getFrontStation());
            final Station station = getStation(addStationRequest.getStationName());
            final InterStation interStation = new InterStation(existedStation, station,
                addStationRequest.getDistance());
            line.add(interStation);
            return AddStationResponse.from(station);
        }
        final Station existedFrontStation = getStation(addStationRequest.getFrontStation());
        final Station existedBackStation = getStation(addStationRequest.getBackStation());
        final InterStation deletedInterStation = line.delete(existedFrontStation, existedBackStation);
        interStationRepository.delete(deletedInterStation);
        final Station station = getStation(addStationRequest.getStationName());
        final InterStation interStation1 = new InterStation(station, existedFrontStation,
            addStationRequest.getDistance());
        final InterStation interStation2 = new InterStation(station, existedBackStation,
            deletedInterStation.getDistance() - addStationRequest.getDistance());
        interStationRepository.addAll(interStation1, interStation2);
        return AddStationResponse.from(station);
    }

    private Station getStation(final String name) {
        return stationRepository.findByName(name)
            .orElseGet(() -> stationRepository.save(new Station(name)));
    }

    public void removeStation(final RemoveStationRequest request) {
        final Station station = stationRepository.findByName(request.getStationName())
            .orElseThrow(() -> new BusinessException("존재하지 않는 역입니다."));
        final List<InterStation> existedInterStations = interStationRepository.findAllByName(request.getStationName());
        final List<Line> lines = lineRepository.findAll();
        final Map<Line, List<InterStation>> lineListHashMap = new HashMap<>();

        for (final InterStation interStation : existedInterStations) {
            for (final Line line : lines) {
                if (line.contains(interStation)) {
                    final List<InterStation> list = lineListHashMap.getOrDefault(line, new ArrayList<>());
                    list.add(interStation);
                    lineListHashMap.put(line, list);
                }
            }
        }

        for (final Entry<Line, List<InterStation>> longListEntry : lineListHashMap.entrySet()) {
            final List<InterStation> interStations = longListEntry.getValue();
            if (interStations.size() == 2) {
                final List<Station> stations = new ArrayList<>();
                long distance = 0;
                for (final InterStation interStation : interStations) {
                    stations.add(interStation.findNotEqualStation(station));
                    distance += interStation.getDistance();
                }
                final InterStation interStation = new InterStation(stations.get(0), stations.get(1), distance);
                interStationRepository.add(interStation);
                return;
            }
            if (interStations.size() == 1) {
                final Line line = longListEntry.getKey();
                if (line.interStationCountEquals(1)) {
                    lineRepository.remove(line);
                    return;
                }
            }

        }
        interStationRepository.deleteAll(existedInterStations);
    }
}
