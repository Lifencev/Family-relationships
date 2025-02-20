-- create
CREATE TABLE family_tree (
  id INT PRIMARY KEY,
  name VARCHAR(255),
  gender Boolean,
  mother_id INT,
  father_id INT,
  FOREIGN KEY (mother_id) REFERENCES family_tree(id),
  FOREIGN KEY (father_id) REFERENCES family_tree(id)
);

-- insert
INSERT INTO family_tree (id, name, gender, mother_id, father_id)
VALUES
(10, 'Sarah', True, NULL, NULL),
(9, 'Catlyn', True, NULL, NULL),
(8, 'Alex', False, NULL, NULL),
(7, 'John', False, NULL, NULL),
(6, 'Jane', True, NULL, NULL),
(5, 'Jim', False, 6, 7),
(4, 'Anna', True, NULL, 5),
(3, 'Tom', False, NULL, 5),
(2, 'Peter', False, 4, NULL),
(1, 'Mary', True, NULL, 3);

-- get all people
SELECT *
FROM family_tree
ORDER BY id ASC;

-- get parents and children
SELECT parent.name AS Parent,
       child.name AS Child
FROM family_tree AS parent
JOIN family_tree AS child ON child.mother_id = parent.id OR
child.father_id = parent.id;

-- get mother
SELECT name
FROM family_tree
WHERE gender = True 
AND id = (SELECT mother_id
          FROM family_tree
          WHERE id = 5
          );

-- get father      
SELECT name
FROM family_tree
WHERE gender = False 
AND id = (SELECT father_id
          FROM family_tree
          WHERE id = 5
          );

-- get brothers
SELECT ft2.name AS brother_name
FROM family_tree ft1
JOIN family_tree ft2 ON ft1.father_id = ft2.father_id OR ft1.mother_id = ft2.mother_id
WHERE ft1.id = 4
  AND ft2.gender = FALSE
  AND ft2.id != ft1.id;

-- get sisters
SELECT ft2.name AS sister_name
FROM family_tree ft1
JOIN family_tree ft2 ON ft1.father_id = ft2.father_id OR ft1.mother_id = ft2.mother_id
WHERE ft1.id = 3
  AND ft2.gender = TRUE
  AND ft2.id != ft1.id;

-- get ancestors
WITH RECURSIVE Ancestors AS (
    SELECT id, name, mother_id, father_id, CAST(id AS CHAR(255)) AS path
    FROM family_tree
    WHERE id = 5
    UNION ALL
    SELECT ft.id, ft.name, ft.mother_id, ft.father_id, CONCAT(a.path, ',', ft.id)
    FROM family_tree ft
    JOIN Ancestors a ON ft.id = a.mother_id OR ft.id = a.father_id
    WHERE FIND_IN_SET(ft.id, a.path) = 0
),
ancestors_without_self AS (
    SELECT *
    FROM ancestors
    WHERE id != 5
)
SELECT name
FROM ancestors_without_self;

-- get descendants
WITH RECURSIVE descendants AS (
    SELECT *
    FROM family_tree
    WHERE id = 5
    UNION ALL
    SELECT ft.*
    FROM family_tree ft
    JOIN descendants d ON (d.id = ft.mother_id OR d.id = ft.father_id)
),
descendants_without_self AS (
    SELECT *
    FROM descendants
    WHERE id != 5
)
SELECT name
FROM descendants_without_self;
