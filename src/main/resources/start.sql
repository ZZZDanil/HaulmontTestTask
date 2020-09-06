drop table doctors;
drop table patients;
drop table recipes;


CREATE TABLE doctors (id bigint identity primary key not null, name varchar(255), surname varchar(255), patronymic varchar(255), specialisation varchar(255));
CREATE TABLE patients (id bigint identity primary key not null, name varchar(255), surname varchar(255), patronymic varchar(255), phone varchar(255));
CREATE TABLE recipes (id bigint identity primary key, about varchar(255), patient bigint foreign key REFERENCES patients (id), doctor bigint foreign key REFERENCES doctors (id), create_date varchar(255), duration_days varchar(255), priority varchar(255));

insert into doctors(name, surname, patronymic, specialisation) values('Попов_d','Пётр','Сергеевич', 'Прозектор');
insert into doctors(name, surname, patronymic, specialisation) values('Семенова_d','Елизавета','Артёмовна', 'Сиделка');
insert into doctors(name, surname, patronymic, specialisation) values('Антонов_d','Данила','Вячеславович', 'Невролог');
insert into doctors(name, surname, patronymic, specialisation) values('Калинина_d','Ирина','Максимовна', 'Диетолог');
insert into doctors(name, surname, patronymic, specialisation) values('Панкратова_d','Злата','Данииловна', 'Врач');
insert into doctors(name, surname, patronymic, specialisation) values('Калинина_d','Мия','Максимовна', 'Эндокринолог');
insert into doctors(name, surname, patronymic, specialisation) values('Алексеева_d','Стефания','Дмитриевна', 'Врач');
insert into doctors(name, surname, patronymic, specialisation) values('Тарасов_d','Савва','Михайлович', 'Отоневролог');
insert into doctors(name, surname, patronymic, specialisation) values('Корнилов_d','Илья','Станиславович', 'Врач');
insert into doctors(name, surname, patronymic, specialisation) values('Куликова_d','Стефания','Алексеевна', 'Акушерка');

insert into doctors(name, surname, patronymic, specialisation) values('Никонов_d','Илья','Кириллович', 'Прозектор');
insert into doctors(name, surname, patronymic, specialisation) values('Смирнова_d','Елизавета','Максимовна', 'Сиделка');
insert into doctors(name, surname, patronymic, specialisation) values('Петров_d','Александр','Степанович', 'Невролог');
insert into doctors(name, surname, patronymic, specialisation) values('Тимофеев_d','Артемий','Георгиевич', 'Диетолог');
insert into doctors(name, surname, patronymic, specialisation) values('Кузина_d','Алиса','Дмитриевна', 'Врач');
insert into doctors(name, surname, patronymic, specialisation) values('Кузнецова_d','Диана','Максимовна', 'Эндокринолог');
insert into doctors(name, surname, patronymic, specialisation) values('Алексеева_d','Мария','Дмитриевна', 'Врач');
insert into doctors(name, surname, patronymic, specialisation) values('Михайлов_d','Савва','Михайлович', 'Отоневролог');
insert into doctors(name, surname, patronymic, specialisation) values('Корнилов_d','Илья','Станиславович', 'Врач');
insert into doctors(name, surname, patronymic, specialisation) values('Куликова_d','Стефания','Алексеевна', 'Акушерка');

insert into patients(name, surname, patronymic, phone) values('Попов','Пётр','Сергеевич', '+7(900)737-4410');
insert into patients(name, surname, patronymic, phone) values('Семенова','Елизавета','Артёмовна', '+7(900)633-8166');
insert into patients(name, surname, patronymic, phone) values('Антонов','Данила','Вячеславович', '+7(900)862-5417');
insert into patients(name, surname, patronymic, phone) values('Калинина','Ирина','Максимовна', '+7(900)748-0484');
insert into patients(name, surname, patronymic, phone) values('Панкратова','Злата','Данииловна', '+7(900)116-5363');
insert into patients(name, surname, patronymic, phone) values('Калинина','Мия','Максимовна', '+7(900)978-1348');
insert into patients(name, surname, patronymic, phone) values('Алексеева','Стефания','Дмитриевна', '+7(900)117-5351');
insert into patients(name, surname, patronymic, phone) values('Тарасов','Савва','Михайлович', '+7(900)990-4856');
insert into patients(name, surname, patronymic, phone) values('Корнилов','Илья','Станиславович', '+7(900)725-0999');
insert into patients(name, surname, patronymic, phone) values('Куликова','Стефания','Алексеевна', '+7(900)680-0392');

