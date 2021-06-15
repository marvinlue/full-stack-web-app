-- RUN Http file UserInsert.http instead
/*Insert network.users
VALUES ('1','src/main/resources/static/images/default.png','user1@mail.com','password1','2021-06-10 01:00:00','user1'),
       ('2','src/main/resources/static/images/default.png','user2@mail.com','password2','2021-06-10 02:00:00','user2'),
       ('3','src/main/resources/static/images/default.png','user3@mail.com','password3','2021-06-10 03:00:00','user3');

UPDATE network.user_sequence
SET next_val = 4
WHERE next_val = 1;*/

INSERT network._groups
VALUES ('1','2021-06-10 01:00:00','group1'),
       ('2','2021-06-10 01:00:00','group2');

UPDATE network.group_sequence
SET next_val = 3
WHERE next_val = 1;

INSERT network.members_info
VALUES ('1',b'1','2021-06-10 01:00:00','1','1'),
       ('2',b'0','2021-06-10 01:00:00','1','2'),
       ('3',b'0','2021-06-10 01:00:00','1','3'),
       ('4',b'1','2021-06-10 01:00:00','2','1'),
       ('5',b'1','2021-06-10 01:00:00','2','2');

UPDATE network.member_sequence
SET next_val = 6
WHERE next_val = 1;

INSERT network.posts
VALUES ('1','First',ST_GeomFromText('POINT(18 -33)'),'This is the first post in group1','2021-06-10 01:00:00','1','1'),
       ('2','Second',ST_GeomFromText('POINT(18 -33)'),'This is the second post in group1','2021-06-10 02:00:00','1','2'),
       ('3','Third',ST_GeomFromText('POINT(18 -33)'),'This is the third post in group1','2021-06-10 03:00:00','1','3'),
       ('4','First',ST_GeomFromText('POINT(18 -33)'),'This is the first post in group2','2021-06-10 01:00:00','1','2'),
       ('5','Second',ST_GeomFromText('POINT(18 -33)'),'This is the first post in group2','2021-06-10 02:00:00','1','2');

UPDATE network.post_sequence
SET next_val = 6
WHERE next_val = 1;