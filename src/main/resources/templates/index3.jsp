<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Panadería - Sistema de Gestión (Versión 3)</title>
  <style>
    body {
      font-family: 'Segoe UI', sans-serif;
      background: #fafafa;
      margin: 0;
      padding: 0;
    }
    header {
      background-color: #F9D923;
      padding: 20px;
      text-align: center;
      color: #0A2F66;
      font-size: 28px;
      font-weight: bold;
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }
    .container {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      padding: 40px;
      gap: 20px;
    }
    .card {
      width: 250px;
      height: 150px;
      background: white;
      border: 2px solid #0A2F66;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 20px;
      font-weight: bold;
      color: #0A2F66;
      cursor: pointer;
      transition: transform 0.3s, background 0.3s;
    }
    .card:hover {
      transform: scale(1.05);
      background: #FFD43B;
    }
  </style>
</head>
<body>
  <header>
    🍞 Sistema de Gestión de la Panadería
  </header>

  <div class="container">
    <div class="card" onclick="location.href='registrar.html'">Registrar Pedido</div>
    <div class="card" onclick="location.href='consultar.html'">Consultar Pedido</div>
    <div class="card" onclick="location.href='opiniones.html'">Opiniones</div>
    <div class="card" onclick="location.href='inventario.html'">Inventario</div>
    <div class="card" onclick="location.href='reportes.html'">Reportes</div>
    <div class="card" onclick="location.href='entregas.html'">Entregas</div>
    <div class="card" onclick="location.href='agregar.html'">Agregar</div>
    <div class="card" onclick="location.href='observar.html'">Registros</div>
  </div>
</body>
</html>
