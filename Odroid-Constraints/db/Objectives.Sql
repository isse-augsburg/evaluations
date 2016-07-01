
Create View if not exists ObjOne as Select ProblemId, nTasks, SolverId, MaxDist 
From JobResult
where Solved and SolverId=1;

Create View if not exists ObjTwo as Select ProblemId, nTasks, SolverId, MaxDist 
From JobResult
where Solved and SolverId=2;

Create View if not exists ObjThree as Select ProblemId, nTasks, SolverId, MaxDist 
From JobResult
where Solved and SolverId=3;

Create View If not Exists ObjectiveComparison as Select oo.ProblemId, oo.nTasks, 
       oo.SolverId, oo.MaxDist as MaxDistOne,
	   otw.SolverId, otw.MaxDist as MaxDistTwo,
	   ot.SolverId, ot.MaxDist as MaxDistThree,
	   1.0 - (1.0*oo.MaxDist / ot.MaxDist) as RelImpr
From ObjOne as oo,
     ObjTwo as otw,
	 ObjThree as ot
where oo.ProblemId = otw.ProblemId and otw.ProblemId = ot.ProblemId;

SELECT * FROM 
SolveStats,
(SELECT nTasks, AVG(RelImpr) FROM ObjectiveComparison  Group By nTasks) as AvgImpr
where AvgImpr.nTasks = SolveStats.nTasks;
