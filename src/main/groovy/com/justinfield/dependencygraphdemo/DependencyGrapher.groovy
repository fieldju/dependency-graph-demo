package com.justinfield.dependencygraphdemo

/**
 * Class containing the methods needed to read a file of relationship data and print the dependency graph of a given node.
 */
class DependencyGrapher {

    Map<String, Set> dependencies = [:]

    /**
     * Given a path to a valid file, this will load the data as a map of dependencies keyname to a set of dependencies
     *
     * ex:
     * A->B
     * A->C
     * B->C
     * B->D
     *
     * Will create the following map
     *
     * [
     *      A: [B, C]
     *      B: [C, D]
     * ]
     *
     * @param filePath, the path to the file containing the relationship data in the form of new line separated X->Y
     */
    void loadDependencies(String filePath) {
        File graphData = new File(filePath)

        if (! graphData.exists()) {
            throw new IllegalArgumentException("File: $filePath does not exist")
        }

        graphData.eachLine { String relationship ->
            relationship.find(/^(\w+)->(\w+)$/) { String fullMatch, String key, String dependency ->
                if (! dependencies.containsKey(key)) {
                    dependencies.put(key, [dependency] as Set)
                } else {
                    Set keyDependencies = dependencies.get(key)
                    keyDependencies.add(dependency)
                }
            }
        }
    }

    /**
     * Given a starting node and a map of nodes and there dependencies sets this method will
     * recurse through the map of deps recursively printing the dependency tree
     * @param node the node to process
     * @param dependencies, the map of keys and there dependencies
     * @param depth, the current recurse depth, used to pretty print the tree.
     * @param visited, we need to keep track of were we have been to avoid infinite loops
     */
    void printGraph(String node, def depth = 0, Set visited = [], Set dontPrintSet = []) {
        if (!dependencies.containsKey(node)) {
            throw new IllegalArgumentException("Node: $node, is not in the dependency map")
        }

        if (depth == 0) {
            println node
        } else if (depth > 0) {
            (depth - 1).times { print dontPrintSet.contains(it) ? '   ' : '|  '}
            println("|_ $node")
        }

        Set<String> deps = dependencies.get(node)

        // lets sort the set alphabetically, under the assumption that this is a requirement
        deps = deps.sort()

        deps.each { String dependency ->
            // I made and assumption that if we detect a loop that we can just print the looped dep and not recurse
            if (dependencies.containsKey(dependency) && ! visited.contains(dependency)) {
                // lets keep track of the depths that dont have anymore nodes when we recurse
                if (deps.last() == dependency) {
                    dontPrintSet.add(depth)
                }
                // lets keep track of where we have been when we recurse
                visited.add(node)

                printGraph(dependency, depth + 1, visited, dontPrintSet)

                // when we exit the recursive call lets remove the node from where we have been
                visited.remove(node)
                // and remove the depth from the dont print set
                dontPrintSet.remove(depth + 1)
            } else {
                depth.times { print dontPrintSet.contains(it) ? '   ' : '|  '}
                if (dependency != deps.last()) {
                    print("|_ $dependency")
                } else {
                    print("\\_ $dependency")
                }
                println visited.contains(dependency) ? '- circular dependency detected' : ''
            }
        }
    }
}
