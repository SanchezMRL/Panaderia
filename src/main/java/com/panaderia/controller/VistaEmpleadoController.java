@Controller
@RequestMapping("/empleados/agregar")
public class VistaEmpleadoController {

    @GetMapping
    public String mostrarFormularioEmpleado() {
        return "agregar"; // agregar.html
    }
}
