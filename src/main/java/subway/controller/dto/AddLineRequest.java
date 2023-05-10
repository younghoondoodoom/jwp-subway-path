package subway.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddLineRequest {

    private String name;
    private String color;
    private String frontStationName;
    private String backStationName;
    private Long distance;
}
