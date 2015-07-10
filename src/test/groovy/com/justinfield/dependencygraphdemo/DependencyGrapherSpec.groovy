package com.justinfield.dependencygraphdemo

import spock.lang.Specification

class DependencyGrapherSpec extends Specification {

    DependencyGrapher dependencyGraphService;

    def setup() {
        dependencyGraphService = new DependencyGrapher()
    }

    def "test that when we specify a valid graph file that the data is loaded"() {
        when:
            dependencyGraphService.loadDependencies('src/test/resources/simple-graph.txt')
            Map<String, Set> deps = dependencyGraphService.getDependencies()
        then:
            deps.size() == 2
            deps.containsKey('A')
            deps.containsKey('B')
        when:
            def a = deps.get('A')
        then:
            a.size() == 2
            a.containsAll(['B', 'C'])
        when:
            def b = deps.get('B')
        then:
            b.size() == 2
            b.containsAll(['C', 'D'])
    }

    def "test that when we print the simple graph we get the output we expect from the example" () {
        setup:
            final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
        when:
            dependencyGraphService.loadDependencies('src/test/resources/simple-graph.txt')
            dependencyGraphService.printGraph('A')
        then:
            outContent.toString() == "" +
                    "A\n" +
                    "|_ B\n" +
                    "|  |_ C\n" +
                    "|  \\_ D\n" +
                    "\\_ C\n"
    }

    def "test that when we define a dependency graph with a loop that do not infinitely loop"() {
        when:
            dependencyGraphService.loadDependencies('src/test/resources/circular-dep.txt')
            dependencyGraphService.printGraph('A')
        then:
            notThrown(StackOverflowError.class)
    }
}
