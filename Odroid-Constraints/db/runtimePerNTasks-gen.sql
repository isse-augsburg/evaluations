
Create View RuntimeOne as SELECT jr.nTasks, AVG(jr.ElapsedSecs) as MeanSecs, AVG( (jr.ElapsedSecs - sub.a) * (jr.ElapsedSecs - sub.a) ) as VarSecs
FROM JobResult as jr,
( SELECT jr2.nTasks as nTasks2, AVG(jr2.ElapsedSecs) as a from JobResult as jr2 where jr2.SolverId = 1 group by nTasks) as sub
where SolverId = 1 and sub.nTasks2 = jr.nTasks
group by nTasks;

Create View RuntimeTwo as SELECT jr.nTasks, AVG(jr.ElapsedSecs) as MeanSecs, AVG( (jr.ElapsedSecs - sub.a) * (jr.ElapsedSecs - sub.a) ) as VarSecs
FROM JobResult as jr,
( SELECT jr2.nTasks as nTasks2, AVG(jr2.ElapsedSecs) as a from JobResult as jr2 where jr2.SolverId = 2 group by nTasks) as sub
where and SolverId = 2 and sub.nTasks2 = jr.nTasks
group by nTasks;

Create View RuntimeThree as SELECT jr.nTasks, AVG(jr.ElapsedSecs) as MeanSecs, AVG( (jr.ElapsedSecs - sub.a) * (jr.ElapsedSecs - sub.a) ) as VarSecs
FROM JobResult as jr,
( SELECT jr2.nTasks as nTasks2, AVG(jr2.ElapsedSecs) as a from JobResult as jr2 where jr2.SolverId = 3 group by nTasks) as sub
where SolverId = 3 and sub.nTasks2 = jr.nTasks
group by nTasks;

SELECT * FROM
RuntimeOne as ro, RuntimeTwo as rtw, RuntimeThree as rt
where ro.nTasks = rtw.nTasks and rtw.nTasks = rt.nTasks;

 Drop View RuntimeOne; Drop View RuntimeTwo; Drop View RuntimeThree; 
