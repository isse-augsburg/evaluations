-- Drop View ClosedOne; Drop View ClosedTwo;

Create View If not exists ClosedOne as SELECT jr.nTasks, AVG(jr.Optimally) as Closed,  Sum(jr.Optimally) as NClosed
FROM JobResult as jr
where TimeLimit = 60 and SolverId = 1
group by nTasks;

Create View If not exists ClosedTwo as SELECT jr.nTasks, AVG(jr.Optimally) as Closed,  Sum(jr.Optimally) as NClosed
FROM JobResult as jr
where TimeLimit = 60 and SolverId = 2
group by nTasks;

Select ct.nTasks, ct.Closed, ct.NClosed, ss.solved FROM 
ClosedOne as ct,
SolveStats as ss
where ct.nTasks = ss.nTasks

