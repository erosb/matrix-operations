Matrix Operations
=================

This project is intended to serve as an example of implementing some basic matrix-related
computations in Java. Originally it was my homework in subject "numerical analysis" at
University of Debrecen. Later I decided to make it public since some people asked me to
give some examples on Java programming. I hope the code quality is fairly good, although
it contains some duplications and some dead code too. Recently I worked on the API docs,
but it really needs further improvements, especially the "expansion by minors" determinant
computation alorithm.

The software can perform the following operations:

* calculate the inverse of a given matrix
* calculate the determinant of a given matrix
* calculate the condition number of a given matrix
	
Although this project is used as a kind of example on Java programming in general sadly
it is math-related (to be honest I really hate math). I learned the underlying theory
from the ebook titled "Diszkrét matematika II" written by Sándor Bácsó at the University
of Debrecen, but the following resources are also easy to understand (and available in
English):

	
* matrix inversion: http://www.purplemath.com/modules/mtrxinvr.htm
* matrix determinant calculation:
	- the Rule of Sarrus for 2x2 and 3x3 matrices: http://en.wikipedia.org/wiki/Rule_of_Sarrus
	- matrix expansion by minors for bigger matrices: http://www.intmath.com/matrices-determinants/1-determinants.php
* matrix condition number: http://en.wikipedia.org/wiki/Condition_number#Matrices
	

Brief guidelines for examining the code
---------------------------------------

The inverse and condition number calculations are fairly straightforward, the matrix.Main
class methods read the matrices from the standard input ("command line") then the actual
calculations are done by the getInverted() and getCondition() methods.To be honest I didn't
spend too much time on implementing these, therefore both uses cloning of the initial matrices,
probably some less memory consumptive implementations are also possible.

The determinant calculation is a bit more complex. If you want to calculate the determinant
of a matrix then first you have to get a DeterminantProcessor instance using
DeterminantProcessorFactory.getDeterminantProcessor() . This will return the DeterminantProcessor
instance which seems to be the best for the given matrix. In the cases of 2x2 and 3x3
matrices a DeterminantProcessorSarus instance will be returned, otherwise a
DeterminantProcessorExpansion instace. The first one is still trivial, since the rule of
Sarrus is very simple to implement in any programming languages. The expansion by minors
algorithm is a more complex topic.

Misc
----

* unittests may be useful for understanding
* the software can be used as a class library too - but I'm fairly sure some much
		better quality java libs are available for these tasks.
