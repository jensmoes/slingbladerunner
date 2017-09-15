# slingbladerunner
Example solution to the Hamiltonian path problem using the beginning and ends of movie titles as the path. 
Multiple words allowed as long as the next title begins with the same words the previous ended on.

Speeds up solution search by creating a 1 bit pre-calculated matrix of linked titles.
Solution is then a traditional Hamilton path search through the matrix.
This allows for solutions with hundreds of nodes in a few minutes on a single laptop core.

## TODO
Clean up code, indentation is awful.
Simplify locking/unlocking of columns to increase efficiency

