insert into cozinha (id, nome) values (1,'Tailandesa');
insert into cozinha (id, nome) values (2,'Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10,1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.5,1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 15,2);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('a ruim', 0.0,1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('u bom', 0.0,2);

insert into estado(id, nome) values (1,'São Paulo');
insert into estado(id, nome) values (2,'Minas Gerais');
insert into estado(id, nome) values (3,'Rio de Janeiro');

insert into cidade ( nome, estado_id) values ('Serrana', 1);
insert into cidade ( nome, estado_id) values ('Cravinhos', 1);
insert into cidade ( nome, estado_id) values ('Belo Horizonte', 2);
insert into cidade ( nome, estado_id) values ('Ribeirão Preto', 1);
insert into cidade ( nome, estado_id) values ('Batatais', null);

insert into forma_pagamento(nome) values('Cartão de crédito');
insert into forma_pagamento(nome) values('Cartão de dédito');
insert into forma_pagamento(nome) values('Dinheiro');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1,1), (1,2), (2,3),(3,3),(2,2);