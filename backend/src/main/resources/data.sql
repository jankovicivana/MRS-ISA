insert into addresses (street,city,postal_code,country) values ('Doza Djerdja 57','Novi Sad',21000,'Srbija');
insert into addresses (city, country, postal_code, street) values ('trebinje', 'bih', 81100,'republike srpske 17');
insert into addresses (city, country, postal_code, street) values ('Cuprija', 'Srbija', 35230,'Kneza Milosa 18');


insert into cottage_owners(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,main_photo,deleted, enabled, average_grade,registration_reason) values (nextval('my_seqv1'),'pera123@gmail.com','Pera','$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K','06245564','Peric',1,null,1,0,0,'coka1.jpeg',false, true,0.0,'nema');
insert into clients (id, email, password, name, surname, phone_number, type, address, penalties, points,main_photo,deleted, enabled) values (nextval('my_seqv1'), 'ivanaj0610@gmail.com', '$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K', 'ivana', 'jankovic', '065', 3, 2, 1, 0,'pera2.jpg',false, true);

insert into cottages (id,description,name,price,address,cottage_owner_id,max_num_person,deleted, average_grade, kind) values (nextval('my_seqv2'),'Ovo je jako kul vikendica. Najbolja vikendica na svetu. Stvarno je dobra.','Divna vikendica','200',1,1,10,false,3.2, 0);

insert into images (path,entity,is_main_photo) values ('cottage1.jpg',1,true);
insert into images (path,entity,is_main_photo) values ('cottage3.jpg',1,false);
insert into images (path,entity,is_main_photo) values ('cottage4.jpg',1,false);
insert into images (path,entity,is_main_photo) values ('cottage5.jpg',1,false);

insert into rooms (bed_number,cottage) values (3,1);
insert into rooms (bed_number,cottage) values (2,1);
insert into rooms (bed_number,cottage) values (2,1);

insert into additional_services (name,entity,quick_reservation) values ('Pet frendly',1,null);
insert into additional_services (name,entity,quick_reservation) values ('Wi-fi',1,null);
insert into additional_services (name,entity,quick_reservation) values ('Klimatizovano',1,null);
insert into additional_services (name,entity,quick_reservation) values ('Parking',1,null);
insert into additional_services (name,entity,quick_reservation) values ('Kokteli',1,null);

insert into rules (rule,entity) values ('Prijava posle 12h',1);
insert into rules (rule,entity) values ('Odjava pre 17h',1);
insert into rules (rule,entity) values ('Pusenje je zabranjeno',1);
insert into rules (rule,entity) values ('Ne lomiti stvari',1);
insert into rules (rule,entity) values ('Decu drzati podalje',1);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (5,'20220710','20220629 10:00:00 AM',false,10,150,'20220702',1,null,145,false);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (10,'20220630','20220625 10:00:00 AM',false,10,200,'20220628',1,null,180,false);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (5,'20220625','20220621 10:00:00 AM',false,10,180,'20220622',1,null,170,false);



insert into fishing_instructors(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,biography,main_photo,deleted, enabled, average_grade,registration_reason) values (nextval('my_seqv1'),'pera@gmail.com','Pera','$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K','06245564','Peric',1,null,1,0,0,'Ovo bi trebao da je biografija instruktora.','pera2.jpg',false, true, 0.0,'evo mene opet');

insert into adventures (id,description,name,price,address,fishing_instructor_id,max_person_num,cancel_fee,deleted, average_grade, kind) values (nextval('my_seqv2'),'Ovakav adrenalin se samo kod nas moze dozivjeti.Ludi ste ako nas ne posjetite.','Fenomenalna avantura','200',1,3,10,0,false,2.2, 2);


insert into images (path,entity,is_main_photo) values ('pic1.jpg',2,false);
insert into images (path,entity,is_main_photo) values ('pic2.jpg',2,false);
insert into images (path,entity,is_main_photo) values ('pic3.jpg',2,false);
insert into images (path,entity,is_main_photo) values ('pic4.jpg',2,true);
insert into images (path,entity,is_main_photo) values ('pic5.jpg',2,false);

insert into additional_services (name,entity,quick_reservation) values ('Pet frendly',2,null);
insert into additional_services (name,entity,quick_reservation) values ('Wi-fi',2,null);
insert into additional_services (name,entity,quick_reservation) values ('Klimatizovano',2,null);
insert into additional_services (name,entity,quick_reservation) values ('Parking',2,null);
insert into additional_services (name,entity,quick_reservation) values ('Kokteli',2,null);

