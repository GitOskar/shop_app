package pl.umcs.shop_app.util

import spock.lang.Specification

class TimeUtilTest extends Specification {

    def "Should convert minutes to milliseconds correctly"() {

        expect:
        TimeUtil.minutesToMilliSeconds(minutes) == milliseconds

        where:
        minutes || milliseconds
        0       || 0
        1       || 60000
        5       || 300000
        50000   || 3000000000
    }
}
