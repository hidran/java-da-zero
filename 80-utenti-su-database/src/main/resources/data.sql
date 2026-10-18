-- Demo users of the course: the password of each user is <name>-demo, stored only as a BCrypt hash
insert into app_user values ('alice', '{bcrypt}$2a$10$YZBK.GYMthlDx9tf0GMnxOd9MBVpx8dsg0EKXSk3FkWYnLG8yd0Re', 'USER', true);
insert into app_user values ('bob', '{bcrypt}$2a$10$Z8sbHRaI1LujirsaEdW9SuECMKKoR8xX0auvzxjYig/099s/d4k2e', 'ADMIN', true);
insert into app_user values ('carol', '{bcrypt}$2a$10$K7BPBUwyLKR10LFxoTLjQeh7NoKd73j6wpClJTMH.Y6cxAA/BmT46', 'USER', true);
insert into app_user values ('dave', '{bcrypt}$2a$10$RgDvmCzNiIHpNVp3ZTRIMe3yrZGPnk/wKWuMnEl45jqonSOe/aCfS', 'USER', false);
