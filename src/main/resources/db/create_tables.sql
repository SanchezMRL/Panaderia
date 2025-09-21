-- Run these statements in your PostgreSQL database (panaderia_db_q3ri)
CREATE TABLE IF NOT EXISTS productos (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  precio_base NUMERIC(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
  id SERIAL PRIMARY KEY,
  id_cliente INTEGER,
  id_proveedor INTEGER,
  id_empleado INTEGER NOT NULL,
  fecha TIMESTAMP NOT NULL,
  estado VARCHAR(50),
  observaciones TEXT
);

CREATE TABLE IF NOT EXISTS pedido_detalles (
  id SERIAL PRIMARY KEY,
  id_pedido INTEGER REFERENCES pedidos(id) ON DELETE CASCADE,
  id_producto INTEGER,
  cantidad INTEGER,
  precio_unitario NUMERIC(12,2)
);

CREATE TABLE IF NOT EXISTS pagos (
  id SERIAL PRIMARY KEY,
  id_pedido_cliente INTEGER,
  metodo VARCHAR(50),
  monto NUMERIC(12,2),
  fecha TIMESTAMP,
  estado VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS opiniones (
  id SERIAL PRIMARY KEY,
  id_pedido_cliente INTEGER,
  comentario TEXT,
  calificacion INTEGER,
  satisfaccion INTEGER,
  fecha TIMESTAMP
);

-- Sample product
INSERT INTO productos (nombre, precio_base) VALUES ('Pan francés', 0.50) ON CONFLICT DO NOTHING;
INSERT INTO productos (nombre, precio_base) VALUES ('Rosca de yema', 2.50) ON CONFLICT DO NOTHING;
