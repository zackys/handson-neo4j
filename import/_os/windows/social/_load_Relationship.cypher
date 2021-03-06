LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/_os/windows/social/WORKS_FOR.csv" AS line
MATCH (n1:Person {personId:line.personId}), (n2:Company {companyId:line.companyId})
CREATE (n1)-[:WORKS_FOR]->(n2)
UNION
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/_os/windows/social/WORKED_ON.csv" AS line
MATCH (n1:Person {personId:line.personId}), (n2:Project {projectId:line.projectId})
CREATE (n1)-[:WORKED_ON]->(n2)
UNION
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/_os/windows/social/INTERESTED_IN.csv" AS line
MATCH (n1:Person {personId:line.personId}), (n2:Interest {interestId:line.interestId})
CREATE (n1)-[:INTERESTED_IN]->(n2)
