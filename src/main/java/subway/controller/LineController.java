package subway.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.controller.dto.AddLineRequest;
import subway.controller.dto.AddLineResponse;
import subway.service.LineService1;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lines")
public class LineController {

    private final LineService1 lineService1;

    @PostMapping
    public ResponseEntity<AddLineResponse> createLine(
        @RequestBody final AddLineRequest addLineRequest) {
        final AddLineResponse addLineResponse = lineService1.saveLine(addLineRequest);
        return ResponseEntity.created(URI.create("/lines/" + addLineResponse.getId()))
            .body(addLineResponse);
    }
}
