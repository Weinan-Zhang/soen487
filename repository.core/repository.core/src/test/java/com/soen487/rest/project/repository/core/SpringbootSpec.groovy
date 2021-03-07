package com.soen487.rest.project.repository.core

import com.soen487.rest.project.repository.core.service.CalculateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringbootSpec extends Specification {
    @Autowired
    CalculateService calculateService;

    def "test in springboot context"(){
        expect: "test minus method"
        z == calculateService.minus(x, y)

        where:
        x | y | z
        1 | 2 | -1
        5 | 2 | 3
        11 | 9 | 2
    }

    def "another test in springboot context"() {
        expect: "use test cases stored in array"
        z == calculateService.minus(x, y)

        where:
        x << [12, 5, 9]
        y << [8, 6, 11]
        z << [4, -1, -2]

    }
}