insert into rules (rule,entity) values ('Prijava posle 12h',2);
insert into rules (rule,entity) values ('Odjava pre 17h',2);
insert into rules (rule,entity) values ('Pusenje je zabranjeno',2);
insert into rules (rule,entity) values ('Ne lomiti stvari',2);
insert into rules (rule,entity) values ('Decu drzati podalje',2);

insert into fishing_equipment (equipment,adventure,boat) values ('udice',2,null);
insert into fishing_equipment (equipment,adventure,boat) values ('mamci',2,null);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (5,'20220528 10:00:00 AM','20220522 10:00:00 AM',false,10,150,'20220524 10:00:00 AM',2,null,120,false);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (10,'20220620 10:00:00 AM','20220601 10:00:00 AM',false,10,200,'20220615 10:00:00 AM',2,null,150,false);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (5,'20220615 10:00:00 AM','20220522 10:00:00 AM',false,10,180,'20220610 10:00:00 AM',3,null,13,false);


insert into boat_owners(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,main_photo,deleted, enabled, average_grade,registration_reason) values (nextval('my_seqv1'),'mika@gmail.com','Mika','$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K','06245564','Mikic',2,null,2,0,0,'coka2.jpg',false, true, 0.0,'ja dosao');

insert into boats (id, deleted, description, name, price, address, motor_num, cancel_fee, capacity, length, max_speed,
                   power, type, boat_owner, average_grade, kind)
values (nextval('my_seqv2'),false,'Ovo je mnogo dobar brod. Najbrzi na svetu. On ne plovi, on leti.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed volutpat non nisi dignissim vulputate. Fusce vitae nunc sed nibh porttitor aliquet et vitae dui. Aenean ultricies ligula id ex tempus facilisis.','Brod Nikolija',200,3,3,20,5,25,250,600,0,4,3.0, 1);

insert into clients (id, email, password, name, surname, phone_number, type, address, penalties, points,main_photo,deleted, enabled) values (nextval('my_seqv1'), 'yahoomail', '$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K', 'ivana', 'kasikovic', '065', 3, 2, 0, 0,'pera2.jpg',false, true);
insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220612 10:00:00 AM','20220625 10:00:00 AM',false,2,200,20,60,5,false,2);
insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220622 10:00:00 AM','20220625 10:00:00 AM',false,2,200,20,60,5,false,2);
insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220615 10:00:00 AM','20220625 10:00:00 AM',false,2,200,20,60,5,false,2);

insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20210522 10:00:00 AM','20210525 10:00:00 AM',false,1,200,20,120,5,false,2);

insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220615 10:00:00 AM','20220625 10:00:00 AM',false,1,200,20,180,5,false,2);

insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220513 10:00:00 AM','20220525 10:00:00 AM',false,1,500,20,250,5,false,2);

insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220622 10:00:00 AM','20220623 10:00:00 AM',false,1,200,20,60,5,false,2);


insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220530 10:00:00 AM','20220605 10:00:00 AM',false,1,200,20,60,5,false,2);

insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220422 10:00:00 AM','20220504 10:00:00 AM',false,3,150,20,130,5,false,2);

insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220522 10:00:00 AM','20220603 10:00:00 AM',false,3,300,20,250,5,false,2);

insert into reservations(start_date_time,end_date_time,deleted,entity,price,system_profit,advertiser_profit,person_num,is_canceled,client) values
    ('20220613 10:00:00 AM','20220625 10:00:00 AM',false,3,500,20,400,5,false,2);

insert into images (path,entity,is_main_photo) values ('boat1.jpg',3,false);
insert into images (path,entity,is_main_photo) values ('boat2.jpg',3,false);
insert into images (path,entity,is_main_photo) values ('boat3.jpg',3,false);
insert into images (path,entity,is_main_photo) values ('boat5.jpg',3,true);

insert into additional_services (name,entity,quick_reservation) values ('Pet frendly',3,null);
insert into additional_services (name,entity,quick_reservation) values ('Wi-fi',3,null);
insert into additional_services (name,entity,quick_reservation) values ('Klimatizovano',3,null);
insert into additional_services (name,entity,quick_reservation) values ('Zurke',3,null);

insert into rules (rule,entity) values ('Prijava posle 12h',3);
insert into rules (rule,entity) values ('Odjava pre 17h',3);
insert into rules (rule,entity) values ('Ne lomiti stvari',3);
insert into rules (rule,entity) values ('Decu drzati podalje',3);

