Create View if not exists Avgs as SELECT nTasks, AVG(MaxDistOne) as AOne, AVG(MaxDistTwo) as ATwo,  AVG(MaxDistThree) as AThree FROM ObjectiveComparison as OC
GROUP BY nTasks;

SELECT OC.nTasks, AVG(MaxDistOne),
        AVG ( (MaxDistOne - AOne)*(MaxDistOne-AOne) )   as StdOne, 
        AVG ( (MaxDistTwo - ATwo)*(MaxDistTwo - ATwo) ) as StdTwo,
		AVG ( (MaxDistThree - AThree)*(MaxDistThree-AThree) ) as StdThree
FROM ObjectiveComparison as OC,
     Avgs
Where Avgs.nTasks = OC.nTasks;
GROUP BY nTasks

Drop View Avgs;
