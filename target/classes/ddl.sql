create table cidade (id bigint not null auto_increment, nome varchar(255) not null, estado_id bigint, primary key (id)) engine=InnoDB
create table cozinha (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table forma_pagamento (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table permissao (id bigint not null auto_increment, descricao varchar(255) not null, primary key (id)) engine=InnoDB
create table restaurante (id bigint not null auto_increment, nome varchar(255) not null, taxa_frete decimal(19,2) not null, cozinha_id bigint not null, primary key (id)) engine=InnoDB
create table restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null) engine=InnoDB
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id)
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id)
alter table restaurante_forma_pagamento add constraint FK7aln770m80358y4olr03hyhh2 foreign key (forma_pagamento_id) references forma_pagamento (id)
alter table restaurante_forma_pagamento add constraint FKa30vowfejemkw7whjvr8pryvj foreign key (restaurante_id) references restaurante (id)