insert into navigation_equipment (equipment, boat) values (0,3);
insert into navigation_equipment (equipment, boat) values (1,3);
insert into navigation_equipment (equipment, boat) values (2,3);

insert into cottages (id,description,name,price,address,cottage_owner_id,max_num_person,deleted, average_grade, kind) values (nextval('my_seqv2'),'Ovo je jako kul vikendica. Najbolja vikendica na svetu. Stvarno je dobra.','Super vikendica','500',2,1,15,false,2.0, 0);
insert into cottages (id,description,name,price,address,cottage_owner_id,max_num_person,deleted, average_grade, kind) values (nextval('my_seqv2'),'Ovo je jako kul vikendica. Najbolja vikendica na svetu. Stvarno je dobra.','Najbolja vikendica','250',1,1,20,false,5.0, 0);
insert into cottages (id,description,name,price,address,cottage_owner_id,max_num_person,deleted, average_grade, kind) values (nextval('my_seqv2'),'Ovo je jako kul vikendica. Najbolja vikendica na svetu. Stvarno je dobra.','Coka vikendica','800',1,1,20,false,4.5, 0);

insert into images (path,entity,is_main_photo) values ('cottage6.jpg',4,false);
insert into images (path,entity,is_main_photo) values ('cottage7.jpg',4,false);

insert into images (path,entity,is_main_photo) values ('cottage8.jpg',5,false);
insert into images (path,entity,is_main_photo) values ('cottage9.jpg',5,false);

insert into images (path,entity,is_main_photo) values ('coka1.jpeg',6,false);
insert into images (path,entity,is_main_photo) values ('coka2.jpg',6,false);


insert into fishing_equipment (equipment,adventure,boat) values ('Stap za pecanje',null,3);
insert into fishing_equipment (equipment,adventure,boat) values ('Crvici',null,3);

insert into boats (id, deleted, description, name, price, address, motor_num, cancel_fee, capacity, length, max_speed,
                   power, type, boat_owner, average_grade, kind)
values (nextval('my_seqv2'),false,'Ovo je mnogo dobar brod. Najbrzi na svetu. On ne plovi, on leti.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed volutpat non nisi dignissim vulputate. Fusce vitae nunc sed nibh porttitor aliquet et vitae dui. Aenean ultricies ligula id ex tempus facilisis.','Brod Marija',300,3,3,20,5,25,250,600,0,4,2.0, 1);


insert into boats (id, deleted, description, name, price, address, motor_num, cancel_fee, capacity, length, max_speed,
                   power, type, boat_owner, average_grade, kind)
values (nextval('my_seqv2'),false,'Ovo je mnogo dobar brod. Najbrzi na svetu. On ne plovi, on leti.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed volutpat non nisi dignissim vulputate. Fusce vitae nunc sed nibh porttitor aliquet et vitae dui. Aenean ultricies ligula id ex tempus facilisis.','Brod Ivana',250,2,3,20,5,25,250,600,0,4,5.0, 1);


insert into images (path,entity,is_main_photo) values ('boat4.jpg',7,false);
insert into images (path,entity,is_main_photo) values ('boat6.jpg',7,false);
insert into images (path,entity,is_main_photo) values ('boat8.jpg',8,false);
insert into images (path,entity,is_main_photo) values ('boat9.jpg',8,false);


insert into admins(id,email,name,password,phone_number,surname,type,account_delete_request,address,main_photo,is_predefined, enabled,deleted,password_changed,points) values (nextval('my_seqv1'),'admin@gmail.com','Pera','$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K','06245564','Peric',1,null,1,'coka1.jpeg',false, true,false,false,0);
insert into boat_owners(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,main_photo,deleted, enabled, average_grade,registration_reason) values (nextval('my_seqv1'),'ikasikovic1977@gmail.com','Ivana','$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K','06245564','Kasikovic',2,null,2,0,0,'coka2.jpg',false, true, 0.0,'tezak zivot ovaj');
insert into boat_owners(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,main_photo,deleted, enabled, average_grade,registration_reason) values (nextval('my_seqv1'),'ikasikovic197@gmail.com','Ivanaaa','$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K','06245564','Kasikovic',2,null,2,0,1,'coka2.jpg',false, true, 0.0,'ovo mi je zivotna zelja');
insert into fishing_instructors(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,biography,main_photo,deleted, enabled, average_grade,registration_reason) values (nextval('my_seqv1'),'peraaaaa@gmail.com','Pera','$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K','06245564','Peric',1,null,1,0,1,'Ovo bi trebalo da je biografija instruktora.','pera2.jpg',false, true, 0.0,'ja bih da se registrujem');
insert into admins(id,email,name,password,phone_number,surname,type,account_delete_request,address,main_photo,is_predefined, enabled,deleted,password_changed,points) values (nextval('my_seqv1'),'admin2@gmail.com','Mikica','$2a$10$sUvGKGniC4ZePlmC.QvUWOxlwzbEYjg11Dhr7XE/l1fvrKKPWym6K','06245564','Peric',1,null,1,'coka1.jpeg',true, true,false,false,0);

