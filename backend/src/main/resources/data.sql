insert into addresses (street,city,postal_code,country) values ('Doza Djerdja 57','Novi Sad',21000,'Srbija');
insert into addresses (city, country, postal_code, street) values ('trebinje', 'bih', 81100,'republike srpske 17');

insert into cottage_owners(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,main_photo) values (1,'pera@gmail.com','Pera','pera123','06245564','Peric',1,null,1,0,0,'pera2.jpg');
insert into clients (id, email, password, name, surname, phone_number, type, address, penalties, points,main_photo) values (2, 'mail', 'pass', 'ivana', 'jankovic', '065', 3, 2, 0, 0,'pera2.jpg');

insert into cottages (id,description,name,price,address,cottage_owner_id,max_num_person,deleted) values (nextval('my_seqv2'),'Ovo je jako kul vikendica. Najbolja vikendica na svetu. Stvarno je dobra.','Mnogo dobra vikendica','200',1,1,10,false);


insert into images (path,entity,is_main_photo) values ('cottage1.jpg',1,true);
insert into images (path,entity,is_main_photo) values ('cottage2.jpg',1,false);
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
values (5,'20220528','20220522 10:00:00 AM',false,10,150,'20220524',1,null,145,false);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (10,'20220620','20220601 10:00:00 AM',false,10,200,'20220615',1,null,180,false);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (5,'20220615','20220522 10:00:00 AM',false,10,180,'20220610',1,null,170,false);



insert into fishing_instructors(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,biography,main_photo) values (3,'pera@gmail.com','Pera','pera123','06245564','Peric',1,null,1,0,0,'Ovo bi trebao da je biografija instruktora.','pera2.jpg');

insert into adventures (id,description,name,price,address,fishing_instructor_id,max_person_num,cancel_fee,deleted) values (nextval('my_seqv2'),'Ovakav adrenalin se samo kod nas moze dozivjeti.Ludi ste ako nas ne posjetite.','Fenomenalna avantura','200',1,3,10,0,false);


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

insert into fishing_equipment (equipment,adventure) values ('oprema1',2);
insert into fishing_equipment (equipment,adventure) values ('oprema2',2);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (5,'20220528 10:00:00 AM','20220522 10:00:00 AM',false,10,150,'20220524 10:00:00 AM',2,null,120,false);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (10,'20220620 10:00:00 AM','20220601 10:00:00 AM',false,10,200,'20220615 10:00:00 AM',2,null,150,false);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price,deleted)
values (5,'20220615 10:00:00 AM','20220522 10:00:00 AM',false,10,180,'20220610 10:00:00 AM',2,null,13,false);


