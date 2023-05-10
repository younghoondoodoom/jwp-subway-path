package subway.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class AddStationRequest {

    private Boolean isEnd;
    private String lineName;
    private String frontStation;
    private String backStation;
    private String stationName;
    private Long distance;
}
