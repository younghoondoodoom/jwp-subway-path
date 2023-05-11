package subway.entity;

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
}
