
Create View if not exists RuntimeOne as SELECT jr.nTasks, AVG(jr.ElapsedSecs) as MeanSecs, AVG( (jr.ElapsedSecs - sub.a) * (jr.ElapsedSecs - sub.a) ) as VarSecs
FROM JobResult as jr,
( SELECT jr2.nTasks as nTasks2, AVG(jr2.ElapsedSecs) as a from JobResult as jr2 where jr2.TimeLimit = 60 and jr2.SolverId = 1 group by nTasks) as sub
where TimeLimit = 60 and SolverId = 1 and sub.nTasks2 = jr.nTasks
group by nTasks;

Create View if not exists  RuntimeTwo as SELECT jr.nTasks, AVG(jr.ElapsedSecs) as MeanSecs, AVG( (jr.ElapsedSecs - sub.a) * (jr.ElapsedSecs - sub.a) ) as VarSecs
FROM JobResult as jr,
( SELECT jr2.nTasks as nTasks2, AVG(jr2.ElapsedSecs) as a from JobResult as jr2 where jr2.TimeLimit = 60 and jr2.SolverId = 2 group by nTasks) as sub
where TimeLimit = 60 and SolverId = 2 and sub.nTasks2 = jr.nTasks
group by nTasks;

Create View if not exists  RuntimeThree as SELECT jr.nTasks, AVG(jr.ElapsedSecs) as MeanSecs, AVG( (jr.ElapsedSecs - sub.a) * (jr.ElapsedSecs - sub.a) ) as VarSecs
FROM JobResult as jr,
( SELECT jr2.nTasks as nTasks2, AVG(jr2.ElapsedSecs) as a from JobResult as jr2 where jr2.TimeLimit = 60 and jr2.SolverId = 3 group by nTasks) as sub
where TimeLimit = 60 and SolverId = 3 and sub.nTasks2 = jr.nTasks
group by nTasks;

SELECT * FROM
RuntimeOne as ro;

-- Drop View RuntimeOne; Drop View RuntimeTwo; Drop View RuntimeThree; 
