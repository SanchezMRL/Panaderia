@Service
public class LoginService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Object autenticar(String usuario, String password, String tipo) {

        if ("cliente".equalsIgnoreCase(tipo)) {

            Cliente cliente = clienteRepository.findByEmail(usuario);

            if (cliente != null && passwordEncoder.matches(password, cliente.getPassword())) {
                return cliente;
            }

            return null;

        } else if ("admin".equalsIgnoreCase(tipo)) {

            return empleadoRepository.findByEmailAndPassword(usuario, password);

        }

        return null;
    }
}

