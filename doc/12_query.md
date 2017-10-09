# ハンズオン - データ操作 of Neo4j

---

## Neo4j Browser（１）

### p29

Cypherクエリの実行は、「Neo4j Browser」を使って行います。

* Neo4j Browserの起動手順
  * Neo4jのコンソールアプリケーション(Neo4jサーバ)を起動
  * http://localhost:7474 へアクセス
    * 認証画面が表示された場合は、以下を入力
      * user ⇒ neo4j
      * password ⇒ インストール時に設定したパスワード

---

## Neo4j Browser（２）

### p30

* 画面最上部の入力欄よりCypherクエリを入力していきます。

* 以下のクエリを入力し、ENTERキーを押下して下さい。（以下の内容をコピーして入力欄へ貼り付ける）
```
MATCH (n) RETURN n
```
（これはNodeを全検索するクエリです。（まだ何も返らないはず））
* 「↑」キーを押下して下さい。
  * ⇒１つ前の入力（MATCH (n) RETURN n）が表示されます。
* このクエリが表示されたままの状態で、右上の★印を押下して下さい。
  * 現在表示中のクエリがお気に入りとして登録されます。
  * これを呼び出すには、画面左端の★印を押下して表示される「Saved Scripts」から選択します。
* もう１つ、以下のクエリを登録して下さい。（以下の内容をコピーして入力欄へ貼り付ける）
```
MATCH (n) DETACH DELETE n
```
（これは、データを全削除するクエリです。（不用意に実行しないで下さい。））

---

## ステートメント - CREATE

### p31

1.（以下の内容をコピーして入力欄へ貼り付ける）
```
CREATE (:Person {name:"長友", age:31})
CREATE (:Person {name:"平", age:32})
CREATE (:Person {name:"本田", age:31})
CREATE (:Person {name:"三瓶", age:41, job:"芸人"})
CREATE (:Person {name:"ガリアルディーニ", age:23})
CREATE (:Team {name:"インテル"})
CREATE (:Team {name:"日本代表"});
```
 クエリ実行後、お気に入りへ登録した「MATCH (n) RETURN n」を実行してNodeの登録を確認する

---

## ステートメント - MATCHとRETURN

### p32

2.
```
MATCH (n) RETURN n
```
3.
```
MATCH (n:Person) RETURN n
```
4.
```
MATCH (n:Person) RETURN n.name, n:age, n.job
```
5.
```
MATCH (n:Person {age:32}) RETURN n.name AS name
```

---

## ステートメント - WHERE

### p33

5.
```
MATCH (n:Person)
 WHERE n.age = 32
 RETURN n.name AS name, n.age AS age
```
6.
```
MATCH (n:Person)
 WHERE n.age > 40
 RETURN n.name AS name, n.age AS age
```

---

## ステートメント - CREATE（２）

### p36

7.
```
CREATE (:Person {name:"香川", age:28})
          -[:MEMBER_OF]->
       (:Team {name:"ドルトムント"})
```
8.
```
MATCH (x:Person {name:"長友"}),
      (y:Person {name:"平"})
  CREATE (y)-[:MARRIED {at:2017}]->(x)
```

---

## ステートメント - CREATE（３）

### p37

9.
```
MATCH (nagatomo:Person {name:"長友"}),
      (taira:Person {name:"平"}),
      (sanpei:Person {name:"三瓶"}),
      (honda:Person {name:"本田"}),
      (gagl:Person {name:"ガリアルディーニ"}),
      (japan:Team {name:"日本代表"}),
      (intel:Team {name:"インテル"})
CREATE (taira)-[:KNOWS]->(sanpei)
CREATE (nagatomo)-[:MEMBER_OF {since:2011}]->(intel)
CREATE (nagatomo)-[:MEMBER_OF {at:[2010,2014]}]->(japan)
CREATE (honda)-[:MEMBER_OF {at:[2010,2014]}]->(japan)
CREATE (gagl)-[:MEMBER_OF {since:2017}]->(intel)
```

---

## ステートメント - SET

### p38

10.
```
MATCH (n:Person {name:"香川"})
 SET n.from = "兵庫"
```

11.
```
MATCH (n:Person {name:"香川"})
 SET n.from = "神戸"
```
---

## ステートメント - REMOVE

### p39

12.
```
MATCH (n:Person {name:"香川"})
 REMOVE n.from
```

---

## ステートメント - DELETE

### p40

13.
```
MATCH (x:Person {name:"香川"})-[r]->
      (y:Team {name:"ドルトムント"})
 DELETE r
```

14.
```
MATCH (n:Person {name:"香川"})
 DELETE n
```

---

## DELETE時の注意事項

### p41

15.
```
MATCH (n:Team {name:"ドルトムント"})
 DETACH DELETE n
```

---

## パスを使って検索する（１）

### p48

16.
```
MATCH (:Person {name:"長友"})-[:MARRIED]-(n)
 RETURN n.name
```

---

## パスを使って検索する（２）

### p49

17.
```
MATCH (:Person {name:"長友"})-[:MEMBER_OF]->
    (:Team)<-[:MEMBER_OF]-(n)
  RETURN n.name
```

18.
```
MATCH (:Person {name:"長友"})-[:MEMBER_OF]->
    (:Team {name:"日本代表"})<-[:MEMBER_OF]-(n)
  RETURN n.name
```

# ハンズオン - CSVアップロード

---

## 次章で使うデータのアップロード

### p62

データを全削除（MATCH (n) DETACH DELETE n）してから実行

19.
```
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
```

20.
```
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/WORKS_FOR.csv" AS line
MATCH (n1:Person {personId:line.personId}), (n2:Company {companyId:line.companyId})
CREATE (n1)-[:WORKS_FOR]->(n2)
UNION
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/WORKED_ON.csv" AS line
MATCH (n1:Person {personId:line.personId}), (n2:Project {projectId:line.projectId})
CREATE (n1)-[:WORKED_ON]->(n2)
UNION
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/INTERESTED_IN.csv" AS line
MATCH (n1:Person {personId:line.personId}), (n2:Interest {interestId:line.interestId})
CREATE (n1)-[:INTERESTED_IN]->(n2)
```

---

# ハンズオン - ソーシャル・グラフ

---

## ①パスの検索

### p73

```
MATCH p=(i:Interest)<--(n:Person) RETURN p
```

1.
```
MATCH (i:Interest)<--(n:Person) 
  RETURN i.name, n.name
```

---

## 集約関数 - COLLECT

### p74

2.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name, n.name, COLLECT(n.name)
```

3.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name,         COLLECT(n.name)
```

---

## 集約関数 - COUNT

### p75

4.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name,         COLLECT(n.name), COUNT(n.name)
```

5.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name,         COLLECT(n.name), COUNT(i.name)
```

---

## ②"趣味"ごとにパス数を集計

### p76

6.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name, COUNT(i.name)
```

---

## ORDER BY 句

### p77

7.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name, COUNT(i.name)
           ORDER BY COUNT(i.name) DESC
```

8.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name AS intr, COUNT(i.name) AS rank
           ORDER BY rank DESC
```

---

## LIMIT 句

### p78

9.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name AS intr, COUNT(i.name) AS rank
           ORDER BY rank DESC
           LIMIT 3
```

---

## WITH 句を使い、クエリを整理

### p79

10.
```
MATCH (i:Interest)<--(n:Person)
  WITH i.name AS intr
  RETURN intr, COUNT(intr) AS rank
           ORDER BY rank DESC
           LIMIT 3
```

