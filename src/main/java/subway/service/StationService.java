package subway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.controller.dto.AddStationRequest;
import subway.controller.dto.AddStationResponse;
import subway.domain.Station;
import subway.repository.InterStationRepository;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StationService {

    private final StationRepository stationRepository;
    private final LineRepository lineRepository;
    private final InterStationRepository interStationRepository;

    @Transactional
    public AddStationResponse saveStation(final AddStationRequest addStationRequest) {
//        final Line line = lineRepository.findByName(addStationRequest.getLineName());
//        final Station frontStation = getStation(addStationRequest.getFrontStationName());
//        final Station backStation = getStation(addStationRequest.getBackStationName());
//        final InterStation existedInterStation = line.delete(backStation);
//        interStationRepository.delete(existedInterStation);
//        final InterStation interStation = line.addStation(frontStation, backStation,
//            addStationRequest.getDistance(), existedInterStation);
//        final Line update = lineRepository.update(line);
//        final InterStation result = interStationRepository.save(interStation);
//        return AddStationResponse.from(update);
        return null;
    }

    private Station getStation(final String name) {
        return stationRepository.findByName(name)
                .orElseGet(() -> stationRepository.save(new Station(name)));
    }
}
