
# ハンズオン - ソーシャル・グラフ

---

## Warm Up - 解答例

### p93

(1)「Benの年齢は？」
```
MATCH (n:Person {name:"Ben"}) RETURN n.name, n.age
```
* Label名やType名は大文字・小文字を区別するので、例えば`(n:person)`はNGです。
* Benは文字列ですので、name:Benではうまくいきません。

(2)「Benの関心事は？」
```
MATCH (:Person {name:"Ben"})-[:INTERESTED_IN]->(i:Interest) 
  RETURN i.name
```
* `(:Person {name:"Ben"})`を`(n:Person {name:"Ben"})`としてもOKです。
* 但し、例えば`MATCH (i:Person {name:"Ben"})-[:INTERESTED_IN]->(i:Interest)`としてしまうと、開始ノードと終了ノードが同じとみなされうまくいきません。

(3)「Benと同じプロジェクトで働く人は？」
```
MATCH (:Person {name:"Ben"})-[:WORKED_ON]->()
    <-[:WORKED_ON]-(coworker:Person) 
  RETURN coworker.name
```

---
