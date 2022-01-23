package crypto.assignment.utils;

import crypto.assignment.dto.CandleStickChart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChartIntervalParserTest {

    @ParameterizedTest
    @MethodSource("intervalStringTestCases")
    void parseIntervalStringToMillis_convert_interval_string_toMilli(String intervalString, double expectedMillisecond) {
        double actualMillisecond = ChartIntervalParser.parseIntervalStringToMillis(intervalString);
        assertEquals(expectedMillisecond, actualMillisecond);
    }

    @Test
    void setIntervalInMillis_throws_error_if_invalid_interval() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> ChartIntervalParser.parseIntervalStringToMillis("1X"),
                "Expected setIntervalInMillis() to throw an exception for invalid interval"
        );

        assertTrue(thrown.getMessage().contains("Invalid Interval provided"));
    }

    private static Stream<Arguments> intervalStringTestCases() {
        return Stream.of(
                Arguments.of("1m", Double.valueOf("60000")),
                Arguments.of("5m", Double.valueOf("300000")),
                Arguments.of("15m", Double.valueOf("900000")),
                Arguments.of("30m", Double.valueOf("1800000")),
                Arguments.of("1h", Double.valueOf("3600000")),
                Arguments.of("4h", Double.valueOf("14400000")),
                Arguments.of("6h", Double.valueOf("21600000")),
                Arguments.of("12h", Double.valueOf("43200000")),
                Arguments.of("1D", Double.valueOf("86400000")),
                Arguments.of("7D", Double.valueOf("604800000")),
                Arguments.of("14D", Double.valueOf("1209600000")),
                Arguments.of("1M", Double.valueOf("2592000000"))
        );
    }
}

