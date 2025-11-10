-- caronas_db.sql
CREATE DATABASE IF NOT EXISTS caronas_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE caronas_db;

-- tabela usuarios
CREATE TABLE IF NOT EXISTS usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(150) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  senha_hash VARCHAR(255) NOT NULL,
  reputacao DECIMAL(3,2) DEFAULT 5.00,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- tabela veiculos
CREATE TABLE IF NOT EXISTS veiculos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  modelo VARCHAR(100),
  placa VARCHAR(15),
  capacidade INT DEFAULT 4,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- tabela caronas
CREATE TABLE IF NOT EXISTS caronas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  motorista_id INT NOT NULL,
  origem VARCHAR(200) NOT NULL,
  destino VARCHAR(200) NOT NULL,
  data_hora DATETIME NOT NULL,
  vagas INT NOT NULL,
  descricao VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (motorista_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- tabela avaliacoes
CREATE TABLE IF NOT EXISTS avaliacoes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  carona_id INT NOT NULL,
  avaliador_id INT NOT NULL,
  nota TINYINT NOT NULL CHECK (nota BETWEEN 1 AND 5),
  comentario VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (carona_id) REFERENCES caronas(id) ON DELETE CASCADE,
  FOREIGN KEY (avaliador_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- inserts de teste (remova/alterar senhas em produção)
INSERT INTO usuarios (nome, email, senha_hash) VALUES
('Aluno Teste A','alunoA@unicesumar.edu.br','senha123'),
('Aluno Teste B','alunoB@unicesumar.edu.br','senha123'),
('Aluno Motorista','motorista@unicesumar.edu.br','senha123');

INSERT INTO veiculos (usuario_id, modelo, placa, capacidade) VALUES
(3, 'Toyota Corolla', 'ABC1D23', 4);

INSERT INTO caronas (motorista_id, origem, destino, data_hora, vagas, descricao) VALUES
(3, 'Residencia Zona Sul', 'Campus Maringá', '2025-11-20 07:30:00', 3, 'Saída perto do terminal X');
