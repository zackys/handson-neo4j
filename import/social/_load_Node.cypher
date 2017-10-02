LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/Person.csv" AS line
CREATE (:Person {personId:line.id, name:line.name, age:line.age})
UNION
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/Company.csv" AS line
CREATE (:Company {companyId:line.id, name:line.name})
UNION
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/Project.csv" AS line
CREATE (:Project {projectId:line.id, name:line.name})
UNION
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/Interest.csv" AS line
CREATE (:Interest {interestId:line.id, name:line.name})
