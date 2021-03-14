package com.soen487.rest.project.repository.core

import com.soen487.rest.project.repository.core.service.CalculateService
import spock.lang.Shared
import spock.lang.Specification

class SpockTrainningSpec extends Specification {
    @Shared sql = Sql.newInstance("sql:h2:mem","org.h2.driver")
    def "try the condition spec"(){
        given:
        int[] a = new int[2]

        when:
        a[0] = 1
        a[1] = 2

        then:
        a.size() == 2
        a[1] == 2
    }

    def "out of bound exception test"(){
        given:
        int[] a = new int[1]
        a[0] = 1

        when:
        a[1] = 2

        then:
        thrown(IndexOutOfBoundsException)

    }

    def "no exception test check"(){
        given:
        def map = new HashMap();

        when:
        map.put(null, "some")

        then:
        notThrown(NullPointerException)
    }

    def "where test with data driven test"(){
        expect:
        Math.max(a, b) == c

        where:
        a << [1, 6]
        b << [5, 3]
        c << [5, 6]
    }

    def "test with block"(){
        expect:
        Math.min(a, b) == c

        where:
        a   |   b   |   c
        1   |   3   |   1
        11  |   22  |  11
        6   |   5   |   5
    }

    // how to connect data base during test
    def "connect to database"(){
        expect:
        Math.max(a, b) == c
        where:
        [a, b , c] << sql.rows("SELECT a, b, c FROM table limit 1")

        /* alternative way
        where:
        row << sql.rows("select * from maxdata")
        // pick apart columns
        a = row.a
        b = row.b
        c = row.c
        */
    }
}
