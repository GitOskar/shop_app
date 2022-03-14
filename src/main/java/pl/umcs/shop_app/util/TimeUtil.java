package pl.umcs.shop_app.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TimeUtil {

    /**
     *
     * @param minutes - minutes count
     * @return equivalent of minutes provided in milliseconds
     */
    public static long minutesToMilliSeconds(long minutes) {
        return minutes * 60 * 1000;
    }
}
