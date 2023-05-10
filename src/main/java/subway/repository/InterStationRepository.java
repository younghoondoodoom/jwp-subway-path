package subway.repository;

import java.util.List;
import subway.domain.InterStation;

public interface InterStationRepository {

    InterStation save(InterStation interStation);

    void delete(InterStation deletedInterStation);

    void saveAll(List<InterStation> interStations);

    void addAll(InterStation... interStations);

    List<InterStation> findAllByName(String stationName);

    void deleteAll(List<InterStation> existedInterStations);

    void add(InterStation interStation);
}
