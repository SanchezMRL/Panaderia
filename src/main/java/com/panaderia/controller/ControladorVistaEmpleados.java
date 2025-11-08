<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Empleados</title>

    <!-- Bootstrap CDN -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>

<body class="bg-light">

<div class="container mt-4">

    <h2 class="text-center mb-4">Gestión de Empleados</h2>

    <!-- FORMULARIO PARA AGREGAR EMPLEADO -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            Registrar Nuevo Empleado
        </div>
        <div class="card-body">

            <form th:action="@{/empleados/guardar}" method="post">

                <div class="mb-3">
                    <label class="form-label">Nombre:</label>
                    <input type="text" name="nombre" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" name="email" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Teléfono:</label>
                    <input type="text" name="telefono" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Cargo:</label>
                    <input type="text" name="cargo" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Contraseña:</label>
                    <input type="password" name="password" class="form-control" required>
                </div>

                <button class="btn btn-success w-100" type="submit">Guardar Empleado</button>

            </form>

        </div>
    </div>

    <!-- TABLA DE EMPLEADOS REGISTRADOS -->
    <div class="card">
        <div class="card-header bg-dark text-white">
            Empleados Registrados
        </div>

        <div class="card-body p-0">
            <table class="table table-striped mb-0">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Email</th>
                        <th>Teléfono</th>
                        <th>Cargo</th>
                    </tr>
                </thead>

                <tbody>
                    <tr th:each="emp : ${empleados}">
                        <td th:text="${emp.idEmpleado}"></td>
                        <td th:text="${emp.nombre}"></td>
                        <td th:text="${emp.email}"></td>
                        <td th:text="${emp.telefono}"></td>
                        <td th:text="${emp.cargo}"></td>
                    </tr>

                    <!-- Si no hay empleados -->
                    <tr th:if="${#lists.isEmpty(empleados)}">
                        <td colspan="5" class="text-center p-3">
                            No hay empleados registrados.
                        </td>
                    </tr>
                </tbody>

            </table>
        </div>
    </div>

</div>

</body>
</html>
