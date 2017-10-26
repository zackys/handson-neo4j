# ハンズオン - ソーシャル・グラフ

---

## 共通の関心事を持つ同僚は？

### p105

①"Ben"と共通の関心事を持つ同僚は？
```
MATCH (n:Person {name:"Ben"})
  WITH n
MATCH (n)-[:INTERESTED_IN]->()<-[:INTERESTED_IN]-(x),
      (n)-[:WORKED_ON]->()<-[:WORKED_ON]-(x)
  RETURN x
```

②その中で、より多く共通の関心事を持つ同僚は？（①の結果を、共通の関心事の数でランク付ける）
```
MATCH (n:Person {name:"Ben"})
  WITH n
MATCH (n)-[:INTERESTED_IN]->(i)<-[:INTERESTED_IN]-(x),
      (n)-[:WORKED_ON]->(p)<-[:WORKED_ON]-(x)
  WITH i.name AS intr, x.name AS name
  RETURN name, COUNT(intr) AS rank, COLLECT(intr)

```
* 最後の`COLLECT(intr)`は分かりやすくするために追加しましたが、なくてもOKです。

---
