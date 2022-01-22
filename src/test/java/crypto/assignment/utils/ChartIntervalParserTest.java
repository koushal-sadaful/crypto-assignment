package crypto.assignment.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChartIntervalParserTest {

    @ParameterizedTest
    @MethodSource("intervalStringTestCases")
    void shouldConvertIntervalStringToMilliCorrectly(String intervalString, double expectedMillisecond) {
        double actualMillisecond = ChartIntervalParser.parseIntervalStringToMillis(intervalString);
        assertEquals(expectedMillisecond, actualMillisecond);
    }

    private static Stream<Arguments> intervalStringTestCases() {
        return Stream.of(
                Arguments.of("1m", 60000),
                Arguments.of("5m", 300000),
                Arguments.of("15m", 15000000),
                Arguments.of("30m", 1000),
                Arguments.of("1h", 1000),
                Arguments.of("4h", 1000),
                Arguments.of("6h", 1000),
                Arguments.of("12h", 1000),
                Arguments.of("1D", 1000),
                Arguments.of("7D", 1000),
                Arguments.of("14D", 1000),
                Arguments.of("1M", 1000)
        );
    }
}

