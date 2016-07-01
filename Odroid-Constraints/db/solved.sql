

Create View If not exists SolvedOne as SELECT jr.nTasks, AVG(jr.Solved) as Solved
FROM JobResult as jr
where SolverId = 1
group by nTasks;

Create View If not exists SolvedTwo as SELECT jr.nTasks, AVG(jr.Solved) as Solved
FROM JobResult as jr
where SolverId = 2
group by nTasks;

CREATE VIEW If not exists SolveStats as Select co.nTasks, co.Solved, ct.Solved FROM 
SolvedOne as co, SolvedTwo as ct
Where co.nTasks = ct.nTasks;

SELECT * FROM SolveStats;