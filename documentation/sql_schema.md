## Creating SQL commands for generating MySQL Schema

[Create schema SQL file](../config/src/main/resources/sql/create-schema.sql) 

Every time we run script database will be recreated. 
This is will be needed to get default database state with 
static inserted values.  

- ```DROP SCHEMA IF EXISTS `database_name` ``` - if database exists, drops all tables in the database and deletes the database. 

- ```CREATE SCHEMA `database_name` ``` - creates a new database.

```sql
DROP SCHEMA IF EXISTS `project_management`;

CREATE SCHEMA `project_management`;
```

- ```use `database_name` ``` - creating a database does not select it for use, 
you must do that explicitly.

```sql
use `project_management`;
```

Temporarily disabling referential constraints is useful 
when you need to re-create the tables and load data in any 
parent-child order.
-  ```FOREIGN_KEY_CHECKS = 0``` - do not check foreign key constraints.
 
- ```FOREIGN_KEY_CHECKS = 1``` - check foreign key constraints.
  
```sql
SET FOREIGN_KEY_CHECKS = 0;
```
enable it when you’re done:
```sql
SET FOREIGN_KEY_CHECKS = 1;
```

##### Creating table.
- ```DROP TABLE IF EXISTS  `table_name` ``` - if table exists, drops table in the database. 

- ```CREATE TABLE `table_name` ``` - creates a new table.

```sql
DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
    -- Table columns 
)
```

##### Creating table column.

```sql
 `column_name` data_type(length) [NOT NULL]  [DEFAULT value] [AUTO_INCREMENT] 
  column_constraint;
```

##### Here are the details:

- The ```column_name``` specifies the name of the column. Each column has a specific data type and optional size e.g. ``` VARCHAR(255)  ```

- The ```NOT NULL``` constraint ensures that the column will not contain ```NULL```. Besides the ```NOT NULL``` constraint, 
a column may have additional constraint such as ```CHECK```, and ```UNIQUE```.

- The ```DEFAULT``` specifies a default value for the column.

- The ```AUTO_INCREMENT```  indicates that the value of the column is incremented by one automatically whenever a new 
row is inserted into the table. Each table has a maximum one ```AUTO_INCREMENT``` column.

After the column list, you can define table constraints such as ```UNIQUE```, ```CHECK```, ```PRIMARY KEY``` and ```FOREIGN KEY```.

##### Create table example.
```sql
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(128) NOT NULL,
  `priority` enum('High','Medium','Low') NOT NULL,
  `status` enum('Not Started','In Progress','Completed') NOT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_date` datetime NULL ON UPDATE CURRENT_TIMESTAMP,
  `comment` varchar(256) DEFAULT 'No comment',
  `project_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),

  UNIQUE KEY (`task_name`),

  KEY `FK_PROJECT_ID_idx` (`project_id`),

  CONSTRAINT `FK_PROJECT`
  FOREIGN KEY (`project_id`)
  REFERENCES `project` (`id`)

  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
```

The tasks table has the following columns:

   - The ``` `id` ``` is an auto-increment column. If you use the ```INSERT``` statement to insert a new row into the table 
   without specifying a value for the ```id``` column, MySQL will automatically generate a sequential integer for the ``` `id` ``` starting from 1.
   
   - The ``` `task_name` ``` column is a variable character string column whose maximum length is 128. It means that you cannot insert 
   a string whose length is greater than 128 into this column. The ```NOT NULL``` constraint indicates that the column does not accept ```NULL```. 
   In other words, you have to provide a non-```NULL``` value when you insert or update this column.
  
   - The ``` `status` ``` and ``` `priority` ```  are the ```enum's``` columns which do not allow ```NULL```. 
   ```ENUM``` is a string object whose value is chosen from a list of permitted values defined at the time of column creation. 
   For the ``` `status` ```  column ``` enum('Not Started','In Progress','Completed') ``` available values.
   For the ``` `priority` ```  column ``` enum('High','Medium','Low') ``` available values.  
   
   - The ``` `start_date` ``` and ``` `last_update_date` ``` are ```DATETIME``` columns. Because these columns do not have the 
   ```NOT NULL``` constraint, they can store ```NULL```. The ``` `start_date` ``` column has a default value of the current date. 
   In other words, if you don’t provide a value for the ``` `start_date` ``` column when you insert a new row, the 
   ``` `start_date` ``` column will take the current date of the database server. The ``` `last_update_date` ``` column has a 
   default value of ```NULL```. Every time when we update row, ``` `last_update_date` ``` column will take the current 
   date of the database server. Because of  ```ON UPDATE CURRENT_TIMESTAMP``` value.
    
   - The ``` `comment` ``` column is a variable character string column whose maximum length is 256. ```DEFAULT``` value
   is ``` 'No comment' ```.
   
The ``` `id` ``` is the primary key column of the tasks table. 
It means that the values in the ``` `id` ```column will uniquely identify rows in the table.

In addition, the ``` `project_id` ``` is the foreign key column that references to the ``` `project_id` ``` column 
of the table ``` `project` ```, we used a foreign key constraint to establish this relationship.

 MySQL supports foreign keys, which permit cross-referencing related data across tables, and foreign key constraints, which help keep the related data consistent.

A foreign key relationship involves a parent table that holds the initial column values, and a child table with column values that reference the parent column values. 
A foreign key constraint is defined on the child table. 

- If you specify ```NO ACTION``` , you're telling InnoDB that you don't want it to take either of these actions.

```sql
 ON DELETE NO ACTION ON UPDATE NO ACTION 
```

- InnoDB is a storage engine for the database management system MySQL. 

- ``` AUTO_INCREMENT=1 ``` -  means the auto increment column in your table starts from 1.

- ``` DEFAULT CHARSET=latin1 ``` - is the default character set that will be used.

```sql
ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
```

##### Insert values.

```sql
INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...);  
```

- ```INSERT INTO``` - statement is used to insert new records in a table.

- ```VALUES``` - insert values into specified columns. 

If you are adding values for all the columns of the table, you do not need to specify 
the column names in the SQL query. However, make sure the order of the values is in the same order as the columns in the table.

##### Insert values example.

```sql 
INSERT INTO
`task` (`task_name`, `priority`, `status`, `start_date`, `last_update_date`, `comment`, `project_id`)
VALUES
('setup project', 'High', 'Completed', '2020-05-10 17:42:00',
'2020-05-11 17:45:00', 'setup project, setup config', 1);
```