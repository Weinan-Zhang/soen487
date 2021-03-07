package com.soen487.rest.project.repository.core

import com.soen487.rest.project.repository.core.service.CalculateService
import spock.lang.Specification

class StubSpec extends Specification {
    def "test with stub"(){
        given: "create a stub of CalculateService"
        CalculateService calculateService = Stub(CalculateService)
        calculateService.plus(_,_) >> 1

        when: "mock the plus method"
        int x = calculateService.plus(10, 12)
        int y = calculateService.plus(2, 22)

        then:
        x == 1
        y == 1
    }

    def "test stub for returning multiple dummy value"(){
        given: "create stub of calculateService"
        CalculateService calculateService = Stub(CalculateService)
        calculateService.plusplus(_) >>> [1, 2, 3]

        when:
        int x =calculateService.plusplus(14)
        int y =calculateService.plusplus(15)
        int z = calculateService.plusplus(16)

        then:
        x == 1
        y == 2
        z == 3
    }
}
