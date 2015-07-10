package com.justinfield.dependencygraphdemo

/**
 * Class with main method that I can use in a gradle Java Exec Task execute the DependencyGrapher
 *
 * If this wasn't just a demo, I could easily have this all be a simple groovy script that utilizes Groovy's CLI Builder
 * Or I could create a gradle plugin if this utility is meant to be used during builds or with projects
 *
 * However this will suffice for now and will allow us to create tasks via Gradle's JavaExec type.
 * I chose this way so I could easily test the code using Spock in a format people are most likely used to seeing
 *
 * task printGraph(type: JavaExec) {
 *      dependsOn 'classes'
 *      classpath = sourceSets.main.runtimeClasspath
 *      main = 'com.justinfield.dependencygraphdemo.CLI'
 *
 *      systemProperties = [
 *          'filePath': System.getProperty('filePath', 'src/test/resources/graph.txt'),
 *          'startNode': System.getProperty('startNode', 'A')
 *      ]
 * }
 *
 */
class CLI {
    static void main(String [] args) {
        DependencyGrapher dependencyGrapher = new DependencyGrapher()
        String filePath = System.getProperty('filePath')
        String startNode = System.getProperty('startNode')
        println "Loading graph data from: $filePath"
        dependencyGrapher.loadDependencies(filePath)
        dependencyGrapher.printGraph(startNode)
    }
}
