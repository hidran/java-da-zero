package com.shop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class TracingTests {
    private static final String TRACE_ID = "4bf92f3577b34da6a3ce929d0e0e4736";
    private static final String SPAN_AND_TEXT = "-[0-9a-f]{16}\\] .*";

    @Autowired MockMvc mvc;

    @Test
    void productIsReturned() throws Exception {
        mvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    void incomingTraceparentIsContinued(CapturedOutput output) throws Exception {
        String traceparent = "00-" + TRACE_ID + "-00f067aa0ba902b7-01";
        mvc.perform(get("/api/products/2").header("traceparent", traceparent))
                .andExpect(status().isOk());
        String found = TRACE_ID + SPAN_AND_TEXT + "Found product Mouse";
        assertThat(output.getOut()).containsPattern(found);
    }

    @Test
    void bothLogLinesShareOneTraceId(CapturedOutput output) throws Exception {
        mvc.perform(get("/api/products/3")).andExpect(status().isOk());
        String start = "\\[([0-9a-f]{32})" + SPAN_AND_TEXT + "Finding product 3";
        Matcher finding = Pattern.compile(start).matcher(output.getOut());
        assertThat(finding.find()).isTrue();
        String found = finding.group(1) + SPAN_AND_TEXT + "Found product Monitor";
        assertThat(output.getOut()).containsPattern(found);
    }
}
