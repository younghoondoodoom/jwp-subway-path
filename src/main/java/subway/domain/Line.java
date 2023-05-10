package subway.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import subway.exception.BusinessException;

@Getter
@AllArgsConstructor
public class Line {

    private final Long id;
    private final String name;
    private final String color;
    private final List<InterStation> interStations;

    public Line(final String name, final String color) {
        this(null, name, color);
    }

    public Line(final Long id, final String name, final String color) {
        this(id, name, color, new ArrayList<>());
    }

    public InterStation getFirstInterStation() {
        if (interStations.isEmpty()) {
            throw new BusinessException(name + "호선에 역이 없습니다.");
        }
        return interStations.get(0);
    }

    public InterStation initStation(final Station frontStationName, final Station backStationName,
        final long distance) {
        final InterStation interStation = new InterStation(frontStationName, backStationName,
            distance);
        interStations.add(interStation);
        return interStation;
    }

    private InterStation removeExistedInterStation(
        final Predicate<InterStation> interStationPredicate) {
        final InterStation existedInterStation = interStations.stream()
            .filter(interStationPredicate)
            .findAny()
            .orElse(null);
        interStations.remove(existedInterStation);
        return existedInterStation;
    }

    public void addInterStations(final List<InterStation> interStations) {
        this.interStations.addAll(interStations);
    }

    public void add(final InterStation interStation) {
        interStations.add(interStation);
    }

    public InterStation delete(final Station existedFrontStation, final Station existedBackStation) {
        final InterStation removedInterStation = interStations.stream()
            .filter(interStation -> interStation.containsAll(existedFrontStation, existedBackStation))
            .findAny()
            .orElseThrow(() -> new BusinessException("존재하지 않는 역 정보입니다."));
        interStations.remove(removedInterStation);
        return removedInterStation;
    }

    public boolean contains(final InterStation interStation) {
        return interStations.contains(interStation);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Line line = (Line) o;
        return Objects.equals(getId(), line.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public boolean interStationCountEquals(final int size) {
        return interStations.size() == size;
    }
}
