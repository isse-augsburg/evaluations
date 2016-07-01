# Constraint Solving on Odroids

Performance test to evaluate odroids' computational power for constraint solving.
To run the evaluation, make sure to do so on Linux and have [MiniZinc] installed.

The evaluations can be running using the command:
```
java -jar runeval.jar [experiment.properties]
```
and the resulting "results.ser" file can be transferred to an SQLite-DB using
```
java -jar deserialize.jar [resultsfile.ser] [sqlite.db]
```
after creating the necessary tables using db/creates.sql

### Project overview

* **src/de/isse/RunEvaluation.java** contains the main method
* **db/** contains database code to extract information from the runs
* **db/plots** contains the IPython code used to generate plots and tables



   [MiniZinc]: <http://www.minizinc.org/>
