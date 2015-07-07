package com.justin_field.dependencygraphdemo

/**
 * Class with main method that I can use in a gradle Java Exec Task execute the DependencyGrapher
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
