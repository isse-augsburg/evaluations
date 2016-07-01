int: n;
set of int: AGENT = 1..n;
set of int: TASK = 1..n;

% input distance (costs)
array[AGENT, TASK] of int: agentTaskDistance;

int: upperBound; % max(a in AGENT, t in TASK) (agentTaskDistance[a,t]);
 
% sum up the total distance of the collective to the task
var int: totalDistance = sum(a in AGENT) (agentTaskDistance[a, agentToTask[a] ] );
var 0..upperBound: maxDistance = max(a in AGENT) (agentTaskDistance[a, agentToTask[a] ] );

array[AGENT] of var TASK: agentToTask; % agentToTask[a] returns a's task
array[TASK] of var AGENT: taskToAgent; % taskToAgent[t] returns the agent that does t

include "inverse.mzn";
constraint inverse(agentToTask, taskToAgent);

%constraint agentToTask = [1,2];

solve 
% order by the worst case distance
minimize maxDistance;

%output["worstCaseDistance \(worstCaseDistance)"];

output ["maxDist:\(maxDistance)\nsumDist:\(totalDistance)" ];
% nice formatted version
% output[ "TotalDistance: \(totalDistance)\nAgentToTask: \(agentToTask)\nTaskToAgent: \(taskToAgent)" ];
