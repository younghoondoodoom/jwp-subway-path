package subway.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import lombok.RequiredArgsConstructor;
import subway.domain.InterStation;
import subway.domain.Line1;
import subway.domain.Station;

@RequiredArgsConstructor
public class InterStationFinder {

    private final Map<Line1, List<InterStation>> lineListHashMap;

    public List<Station> findStations(final BiPredicate<Line1, List<InterStation>> predicate) {
        final List<Station> stations = new ArrayList<>();
        for (final Line1 line1 : lineListHashMap.keySet()) {
            final List<InterStation> interStations = lineListHashMap.get(line1);
            if (predicate.test(line1, interStations)) {
                final InterStation interStation = interStations.get(0);
                stations.add(interStation.getFirstStation());
                stations.add(interStation.getSecondStation());
            }
        }
        return stations;
    }

    public List<Line1> findLines(final BiPredicate<Line1, List<InterStation>> predicate) {
        final ArrayList<Line1> line1s = new ArrayList<>();
        for (final Line1 line1 : lineListHashMap.keySet()) {
            final List<InterStation> interStations = lineListHashMap.get(line1);
            if (predicate.test(line1, interStations)) {
                line1s.add(line1);
            }
        }
        return line1s;
    }
}
