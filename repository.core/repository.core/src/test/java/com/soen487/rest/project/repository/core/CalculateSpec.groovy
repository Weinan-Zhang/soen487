package com.soen487.rest.project.repository.core


import com.soen487.rest.project.repository.core.service.CalculateService
import spock.lang.Shared
import spock.lang.Specification

class CalculateSpec extends Specification {
    @Shared
    CalculateService calculateService;

    def setupSpec(){
        calculateService = new CalculateService();
        println ">>>>>>instantialte calculateSpec"
    }

    def setup(){
        println "steup"
    }

    def cleanup(){
        println "cleanup"
    }

    def cleanupSpec(){
        println "cleanupSpec"
    }

    def "test life cycle"(){
        given:
        def a = 1
        def b = 2

        expect:
        a < b

        println "finish test"
    }

    def "test plus method of CalculateService"(){
//        given: "prepare the test data"


        when: "test the plus method"
        def a = 1
        def b = 2
        def c = calculateService.plus(a, b)

        then: "assert the result of plus"
        c == 4 - 1
    }

    def "test the given-expect-where structure"(){
        given: "prepare the data"

        expect: "test the plus method"
        z == calculateService.plus(x, y)

        where: "define the test data"
        x | y || z
        1 | 3 || 4
        7 | 6 || 13
        100 | -1 || 99
    }

    def "test the where clause's mapping pattern"(){
        expect: "test the given strings length"
        name.size() == length

        where:
        name    |   length
        "weinan"    |   6
        "yuping"    |   6
        ""  |   0
    }

    def "test the where clause's array patter"(){
        expect: "test the String.size()"

        name.size() == length

        where:
        name << ["sap", "google"]
        length << [3, 6]
    }

    def "test with no parameters by verifyAll"(){
        when: "call test method"
        int z1 = calculateService.plus(1, 1)
        int z2 = calculateService.plus(5, 1)
        int z3 = calculateService.plus(6, 4)

        then:
        verifyAll{
            z1 == 2
            z2 == 6
            z3 == 10
        }
    }

    def "test by with"(){
        given:"instantiate Person"
        Date now = new Date()
        CalculateSpec.Person p = new CalculateSpec.Person("weinan", 40, now)

        with(p){
            name == "weinan"
            age > 30
            birthdate == now
        }
    }

    def "test of 'verifyAll' with parameter"(){
        when:
        CalculateSpec.Person p = new CalculateSpec.Person("yuping", 18)

        then:
        verifyAll(p){
            name == "yuping"
            age < 20
        }
    }

    def "test a target method with multiple times"(){
        given: "define a dummy variable to pass in"
        def a = 0

        expect: "perform target method with 3 times"
        3 * calculateService.plusplus(a) == 3

    }

    static class Person{
        String name
        int age
        Date birthdate

        Person(String name, int age, Date birthdate) {
            this.name = name
            this.age = age
            this.birthdate = birthdate
        }

        Person(String name, int age) {
            this.name = name
            this.age = age
        }
    }
}
