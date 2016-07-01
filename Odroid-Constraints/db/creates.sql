CREATE TABLE IF NOT EXISTS Solver 
( ID INTEGER PRIMARY KEY,
  SolverName     VARCHAR(100)     NOT NULL
);

INSERT INTO Solver VALUES(1, "Greedy");
INSERT INTO Solver VALUES(2, "MZN-Default");
INSERT INTO Solver VALUES(3, "MZN-Tweaked");

CREATE TABLE IF NOT EXISTS JobResult
   ( ID INTEGER PRIMARY KEY AUTOINCREMENT,
    ProblemId      INTEGER     NOT NULL,    
	nTasks         INTEGER     NOT NULL,
    SolverId       INTEGER NOT NULL,
    Solved         BOOLEAN  NOT NULL, 
    Optimally      BOOLEAN  NOT NULL, 
    SumDist        INTEGER  NOT NULL, 
    MaxDist        INTEGER  NOT NULL, 
    ElapsedSecs    REAL  NOT NULL,
	TimeLimit      INTEGER NOT NULL,
	Odroid         BOOLEAN NOT NULL, 
    FOREIGN KEY(SolverId) REFERENCES Solver(ID)
) ;