insert into patients(name, surname, patronymic, phone) values('Никонов','Илья','Кириллович', '+7(900)110-2848');
insert into patients(name, surname, patronymic, phone) values('Смирнова','Елизавета','Максимовна', '+7(900)592-8179');
insert into patients(name, surname, patronymic, phone) values('Петров','Александр','Степанович', '+7(900)180-3234');
insert into patients(name, surname, patronymic, phone) values('Тимофеев','Артемий','Георгиевич', '+7(900)381-5231');
insert into patients(name, surname, patronymic, phone) values('Кузина','Алиса','Дмитриевна', '+7(900)527-3652');
insert into patients(name, surname, patronymic, phone) values('Кузнецова','Диана','Максимовна', '+7(900)450-5294');
insert into patients(name, surname, patronymic, phone) values('Алексеева','Мария','Дмитриевна', '+7(900)847-0821');
insert into patients(name, surname, patronymic, phone) values('Михайлов','Савва','Михайлович', '+7(900)136-3688');
insert into patients(name, surname, patronymic, phone) values('Корнилов','Илья','Станиславович', '+7(900)056-7099');
insert into patients(name, surname, patronymic, phone) values('Куликова','Стефания','Алексеевна', '+7(900)401-7051');


insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Изопринозин Isoprinosini 0,5 D.t.d. N 30 in tab. S. Внутрь принимать по схеме врача',
        5,14,'06.02.2014','17','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Цисплатин	Pulv. Cisplatini 0,5 mg D.t.d. N 10 S. Растворить по указаниям, внутримышечно по схеме',
        1,4,'25.11.2015','30','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Висмодегиб (Эриведж)	Vismodegibi 0,15 D.t.d. N 28 in caps. S. Внутрь по 1 таблетке утром',
        8,2,'02.04.2014','3','Cito');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Комбипэк (Сальбутамол + Теофиллин)	Tab. “Combipec” 0,006/0,2 N 50 D.S. Внутрь по 1 таблетке утром и вечером',
        17,13,'21.03.2017','17','Statim');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Анжелик	Tab. “Angelik” N 28 D.S. Внутрь по 1 таблетке утром и вечером',
        14,11,'06.02.2014','17','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Коделанов	Tab. “Kodelanov” N 10 D.S. Внутрь по 1 таблетке утром',
        13,16,'06.02.2014','4','Cito');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Сибазон (диазепам)	Diazepami 0,5 % – 2 ml D.t.d. N 10 in amp.S. Внутримышечно 2 раза в день',
        12,7,'06.02.2014','2','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Плаквенил (гидроксихлорохин)	Hydroxychloroquini 0,2 D.t.d. N 50 in tab.S. Внутрь по схеме врача',
        11,3,'06.02.2014','17','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Цисплатин	Pulv. Cisplatini 0,5 mg D.t.d. N 10 S. Растворить по указаниям, внутримышечно по схеме',
        3,6,'06.02.2014','17','Cito');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Милурит	Tab. “Milurit” 100 mg D.t.d. N 100 S. Внутрь по 1 т 3 раза в день',
        10,8,'06.02.2014','6','Statim');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Изопринозин Isoprinosini 0,5 D.t.d. N 30 in tab. S. Внутрь принимать по схеме врача',
        9,10,'06.02.2014','10','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Изопринозин Isoprinosini 0,5 D.t.d. N 30 in tab. S. Внутрь принимать по схеме врача',
        8,11,'06.02.2014','17','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Кокарнит	Rp.: Pulv. “Kokarnit” 2 ml N 3 D.S.: Развести по схеме, внутримышечно 1 раз в день',
        7,12,'06.02.2014','17','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Изопринозин Isoprinosini 0,5 D.t.d. N 30 in tab. S. Внутрь принимать по схеме врача',
        6,10,'06.02.2014','2','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Изопринозин Isoprinosini 0,5 D.t.d. N 30 in tab. S. Внутрь принимать по схеме врача',
        4,16,'06.02.2014','17','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Изопринозин Isoprinosini 0,5 D.t.d. N 30 in tab. S. Внутрь принимать по схеме врача',
        3,2,'06.02.2014','17','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Цисплатин	Pulv. Cisplatini 0,5 mg D.t.d. N 10 S. Растворить по указаниям, внутримышечно по схеме',
        2,3,'06.02.2014','999999','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Изопринозин Isoprinosini 0,5 D.t.d. N 30 in tab. S. Внутрь принимать по схеме врача',
        18,5,'06.02.2014','17','Cito');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Реладорм	Rp.: Tab. “Reladorm” 10/100 mg N 30 D.S.: Внутрь по 1 таблетке 1 раз в сутки',
        19,17,'06.02.2014','17','Normal');
insert into recipes(about, patient, doctor, create_date, duration_days, priority)
values ('Комбипэк (Сальбутамол + Теофиллин)	Tab. “Combipec” 0,006/0,2 N 50 D.S. Внутрь по 1 таблетке утром и вечером',
        7,16,'06.02.2014','17','Normal');

