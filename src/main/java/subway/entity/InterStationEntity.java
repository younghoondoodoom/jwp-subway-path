package subway.entity;

import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import subway.domain.InterStation;

@Getter
@RequiredArgsConstructor
public class InterStationEntity {

    private final Long id;
    private final StationEntity frontStation;
    private final StationEntity backStation;
    private final long distance;

    public static InterStationEntity from(final InterStation interStation) {
        return new InterStationEntity(interStation.getId(),
            StationEntity.from(interStation.getFirstStation()),
            StationEntity.from(interStation.getSecondStation()),
            interStation.getDistance());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InterStationEntity that = (InterStationEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
