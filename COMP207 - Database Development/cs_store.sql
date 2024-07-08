Create table Customers(
birth_day Date,
first_name varchar(20),
last_name varchar(20),
c_id int,
Constraint PK_c_id Primary Key (c_id)
);
Create table Employees(
birth_day Date,
first_name varchar(20),
last_name varchar(20),
e_id int,
Constraint PK_e_id Primary Key (e_id)
);
Create table Transactions(
e_id int, 
c_id int, 
date Date, 
t_id int,
Constraint PK_t_id Primary Key (t_id),
Constraint FK_e_id Foreign Key (e_id) References Employees(e_id),
Constraint FK_c_id Foreign Key (c_id) References Customers(c_id)
);
Create table Promotion(
number_to_buy int,
how_many_are_free int,
type int,
Constraint PK_type Primary Key (type)
);
Create table Items(
price_for_each int,
type int,
amount_in_stock int,
name varchar(20),
Constraint PK_name Primary Key (name)
);
Create table ItemsInTransactions(
name varchar(20), 
t_id int,
iit_id int,
Constraint PK_iit_id Primary Key (iit_id),
Constraint FK_name Foreign Key (name) References Items(name),
Constraint FK_t_id Foreign Key (t_id) References Transactions(t_id)
);



Create View louistransactions As
select Count(e_id)  as "number_of_transactions" 
from Transactions Natural Join Employees 
where first_name = "Louis" and monthname(date) = "September"; 



Create View peopleinshop As 
Select birth_day, first_name, last_name from Customers Natural Join Transactions where day(date) = 28 and monthname(date) = "September"
Union 
Select birth_day, first_name, last_name  from Employees Natural Join Transactions where day(date) = 28 and monthname(date) = "September"
Order by birth_day Asc;



Create View itemsbought as
Select name, count(*) as amount_sold 
from transactions natural join itemsintransactions natural join items 
where type = 1 or type = 2 
group by name;
Create View itemsleft1 as
Select  name, type, (amount_in_stock - amount_sold) as amount_left 
from items natural join itemsbought 
where type = 1 or type = 2
order by type Asc;

Create View items_sold as
Select name as item_name, count(*) as amount_sold 
from transactions natural join itemsintransactions natural join items 
where type = 3 or type = 4 
group by name;
create view itemsleft2 as
select name, type, (amount_in_stock - IF(isnull(amount_sold)=1,0,amount_sold)) as amount_left from items left join items_sold on items.name = items_sold.item_name where type = 3 or type = 4
order by type asc;

create or replace view dataset as
select iit_id, t_id, type, price_for_each as price
from itemsintransactions natural join items;

create or replace view iitranking as
select iit_id, t_id, type, price
,(select count(*) from dataset d2 where d2.type = d1.type and d2.t_id = d1.t_id) rnk

from dataset d1
order by t_id desc, type desc, price desc, iit_id desc
;