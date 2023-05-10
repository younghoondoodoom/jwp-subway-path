package subway.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import subway.controller.dto.AddLineRequest;
import subway.controller.dto.AddLineResponse;
import subway.controller.dto.InterStationResponse;
import subway.service.LineService;

@WebMvcTest
class LineControllerTest {

    private static final AddLineResponse LINE_RESPONSE = new AddLineResponse(1L, "name", "color",
        new InterStationResponse(1L, 2L, 3L, 10L));
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LineService lineService;

    @Test
    void 라인을_추가한다() throws Exception {
        given(lineService.saveLine(any())).willReturn(LINE_RESPONSE);
        final AddLineRequest addLineRequest = new AddLineRequest("name", "color",
            "frontStationName", "backStationName", 10L);
        final String request = objectMapper.writeValueAsString(addLineRequest);

        final MvcResult result = mockMvc.perform(
                post("/lines").contentType(MediaType.APPLICATION_JSON).content(request)).andDo(print())
            .andExpect(status().isCreated()).andReturn();

        final AddLineResponse addLineResponse = jsonToObject(result, AddLineResponse.class);
        assertThat(addLineResponse).usingRecursiveComparison().isEqualTo(LINE_RESPONSE);
    }

    private <T> T jsonToObject(final MvcResult result, final Class<T> valueType)
        throws UnsupportedEncodingException, JsonProcessingException {
        final String responseString = result.getResponse().getContentAsString();
        return objectMapper.readValue(responseString, valueType);
    }
}
