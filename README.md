# SnoozeButton
Sophomore Project
## Group Members
* Brian Curry
* Dale Kauffman
* David Lindner
* Nathaniel Prill
* Nicholas Attisha
* Wakhiel Moshon

## SQL Examples
Connection string: `jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull`

Read all items in the products table:
```sql
SELECT * FROM products;
```

Delete an item from the products table:
```sql
DELETE FROM tracker.products WHERE ID = 1002;
```

Modify an item in the products table:
```sql
UPDATE tracker.products SET `Name` = 'Test Product 8' WHERE ID = 1010;
```

Insert a new item in the products table:
Note: ID must be unique - your code should check for this.
```sql
INSERT INTO tracker.products (ID, `Name`, Cost, Price, Quantity) 
	VALUES (1004, 'Test Product 10', 14.85, 29.99, 12);

```