insert into role (name) values ('ROLE_client');
insert into role (name) values ('ROLE_admin');
insert into role (name) values ('ROLE_cottageOwner');
insert into role (name) values ('ROLE_boatOwner');
insert into role (name) values ('ROLE_fishingInstructor');

insert into user_role (user_id, role_id) values (2, 1);
insert into user_role (user_id, role_id) values (5, 1);
insert into user_role (user_id, role_id) values (1, 3);
insert into user_role (user_id, role_id) values (4, 4);
insert into user_role (user_id, role_id) values (3, 5);
insert into user_role (user_id, role_id) values (6, 2);
insert into user_role (user_id, role_id) values (10, 2);

insert into subscriptions (client, entity) values (2, 1);
insert into subscriptions (client, entity) values (2, 2);
insert into subscriptions (client, entity) values (2, 3);
insert into subscriptions (client, entity) values (2, 4);


insert into advertiser_reviews (id,grade,review,status,client,advertiser) values (nextval('my_seqv3'),1,'Nekomunikativan je.',1,2,1);
insert into entity_reviews (id,grade,review,status,client,entity) values (nextval('my_seqv4'),1,'Ima buba.',1,2,1);
insert into entity_reviews (id,grade,review,status,client,entity) values (nextval('my_seqv4'),2,'Prasnjavo je',1,2,1);
insert into entity_reviews (id,grade,review,status,client,entity) values (nextval('my_seqv4'),1,'Operite posteljinu nekad',1,2,1);
insert into entity_reviews (id,grade,review,status,client,entity) values (nextval('my_seqv4'),1,'Ne valja vikendica',1,2,1);


insert into available_periods (start_date_time, end_date_time,entity,fishing_instructor) values ('20220622', '20220627', 1,null);
insert into available_periods (start_date_time, end_date_time, entity,fishing_instructor) values ('20220610', '20220727', null,3);

insert into reports (id,client_showed_up,penalty_status,report_string,advertiser,client) values (nextval('my_seqv5'),true,1,'Malo je bezobrazan. Sram ga bilooooo.',7,2);

insert into account_delete_requests (id,answer,request_reason,status,user_id) values (nextval('my_seqv6'),'','dosadni ste',1,2);
insert into account_delete_requests (id,answer,request_reason,status,user_id) values (nextval('my_seqv6'),'','ukinuli ste nam popuste',1,8);

insert into adventures (id,description,name,price,address,fishing_instructor_id,max_person_num,cancel_fee,deleted, average_grade, kind) values (nextval('my_seqv2'),'Dodjite kod nas,ali obavezno.','Nezaboravna avantura','140',1,3,10,0,false, 4.9, 2);

insert into images (path,entity,is_main_photo) values ('fish1.jpg',9,false);
insert into images (path,entity,is_main_photo) values ('fish2.jpg',9,false);


insert into adventures (id,description,name,price,address,fishing_instructor_id,max_person_num,cancel_fee,deleted, average_grade, kind) values (nextval('my_seqv2'),'Dodjite kod nas,ali obavezno.','Coka avantura','140',1,3,10,0,false, 3.5, 2);

insert into images (path,entity,is_main_photo) values ('fish3.jpg',10,false);
insert into images (path,entity,is_main_photo) values ('fish4.jpg',10,false);

insert into advertiser_complaints (client,advertiser,status,complaint,answer) values (2,1,1,'dosadnjakovic','');
insert into entity_complaints (client,entity,status,complaint,answer) values (2,1,1,'prljavoo','');


insert into ranking_info (id,rank,points_limit,client_discount,advertiser_benefits) values (1,0,10,10,10);
insert into ranking_info (id,rank,points_limit,client_discount,advertiser_benefits) values (2,1,20,20,20);
insert into ranking_info (id,rank,points_limit,client_discount,advertiser_benefits) values (3,2,20,30,30);


insert into system_info (id,client_points,advertiser_points,system_profit) values (1,2,1,30);
