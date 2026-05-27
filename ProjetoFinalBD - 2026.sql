-- SISTEMA DE GESTÃO DE LINHA DE PRODUÇÃO (Tema: Robótica / VSSS)
-- Segunda Entrega: Script SQL Completo e à prova de falhas

DROP DATABASE IF EXISTS SistemaProducao;
CREATE DATABASE SistemaProducao;
USE SistemaProducao;


-- 1. CRIAÇÃO DAS TABELAS

CREATE TABLE Produto (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    nome_modelo VARCHAR(100) NOT NULL,
    custo_producao DECIMAL(10, 2) NOT NULL,
    data_fabricacao DATETIME NOT NULL
);

CREATE TABLE Relatorio_Inspecao (
    id_relatorio INT AUTO_INCREMENT PRIMARY KEY,
    aprovado VARCHAR(20) NOT NULL, -- 'Aprovado' ou 'Reprovado'
    observacoes_tecnicas TEXT,
    id_produto INT UNIQUE NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES Produto(id_produto) ON DELETE CASCADE
);

CREATE TABLE Maquina (
    id_maquina INT AUTO_INCREMENT PRIMARY KEY,
    codigo_identificacao VARCHAR(50) NOT NULL UNIQUE,
    tempo_processo_segundos INT NOT NULL,
    tipo_etapa VARCHAR(50) NOT NULL
);

CREATE TABLE Manutencao (
    id_manutencao INT AUTO_INCREMENT PRIMARY KEY,
    data_falha DATE NOT NULL,
    descricao_erro VARCHAR(255) NOT NULL,
    custo_reparo DECIMAL(10, 2) NOT NULL,
    id_maquina INT NOT NULL,
    FOREIGN KEY (id_maquina) REFERENCES Maquina(id_maquina) ON DELETE CASCADE
);

-- Entidade: Operador
CREATE TABLE Operador (
    id_operador INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(150) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL,
    turno_noturno TINYINT(1) NOT NULL -- 0 = Dia, 1 = Noite
);

CREATE TABLE Operador_has_Maquina (
    Operador_id_operador INT NOT NULL,
    Maquina_id_maquina INT NOT NULL,
    PRIMARY KEY (Operador_id_operador, Maquina_id_maquina),
    FOREIGN KEY (Operador_id_operador) REFERENCES Operador(id_operador) ON DELETE CASCADE,
    FOREIGN KEY (Maquina_id_maquina) REFERENCES Maquina(id_maquina) ON DELETE CASCADE
);

-- 2. INSERÇÃO DE DADOS 

INSERT INTO Produto (nome_modelo, custo_producao, data_fabricacao) VALUES
('Placa Base VSSS v1.0', 45.50, '2026-05-20 08:30:00'),
('Motor DC 12V com Encoder', 120.00, '2026-05-20 09:15:00'),
('Módulo de Rádio NRF24L01', 35.00, '2026-05-21 10:00:00'),
('Chassi em Alumínio Estrutural', 80.20, '2026-05-21 11:45:00'),
('Sensor Infravermelho Sharp', 55.00, '2026-05-22 14:20:00');


INSERT INTO Relatorio_Inspecao (aprovado, observacoes_tecnicas, id_produto) VALUES
('Aprovado', 'Trilhas de cobre perfeitas, sem curtos.', 1),
('Aprovado', 'Eixo rotacionando com torque esperado.', 2),
('Reprovado', 'Falha na comunicação SPI. Necessita retrabalho.', 3),
('Aprovado', 'Corte preciso, dimensões dentro da tolerância.', 4),
('Aprovado', 'Leitura de distância calibrada corretamente.', 5);

INSERT INTO Maquina (codigo_identificacao, tempo_processo_segundos, tipo_etapa) VALUES
('CNC-FRESADORA-01', 1200, 'Corte e Usinagem'),
('PICK-AND-PLACE-01', 300, 'Montagem de Placas'),
('FORNO-REFLOW-02', 600, 'Soldagem SMD'),
('ESTACAO-TESTE-QA', 120, 'Inspeção de Qualidade'),
('IMPRESSORA-3D-A1', 3600, 'Prototipagem');


INSERT INTO Manutencao (data_falha, descricao_erro, custo_reparo, id_maquina) VALUES
('2026-04-10', 'Troca da fresa desgastada.', 150.00, 1),
('2026-04-15', 'Bico de sucção entupido.', 50.00, 2),
('2026-05-02', 'Falha na resistência térmica, temperatura não subia.', 450.00, 3),
('2026-05-18', 'Calibração do multímetro integrada falhou.', 80.00, 4),
('2026-05-25', 'Extrusora trancada com filamento ABS.', 120.00, 5);


INSERT INTO Operador (nome_completo, salario, turno_noturno) VALUES
('Nathan Arruola da Costa', 3500.00, 0),
('Kaynan H Dias de Melo', 3500.00, 0),
('Fuad Murad', 3500.00, 1),
('Carlos Silva', 2800.00, 1),
('Mariana Souza', 3100.00, 0);


INSERT INTO Operador_has_Maquina (Operador_id_operador, Maquina_id_maquina) VALUES
(1, 2), -- Nathan opera Pick and Place
(1, 3), -- Nathan opera Forno Reflow
(2, 1), -- Kaynan opera CNC
(3, 4), -- Fuad opera Estação de Teste (Noturno)
(4, 5); -- Carlos opera Impressora 3D (Noturno)

-- 3 e 4. GERENCIAMENTO DE ACESSO (Usuários e Roles)

DROP ROLE IF EXISTS role_operacao_fabrica;
DROP USER IF EXISTS 'usr_auditor'@'localhost';
DROP USER IF EXISTS 'usr_tecnico'@'localhost';


CREATE ROLE role_operacao_fabrica;
GRANT SELECT, INSERT ON SistemaProducao.* TO role_operacao_fabrica;


CREATE USER 'usr_auditor'@'localhost' IDENTIFIED BY 'Auditoria@2026';
CREATE USER 'usr_tecnico'@'localhost' IDENTIFIED BY 'Tecnico@2026';


GRANT role_operacao_fabrica TO 'usr_auditor'@'localhost', 'usr_tecnico'@'localhost';
SET DEFAULT ROLE role_operacao_fabrica TO 'usr_auditor'@'localhost', 'usr_tecnico'@'localhost';

-- 5. OBJETOS PROGRAMÁVEIS

CREATE VIEW vw_rastreabilidade_produtos AS
SELECT 
    p.id_produto,
    p.nome_modelo,
    p.data_fabricacao,
    r.aprovado,
    r.observacoes_tecnicas
FROM Produto p
LEFT JOIN Relatorio_Inspecao r ON p.id_produto = r.id_produto;


DELIMITER //
CREATE PROCEDURE sp_registrar_falha (
    IN p_id_maquina INT,
    IN p_descricao VARCHAR(255),
    IN p_custo DECIMAL(10,2)
)
BEGIN
    INSERT INTO Manutencao (data_falha, descricao_erro, custo_reparo, id_maquina)
    VALUES (CURDATE(), p_descricao, p_custo, p_id_maquina);
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER trg_validar_custo_produto
BEFORE INSERT ON Produto
FOR EACH ROW
BEGIN
    IF NEW.custo_producao < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Erro: O custo de produção de um produto não pode ser negativo.';
    END IF;
END //
DELIMITER ;

-- 6. CONSULTAS PARA VERIFICAR A EXECUÇÃO (Bônus visual)

SHOW TABLES;
SELECT * FROM Operador;