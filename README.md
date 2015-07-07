# dependency-graph-demo
Given a dependency graph in the form of a file that contains entries '"X->Y"', stating that X depends on Y. separated by newlines, prints a hierarchical view of the dependencies.

If A depends on B and C, and B depends on C and D 

```
A->B
A->C
B->C
B->D
```

The output should look like this:

```
A
|_ B
| |_ C
| \_ D
\_ C
```


## Usage
1. Clone the repo
2. Run the following gradle task

```
./gradlew printGraph
```

This defaults to filePath=src/test/resources/graph.txt and startNode=A
If you wish to load a different file or start with another node simply provide the override system properties like so.

```
./gradlew printGraph -DfilePath=src/test/resources/circular-dep.txt -DstartNode=D
```
