# ハンズオン - データ操作 of Neo4j

---

## Neo4j Browser（１）

### p49

Cypherクエリの実行は、「Neo4j Browser」を使って行います。

* Neo4j Browserの起動手順
  * Neo4jのコンソールアプリケーション(Neo4jサーバ)を起動
  * http://localhost:7474 へアクセス
    * 認証画面が表示された場合は、以下を入力
      * user ⇒ neo4j
      * password ⇒ インストール時に設定したパスワード

---

## Neo4j Browser（２）

### p50

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

### p51

1.（以下の内容をコピーして入力欄へ貼り付ける）
```
CREATE (:Person {name:"長友", age:31})
CREATE (:Person {name:"平", age:32})
CREATE (:Person {name:"本田", age:31})
CREATE (:Person {name:"三瓶", age:41, job:"芸人"})
CREATE (:Person {name:"ガリアルディーニ", age:23})
CREATE (:Team {name:"インテル"})
CREATE (:Team {name:"日本代表"})
```
 クエリ実行後、お気に入りへ登録した「MATCH (n) RETURN n」を実行してNodeの登録を確認する

---

## ステートメント - MATCHとRETURN

### p52

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
MATCH (n:Person) RETURN n.name, n.age, n.job
```
5.
```
MATCH (n:Person {age:32}) RETURN n.name AS name
```

---

## ステートメント - WHERE

### p53

6.
```
MATCH (n:Person)
 WHERE n.age = 32
 RETURN n.name AS name, n.age AS age
```
7.
```
MATCH (n:Person)
 WHERE n.age > 40
 RETURN n.name AS name, n.age AS age
```

---

## ステートメント - CREATE（２）

### p54・55

8.
```
CREATE (:Person {name:"香川", age:28})
          -[:MEMBER_OF]->
       (:Team {name:"ドルトムント"})
```
9.
```
MATCH (x:Person {name:"長友"}),
      (y:Person {name:"平"})
  CREATE (y)-[:MARRIED {at:2017}]->(x)
```

---

## ステートメント - CREATE（３）

### p56

10.
```
MATCH (nagatomo:Person {name:"長友"}),
      (taira:Person {name:"平"}),
      (sanpei:Person {name:"三瓶"}),
      (honda:Person {name:"本田"}),
      (kagawa:Person {name:"香川"}),
      (gagl:Person {name:"ガリアルディーニ"}),
      (japan:Team {name:"日本代表"}),
      (intel:Team {name:"インテル"})
CREATE (taira)-[:KNOWS]->(sanpei)
CREATE (nagatomo)-[:MEMBER_OF {since:2011}]->(intel)
CREATE (nagatomo)-[:MEMBER_OF {at:[2010,2014]}]->(japan)
CREATE (honda)-[:MEMBER_OF {at:[2010,2014]}]->(japan)
CREATE (kagawa)-[:MEMBER_OF {at:[2014]}]->(japan)
CREATE (gagl)-[:MEMBER_OF {since:2017}]->(intel)
```
クエリ実行後、お気に入りへ登録した「MATCH (n) RETURN n」を実行してRelationshipの作成を確認する

---

## ステートメント - SET

### p57

11.
```
MATCH (n:Person {name:"香川"})
 SET n.from = "兵庫"
```

12.
```
MATCH (n:Person {name:"香川"})
 SET n.from = "神戸"
```
---

## ステートメント - REMOVE

### p58

13.
```
MATCH (n:Person {name:"香川"})
 REMOVE n.from
```

---

## ステートメント - DELETE

### p59

14.
```
MATCH (x:Person {name:"香川"})-[r]->
      (y:Team {name:"ドルトムント"})
 DELETE r
```

15.
```
MATCH (n:Team {name:"ドルトムント"})
 DELETE n
```

---

## DELETE時の注意事項

### p60

16.
```
MATCH (n:Person {name:"香川"})
        DELETE n
```

17.
```
MATCH (n:Person {name:"香川"})
 DETACH DELETE n
```

---

## パスを使って検索する（１）

### p70

18.
```
MATCH (:Person {name:"長友"})-[:MARRIED]-(n)
 RETURN n.name
```

---

## パスを使って検索する（２）

### p71

19.
```
MATCH (:Person {name:"長友"})-[:MEMBER_OF]->
    (:Team)<-[:MEMBER_OF]-(n)
  RETURN n.name
```

20.
```
MATCH (:Person {name:"長友"})-[:MEMBER_OF]->
    (:Team {name:"日本代表"})<-[:MEMBER_OF]-(n)
  RETURN n.name
```

# ハンズオン - CSVアップロード

---

## LOAD CSV を試す

### p84

#### 21. GitHub上のPerson.csvの内容を確認する
* https://github.com/zackys/handson-neo4j/blob/master/import/social/Person.csv

#### 22. ヘッダ付きCSVファイルのLOADと、行要素へのアクセス
```
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/Person.csv" AS line
  RETURN line
```
 ⇒ `RETURN line`は、JSON形式で返ります

```
LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/Person.csv" AS line
  RETURN line.id, line.name, line.age
```
 ⇒ `line.id`などは、JSONの要素を参照する形式です

#### 23. 【参考】同じファイルを"ヘッダなしCSVファイル"として読み込んだ場合、どうなるか？
```
LOAD CSV              FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/Person.csv" AS line
  RETURN line
```
 ⇒ `RETURN line`は、(文字)配列形式で返ります。（読み込んだファイルがヘッダありのため、ヘッダもデータ行として読み込まれます。）

```
LOAD CSV              FROM "https://raw.githubusercontent.com/zackys/handson-neo4j/master/import/social/Person.csv" AS line
  RETURN line[0], line[1], line[2]
```
 ⇒ `line[0]`などは、配列の要素を参照する形式です

---

## 次章で使うデータのアップロード

### p85

データを全削除（MATCH (n) DETACH DELETE n）してから実行

24.
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

25.
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

![social01](https://user-images.githubusercontent.com/5683857/31471942-948e6464-af26-11e7-8005-6d7d244545a3.jpg)

---

## Warm Up

### p92

(1)「Benの年齢は？」

(2)「Benの関心事は？」

(3)「Benと同じプロジェクトで働く人は？」

[解答例](https://github.com/zackys/handson-neo4j/blob/master/doc/21_WarmUp.md)
---

## ①パスの検索

### p96

0. 
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

### p97

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

### p98

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

### p99

6.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name, COUNT(i.name)
```

---

## ORDER BY 句

### p100

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

### p101

9.
```
MATCH (i:Interest)<--(n:Person)
  RETURN i.name AS intr, COUNT(i.name) AS rank
           ORDER BY rank DESC
           LIMIT 3
```

---

## WITH 句を使い、クエリを整理

### p102

10.
```
MATCH (i:Interest)<--(n:Person)
  WITH i.name AS intr
  RETURN intr, COUNT(intr) AS rank
           ORDER BY rank DESC
           LIMIT 3
```

---

## 共通の関心事を持つ同僚は？

### p103・104

①"Ben"と共通の関心事を持つ同僚は？
```
MATCH (n:Person {name:"Ben"})
  WITH n
MATCH (n
```

②その中で、より多く共通の関心事を持つ同僚は？（①の結果を、共通の関心事の数でランク付ける）
```
MATCH (n:Person {name:"Ben"})
  WITH n
MATCH (n

```

参考：[11_CheatSheet.pdf](https://github.com/zackys/handson-neo4j/blob/master/doc/11_CheatSheet.pdf)

[解答例](https://github.com/zackys/handson-neo4j/blob/master/doc/22_CoworkerWithSameInterests.md)


