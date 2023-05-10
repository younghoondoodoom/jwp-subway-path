package subway.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import subway.controller.dto.AddLineRequest;
import subway.controller.dto.AddLineResponse;
import subway.controller.dto.InterStationResponse;
import subway.domain.InterStation;
import subway.domain.Line;
import subway.domain.Station;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

@ExtendWith(MockitoExtension.class)
class LineServiceTest {

    @Mock
    private LineRepository lineRepository;
    @Mock
    private StationRepository stationRepository;
    @InjectMocks
    private LineService lineService;

    @Test
    public void 호선을_생성한다() {
        //given
        final AddLineRequest addLineRequest = new AddLineRequest("name", "color",
            "frontStationName", "backStationName", 10L);
        final Station frontStation = new Station(1L, addLineRequest.getFrontStationName());
        final Station backStation = new Station(2L, addLineRequest.getBackStationName());
        final InterStation interStation = new InterStation(1L, frontStation, backStation,
            addLineRequest.getDistance());
        final Line savedLine = new Line(1L, "name", "color", List.of(interStation));
        given(lineRepository.save(any())).willReturn(savedLine);
        given(stationRepository.findByName(any())).willReturn(Optional.of(frontStation),
            Optional.of(backStation));

        //when
        final AddLineResponse addLineResponse = lineService.saveLine(addLineRequest);

        //then
        assertThat(addLineResponse.getName()).isEqualTo(savedLine.getName());
        assertThat(addLineResponse).usingRecursiveComparison().isEqualTo(
            new AddLineResponse(1L, "name", "color", InterStationResponse.from(interStation)));
    }

    @Test
    public void 역을_추가한다() {
        //given

    }
}
