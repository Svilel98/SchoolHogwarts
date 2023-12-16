select * from student;
select * from student where age > 20 and age < 30;
select name from student;
select * from student where name like '%M%' or name like '%n%';
select * from student where id > student.age;
select * from student ORDER BY age;