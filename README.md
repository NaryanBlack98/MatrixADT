# MatrixADT
A general matrix ADT that supports the creation, addition, subtracting, and multiplication of matrices. 

1. List.java:
    This file is the ADT specified for assignment 1. It contains the Node class with three constructors: (next, previous and data), and all of the specified constructors for the List class. Contains all of the access functions and manipulation procedures.
The List class is called upon by:
    e.g. List P = new List();
    
2. Matrix.java
    This file is an ADT that calls upon List.java to create a Matrix. Matrix has an inner construct called "row" that is an array of Lists. Matrix has an inner class called Entry that has two constructs, col (column) and value (type double).
    
3. Sparse.java
    Sparse is a function that reads in an input file, builds the two matrices from that file and does all of the specified functions to those matrices. Their responses are outputted to a file. The function is called like,
    
