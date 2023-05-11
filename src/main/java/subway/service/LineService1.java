package subway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LineService1 {

//    private final LineRepository lineRepository;
//    private final StationRepository stationRepository;
//    private final InterStationRepository interStationRepository;
//
//    @Transactional
//    public AddLineResponse saveLine(final AddLineRequest request) {
//        final Line1 line1 = new Line1(request.getName(), request.getColor());
//        final Station frontStation = getStation(request.getFrontStationName());
//        final Station backStation = getStation(request.getBackStationName());
//        line1.initStation(frontStation, backStation, request.getDistance());
//        final Line1 savedLine1 = lineRepository.save(line1);
//        return AddLineResponse.from(savedLine1);
//    }
//
//    @Transactional
//    public AddStationResponse addStation(final AddStationRequest addStationRequest) {
//        final Line1 line1 = lineRepository.findByName(addStationRequest.getLineName())
//            .orElseThrow(() -> new BusinessException("존재하지 않는 호선입니다."));
//        if (addStationRequest.getIsEnd()) {
//            final Station existedStation = getStation(addStationRequest.getFrontStation());
//            final Station station = getStation(addStationRequest.getStationName());
//            final InterStation interStation = new InterStation(existedStation, station,
//                addStationRequest.getDistance());
//            line1.add(interStation);
//            return AddStationResponse.from(station);
//        }
//        final Station existedFrontStation = getStation(addStationRequest.getFrontStation());
//        final Station existedBackStation = getStation(addStationRequest.getBackStation());
//        final InterStation deletedInterStation = line1.delete(existedFrontStation, existedBackStation);
//        interStationRepository.delete(deletedInterStation);
//        final Station station = getStation(addStationRequest.getStationName());
//        final InterStation interStation1 = new InterStation(station, existedFrontStation,
//            addStationRequest.getDistance());
//        final InterStation interStation2 = new InterStation(station, existedBackStation,
//            deletedInterStation.getDistance() - addStationRequest.getDistance());
//        interStationRepository.addAll(interStation1, interStation2);
//        return AddStationResponse.from(station);
//    }
//
//    private Station getStation(final String name) {
//        return stationRepository.findByName(name)
//            .orElseGet(() -> stationRepository.save(new Station(name)));
//    }
//
//    public void removeStation(final RemoveStationRequest request) {
//        final Station station = stationRepository.findByName(request.getStationName())
//            .orElseThrow(() -> new BusinessException("존재하지 않는 역입니다."));
//        final List<InterStation> existedInterStations = interStationRepository.findAllByName(request.getStationName());
//        final List<Line1> line1s = lineRepository.findAll();
//        final Map<Line1, List<InterStation>> lineListHashMap = makeInterStationsWithSameLine(
//            existedInterStations, line1s);
//        final InterStationFinder interStationFinder = new InterStationFinder(lineListHashMap);
//        removeLines(interStationFinder);
//        removeStations(interStationFinder);
//        final List<InterStation> interStations = makeNewInterStations(station, lineListHashMap);
//        interStationRepository.addAll(interStations);
//        interStationRepository.deleteAll(existedInterStations);
//        stationRepository.remove(station);
//    }
//
//    private void removeLines(final InterStationFinder interStationFinder) {
//        final List<Line1> oneInterStationsLine1s = interStationFinder.findLines(
//            (line, interStations) -> interStations.size() == 1 && line.interStationCountEquals(1));
//        lineRepository.removeAll(oneInterStationsLine1s);
//    }
//
//    private void removeStations(final InterStationFinder interStationFinder) {
//        final List<Station> stations = interStationFinder.findStations(
//            (line, interStations) -> interStations.size() == 1 && (line.interStationCountEquals(1)));
//        stationRepository.removeAll(stations);
//    }
//
//    private List<InterStation> makeNewInterStations(final Station station,
//        final Map<Line1, List<InterStation>> lineListHashMap) {
//        final List<InterStation> interStations = new ArrayList<>();
//        for (final Entry<Line1, List<InterStation>> longListEntry : lineListHashMap.entrySet()) {
//            final List<InterStation> sameLineInterStations = longListEntry.getValue();
//            if (sameLineInterStations.size() == 2) {
//                final InterStation interStation = makeNewInterStation(station, sameLineInterStations);
//                interStations.add(interStation);
//            }
//
//        }
//        return interStations;
//    }
//
//    private InterStation makeNewInterStation(final Station station, final List<InterStation> interStations) {
//        final List<Station> stations = new ArrayList<>();
//        long distance = 0;
//        for (final InterStation interStation : interStations) {
//            stations.add(interStation.findNotEqualStation(station));
//            distance += interStation.getDistance();
//        }
//        return new InterStation(stations.get(0), stations.get(1), distance);
//    }
//
//    private Map<Line1, List<InterStation>> makeInterStationsWithSameLine(
//        final List<InterStation> existedInterStations,
//        final List<Line1> line1s) {
//        final Map<Line1, List<InterStation>> lineListHashMap = new HashMap<>();
//        for (final InterStation interStation : existedInterStations) {
//            for (final Line1 line1 : line1s) {
//                if (line1.contains(interStation)) {
//                    final List<InterStation> list = lineListHashMap.getOrDefault(line1, new ArrayList<>());
//                    list.add(interStation);
//                    lineListHashMap.put(line1, list);
//                }
//            }
//        }
//        return lineListHashMap;
//    }
}
