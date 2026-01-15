-- joueur
CREATE TABLE joueur
(
    id     BIGINT       NOT NULL AUTO_INCREMENT,
    nom    VARCHAR(255) NULL,
    prenom VARCHAR(255) NULL,
    numero INT          NOT NULL,
    CONSTRAINT pk_joueur PRIMARY KEY (id)
);

-- equipe
CREATE TABLE equipe
(
    id  BIGINT       NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NULL,
    CONSTRAINT pk_equipe PRIMARY KEY (id)
);

CREATE TABLE equipe_joueur
(
    equipe_id  BIGINT NOT NULL,
    joueur_id BIGINT NOT NULL
);

ALTER TABLE equipe_joueur
    ADD CONSTRAINT uc_equipe_joueur UNIQUE (joueur_id);

ALTER TABLE equipe_joueur
    ADD CONSTRAINT fk_equjou_on_equipe FOREIGN KEY (equipe_id) REFERENCES equipe (id);

ALTER TABLE equipe_joueur
    ADD CONSTRAINT fk_equjou_on_joueur FOREIGN KEY (joueur_id) REFERENCES joueur (id);

-- round
CREATE TABLE round
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    scorea       INT    NOT NULL,
    scoreb       INT    NOT NULL,
    round_number INT    NOT NULL,
    CONSTRAINT pk_round PRIMARY KEY (id)
);

-- match
CREATE TABLE `match`
(
    id         BIGINT   NOT NULL AUTO_INCREMENT,
    equipea_id BIGINT   NULL,
    equipeb_id BIGINT   NULL,
    status     SMALLINT NULL,
    CONSTRAINT pk_match PRIMARY KEY (id)
);

CREATE TABLE match_round
(
    match_id  BIGINT NOT NULL,
    round_id BIGINT NOT NULL
);

ALTER TABLE match_round
    ADD CONSTRAINT uc_match_rounds_rounds UNIQUE (round_id);

ALTER TABLE `match`
    ADD CONSTRAINT FK_MATCH_ON_EQUIPEA FOREIGN KEY (equipea_id) REFERENCES equipe (id);

ALTER TABLE `match`
    ADD CONSTRAINT FK_MATCH_ON_EQUIPEB FOREIGN KEY (equipeb_id) REFERENCES equipe (id);

ALTER TABLE match_round
    ADD CONSTRAINT fk_matrou_on_match FOREIGN KEY (match_id) REFERENCES `match` (id);

ALTER TABLE match_round
    ADD CONSTRAINT fk_matrou_on_round FOREIGN KEY (round_id) REFERENCES round (id);

-- import_report
CREATE TABLE import_report
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    import_date   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    success_count INT          NOT NULL DEFAULT 0,
    failure_count INT          NOT NULL DEFAULT 0,
    errors        TEXT         NULL,
    CONSTRAINT pk_import_report PRIMARY KEY (id)
);

CREATE INDEX idx_import_report_date ON import_report(import_date DESC);