package com.justin_field.dependencygraphdemo

class DependencyGrapher {

    Map<String, Set> dependencies = [:] as HashMap

    /**
     * Given a path to a valid file, this will load the data as a map of dependencies keyname to a sets of dependencies
     * @param filePath
     */
    void loadDependencies(String filePath) {
        File graphData = new File(filePath)

        if (! graphData.exists()) {
            throw new IllegalArgumentException("File: $filePath does not exist")
        }

        graphData.eachLine { String relationship ->
            relationship.find(/^(\w+)->(\w+)$/) { String fullMatch, String key, String dependency ->
                if (! dependencies.containsKey(key)) {
                    dependencies.put(key, [dependency] as HashSet)
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
    void printGraph(String node, def depth = 0, def visited = [] as Set) {
        if (!dependencies.containsKey(node)) {
            throw new IllegalArgumentException("Node: $node, is not in the dependency map")
        }

        visited.add(node)
        if (depth == 0) {
            println node
        } else if (depth > 0) {
            print('|  ' * (depth - 1))
            println("|_ $node")
        }

        Set<String> deps = dependencies.get(node)

        // lets sort the set alphabetically, under the assumption that this is a requirement
        deps = deps.sort()

        deps.each { String dependency ->
            if (dependencies.containsKey(dependency) && ! visited.contains(dependency)) {
                printGraph(dependency, depth + 1, visited)
            } else {
                print('|  ' * depth)
                if (dependency != deps.last()) {
                    println("|_ $dependency")
                } else {
                    println("\\_ $dependency")
                }
            }
        }
    }
}
