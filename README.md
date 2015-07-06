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