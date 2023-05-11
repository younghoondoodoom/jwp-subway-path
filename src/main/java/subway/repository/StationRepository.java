package subway.repository;

import java.util.List;
import java.util.Optional;
import subway.domain.Station;

public interface StationRepository {

    Station save(final Station station);

    Optional<Station> findByName(String name);

    void removeAll(List<Station> stations);

    void remove(Station station);
}
