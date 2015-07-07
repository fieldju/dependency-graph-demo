# dependency-graph-demo
Given a dependency graph in the form of a file that contains entries '"X­>Y"', stating that X depends on Y. seperated by newlines, prints a hierarchical view of the dependencies.

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

to run clone the repo and simply run the following gradle task

```
./gradlew printGraph
```

which defaults to loading the data in graph.txt and starting on node A.
However if you wish to load a different file or start with another node simply provide the override system properties like so.

```
./gradles printGraph -DfilePath=src/test/resources/circular-dep.txt -DstartNode=D
```