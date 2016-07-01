int: n;
set of int: AGENT = 1..n;
set of int: TASK = 1..n;

% input distance (costs)
array[AGENT, TASK] of int: agentTaskDistance;

int: maxDist = max(a in AGENT, t in TASK) (agentTaskDistance[a,t]);
int: upperBound; % max(a in AGENT, t in TASK) (agentTaskDistance[a,t]);
 
array[AGENT] of var 0..maxDist: agentShare = [ agentTaskDistance[a, agentToTask[a] ] | a in AGENT ];
 
% sum up the total distance of the collective to the task
var int: totalDistance = sum(agentShare) ;

%array[AGENT] of agentTaskDistance[a, agentToTask[a] ]

var 0..upperBound: maxDistance = max(agentShare) ;

array[AGENT] of var TASK: agentToTask; % agentToTask[a] returns a's task

include "alldifferent.mzn";
constraint alldifferent(agentToTask);

%constraint agentToTask = [1,2];

solve 
% order by the worst case distance
%:: int_search(agentShare, dom_w_deg, indomain_min, complete)
:: int_search(agentShare, largest, indomain_min, complete)
minimize maxDistance;

%output["worstCaseDistance \(worstCaseDistance)"];

output ["maxDist:\(maxDistance)\nsumDist:\(totalDistance)" ];
% nice formatted version
% output[ "TotalDistance: \(totalDistance)\nAgentToTask: \(agentToTask)\nTaskToAgent: \(taskToAgent)" ];
