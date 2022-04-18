insert into addresses (street,city,postal_code,country) values ('Doza Djerdja 57','Novi Sad',21000,'Srbija');

insert into fishing_instructors(id,email,name,password,phone_number,surname,type,account_delete_request,address,points,registration_status,biography) values (1,'pera@gmail.com','Pera','pera123','06245564','Peric',1,null,1,0,0,'Ovo bi trebao da je biografija instruktora.');

insert into adventures (id,description,name,price,address,fishing_instructor_id,max_person_num,cancel_fee) values (nextval('my_seqv2'),'Ovo je jako kul vikendica. Najbolja vikendica na svetu. Stvarno je dobra.','Mnogo dobra vikendica','200',1,1,10,0);


insert into images (path,entity) values ('../images/pic1.jpg',1);
insert into images (path,entity) values ('../images/pic2.jpg',1);
insert into images (path,entity) values ('../images/pic3.jpg',1);
insert into images (path,entity) values ('../images/pic4.jpg',1);
insert into images (path,entity) values ('../images/pic5.jpg',1);

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

insert into fishing_equipment (equipment,adventure) values ('oprema1',1);
insert into fishing_equipment (equipment,adventure) values ('oprema2',1);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price)
values (5,'20220528 10:00:00 AM','20220522 10:00:00 AM',false,10,150,'20220524 10:00:00 AM',1,null,120);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price)
values (10,'20220620 10:00:00 AM','20220601 10:00:00 AM',false,10,200,'20220615 10:00:00 AM',1,null,150);

insert into quick_reservations (discount, end_date_time, expiration_date_time, is_reserved, max_person_num, price,
                                start_date_time, entity, reservation,discounted_price)
values (5,'20220615 10:00:00 AM','20220522 10:00:00 AM',false,10,180,'20220610 10:00:00 AM',1,null,13);