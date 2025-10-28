<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Panadería - Sistema de Gestión (Versión 2)</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      display: flex;
    }
    .sidebar {
      width: 250px;
      background-color: #0A2F66;
      color: white;
      height: 100vh;
      padding: 20px;
      box-shadow: 2px 0 8px rgba(0,0,0,0.2);
    }
    .sidebar h2 {
      text-align: center;
    }
    .sidebar ul {
      list-style: none;
      padding: 0;
    }
    .sidebar ul li {
      margin: 15px 0;
      padding: 10px;
      background-color: #F9D923;
      color: #0A2F66;
      text-align: center;
      cursor: pointer;
      border-radius: 8px;
    }
    .sidebar ul li:hover {
      background-color: #FFD43B;
    }
    .main {
      flex: 1;
      padding: 40px;
      background: #E8F1FA;
    }
    .main h1 {
      color: #0A2F66;
    }
  </style>
</head>
<body>
  <div class="sidebar">
    <h2>🍞 Menú</h2>
    <ul>
      <li onclick="location.href='registrar.html'">Registrar Pedido</li>
      <li onclick="location.href='consultar.html'">Consultar Pedido</li>
      <li onclick="location.href='opiniones.html'">Opiniones</li>
      <li onclick="location.href='inventario.html'">Inventario</li>
      <li onclick="location.href='reportes.html'">Reportes</li>
      <li onclick="location.href='entregas.html'">Entregas</li>
      <li onclick="location.href='agregar.html'">Agregar</li>
      <li onclick="location.href='observar.html'">Registros</li>
    </ul>
  </div>
  <div class="main">
    <h1>Bienvenido al Sistema de Gestión de Panadería</h1>
    <p>Seleccione una opción del menú lateral.</p>
  </div>
</body>
</html>
