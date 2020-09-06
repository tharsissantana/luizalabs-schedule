CREATE TABLE schedule
(
    id uuid NOT NULL,
    data date NOT NULL,
    destinatario varchar(100) NOT NULL,
    type integer NOT NULL,
    mensagem varchar(255),
    status integer NOT NULL,
    PRIMARY KEY (id)
);