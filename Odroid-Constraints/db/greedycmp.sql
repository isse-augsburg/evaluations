Drop View CompAvg; Drop View GreedyComp;

Create View If Not Exists GreedyComp as 
SELECT jr1.ProblemId, jr1.nTasks, jr1.MaxDist as DistMzn, jr2.MaxDist as DistGreedy, jr2.MaxDist - jr1.MaxDist as Diff, (jr2.MaxDist - jr1.MaxDist)/10.0 as DiffSeconds FROM 
JobResult as jr1,
JobResult as jr2 
Where jr1.SolverID = 1 and jr2.SolverID = 2
and jr1.ProblemId = jr2.ProblemID;

Create View If Not Exists CompAvg as 
SELECT nTasks, AVG(Diff) as ADiff, AVG(DiffSeconds)  as ADiffSeconds
FROM GreedyComp
GROUP by nTasks;

SELECT CA.nTasks as nTasks, AVG(Diff) as ADiff, AVG ( (Diff - ADiff) * (Diff - ADiff)) as DiffVar,
       AVG(DiffSeconds) as ADiffSeconds, AVG ( (DiffSeconds - ADiffSeconds) * (DiffSeconds - ADiffSeconds)) as DiffSecondsVar
FROM GreedyComp, CompAvg as CA
Where GreedyComp.nTasks = CA.nTasks
GROUP BY CA.nTasks