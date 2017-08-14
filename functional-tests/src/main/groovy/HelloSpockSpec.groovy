package main.groovy

import spock.lang.*

class HelloSpockSpec extends Specification {

    def "Test length of Hello World"() {
        expect:
            "hello world".length() == 11
    }
}