package subway.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InterStationEntity {

    private final Long id;
    private final Long lineId;
    private final Long frontStationId;
    private final Long backStationId;
    private final long distance;

    public InterStationEntity(final Long lineId, final Long frontStationId, final Long backStationId, final long distance) {
        this(null, lineId, frontStationId, backStationId, distance);
    }
}
