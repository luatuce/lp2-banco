import java.util.*;
import java.io.*;

// Classe Usuario
class Usuario {
    public String nome;
    public String cpf;
    public String senha;
    public List<Conta> contas;

    // Construtor do usuário, inicializa com nome, cpf, senha e uma lista de contas vazia
    public Usuario(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    // Métodos de acesso para os atributos
    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public List<Conta> getContas() {
        return contas;
    }

    // Método para adicionar uma conta ao usuário
    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }
}

// Classe abstrata Conta
abstract class Conta {
    private static int contadorNumeroConta = 1;
    private int numero;
    protected double saldo;
    private Usuario usuario;

    // Construtor da conta, que recebe o usuário e o saldo inicial
    public Conta(Usuario usuario, double saldoInicial) {
        this.numero = contadorNumeroConta++;
        this.saldo = saldoInicial;
        this.usuario = usuario;
    }

    // Métodos de acesso
    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    // Métodos abstratos para depositar e sacar
    public abstract void depositar(double valor);
    public abstract boolean sacar(double valor);

    // Método para transferir valor de uma conta para outra
    public boolean transferir(Conta destino, double valor) {
        if (sacar(valor)) {  // Tenta sacar o valor da conta de origem
            destino.depositar(valor);  // Se o saque for bem-sucedido, deposita na conta de destino
            return true;
        }
        return false;  // Caso contrário, retorna false
    }
}

// Exceção personalizada para agências
class AgenciaException extends Exception {
    public AgenciaException(String message) {
        super(message);
    }
}

// Exceção personalizada para contas
class ContaException extends Exception {
    public ContaException(String message) {
        super(message);
    }
}

// Classe Agencia
class Agencia {
    private String nome;
    private String codigo;
    private List<Conta> contas;

    // Construtor da agência, inicializa com nome, código e lista de contas vazia
    public Agencia(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.contas = new ArrayList<>();
    }

    // Métodos de acesso
    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public List<Conta> getContas() {
        return contas;
    }

    // Método para adicionar uma conta à agência (limita o número de contas a 10)
    public void adicionarConta(Conta conta) throws AgenciaException {
        if (contas.size() >= 10) {
            throw new AgenciaException("Limite de contas atingido para esta agência.");
        }
        contas.add(conta);
    }
}

// Classe ContaSalario
class ContaSalario extends Conta {
    private static final int LIMITE_SAQUES = 3;
    private int saquesRealizados;

    public ContaSalario(Usuario usuario, double saldoInicial) {
        super(usuario, saldoInicial);
        this.saquesRealizados = 0;
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
    }

    @Override
    public boolean sacar(double valor) {
        // Limita o número de saques para 3
        if (saquesRealizados >= LIMITE_SAQUES) {
            System.out.println("Limite de saques atingido para esta conta.");
            return false;
        }
        if (valor <= saldo) {
            saldo -= valor;
            saquesRealizados++;
            return true;
        }
        return false;
    }
}

// Classe ContaCorrente
class ContaCorrente extends Conta {
    private static final double TAXA_MANUTENCAO = 10.0;

    public ContaCorrente(Usuario usuario, double saldoInicial) {
        super(usuario, saldoInicial);
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            saldo -= TAXA_MANUTENCAO;  // Deduz taxa de manutenção
            return true;
        }
        return false;
    }
}

// Classe ContaPoupanca
class ContaPoupanca extends Conta {
    private static final double RENDIMENTO_MENSAL = 0.01; // 1%

    public ContaPoupanca(Usuario usuario, double saldoInicial) {
        super(usuario, saldoInicial);
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    // Método que aplica rendimento mensal à conta
    public void aplicarRendimento() {
        saldo += saldo * RENDIMENTO_MENSAL;
    }
}

// Classe Banco que gerencia usuários, contas e agências
public class Banco {
    private List<Usuario> usuarios;
    private List<Agencia> agencias;
    private Scanner scanner;

    public Banco() {
        usuarios = new ArrayList<>();
        agencias = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Método para cadastrar um novo usuário
    public void cadastrarUsuario() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║      Cadastro de Usuário   ║");
        System.out.println("╚════════════════════════════╝");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        usuarios.add(new Usuario(nome, cpf, senha));
        System.out.println("Usuário cadastrado com sucesso!");
    }

    // Método para autenticar um usuário no sistema
    public Usuario autenticarUsuario() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║        Login de Usuário    ║");
        System.out.println("╚════════════════════════════╝");
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf) && usuario.getSenha().equals(senha)) {
                System.out.println("Autenticação bem-sucedida. Bem-vindo, " + usuario.getNome() + "!");
                return usuario;
            }
        }
        System.out.println("CPF ou senha incorretos.");
        return null;
    }

    // Método para cadastrar uma nova agência
    public void cadastrarAgencia() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║     Cadastro de Agência    ║");
        System.out.println("╚════════════════════════════╝");
        System.out.print("Nome da Agência: ");
        String nome = scanner.nextLine();
        System.out.print("Código da Agência: ");
        String codigo = scanner.nextLine();
        agencias.add(new Agencia(nome, codigo));
        System.out.println("Agência cadastrada com sucesso!");
    }

    // Método para cadastrar uma nova conta para um usuário
    public void cadastrarConta(Usuario usuario) {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║   Cadastro de Conta        ║");
        System.out.println("╚════════════════════════════╝");
        System.out.print("Escolha a agência (Código): ");
        String codigoAgencia = scanner.nextLine();
        Agencia agencia = buscarAgenciaPorCodigo(codigoAgencia);

        if (agencia == null) {
            System.out.println("Agência não encontrada.");
            return;
        }

        System.out.println("\nEscolha o tipo de conta:");
        System.out.println("1. Conta Corrente\n2. Conta Poupança\n3. Conta Salário");
        int opcao = scanner.nextInt();
        System.out.print("Saldo inicial: ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine(); // Consumir nova linha

        Conta conta;
        try {
            switch (opcao) {
                case 1:
                    conta = new ContaCorrente(usuario, saldoInicial);
                    agencia.adicionarConta(conta);
                    break;
                case 2:
                    conta = new ContaPoupanca(usuario, saldoInicial);
                    agencia.adicionarConta(conta);
                    break;
                case 3:
                    conta = new ContaSalario(usuario, saldoInicial);
                    agencia.adicionarConta(conta);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }
            usuario.adicionarConta(conta);
            System.out.println("Conta cadastrada com sucesso! Número da conta: " + conta.getNumero());
        } catch (AgenciaException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para buscar uma agência pelo código
    private Agencia buscarAgenciaPorCodigo(String codigo) {
        for (Agencia agencia : agencias) {
            if (agencia.getCodigo().equals(codigo)) {
                return agencia;
            }
        }
        return null;
    }

    // Função para carregar dados de arquivos CSV (não implementada)
    public void carregarDados() {
        try (BufferedReader br = new BufferedReader(new FileReader("dados.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Parse a linha e carregue os dados conforme necessário
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    // Função para salvar dados em CSV (não implementada)
    public void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("dados.csv"))) {
            // Escreva os dados dos usuários, contas e agências em CSV
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    // Método principal onde o programa começa
    public static void main(String[] args) {
        Banco banco = new Banco();

        while (true) {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║   Selecione a operação desejada║");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("\n1. Cadastrar Usuário\n2. Login\n3. Cadastrar Agência\n4. Sair");
            int opcao = banco.scanner.nextInt();
            banco.scanner.nextLine(); // Consumir nova linha

            if (opcao == 4) {
                System.out.println("Encerrando o sistema...");
                banco.salvarDados(); // Salvar antes de sair
                break;
            }

            switch (opcao) {
                case 1:
                    banco.cadastrarUsuario();
                    break;
                case 2:
                    Usuario usuario = banco.autenticarUsuario();
                    if (usuario != null) {
                        banco.menuOperacoes(usuario); // Chama o menu de operações
                    }
                    break;
                case 3:
                    banco.cadastrarAgencia();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Menu de operações após o login
    private void menuOperacoes(Usuario usuario) {
        while (true) {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║   Selecione a operação desejada║");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("1. Visualizar Contas\n2. Criar Conta\n3. Depositar\n4. Sacar\n5. Transferir\n6. Voltar ao Menu Inicial");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    visualizarContas(usuario);
                    break;
                case 2:
                    if (usuario.getContas().isEmpty()) {
                        cadastrarConta(usuario); // Criar conta caso o usuário não tenha
                    } else {
                        System.out.println("Você já tem contas cadastradas.");
                    }
                    break;
                case 3:
                    realizarDeposito(usuario);
                    break;
                case 4:
                    realizarSaque(usuario);
                    break;
                case 5:
                    realizarTransferencia(usuario);
                    break;
                case 6:
                    System.out.println("Saindo do menu de operações.");
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Função para visualizar as contas do usuário
    private void visualizarContas(Usuario usuario) {
        System.out.println("\nContas do usuário " + usuario.getNome() + ":");
        for (Conta conta : usuario.getContas()) {
            System.out.println("Número da Conta: " + conta.getNumero() + " | Saldo: " + conta.getSaldo());
        }
    }

    // Função para realizar o depósito em uma conta
    private void realizarDeposito(Usuario usuario) {
        System.out.print("Número da conta para depósito: ");
        int numeroConta = scanner.nextInt();
        System.out.print("Valor do depósito: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir nova linha

        Conta conta = buscarContaPorNumero(usuario, numeroConta);
        if (conta != null) {
            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    // Função para realizar o saque em uma conta
    private void realizarSaque(Usuario usuario) {
        System.out.print("Número da conta para saque: ");
        int numeroConta = scanner.nextInt();
        System.out.print("Valor do saque: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir nova linha

        Conta conta = buscarContaPorNumero(usuario, numeroConta);
        if (conta != null && conta.sacar(valor)) {
            System.out.println("Saque realizado com sucesso.");
        } else {
            System.out.println("Saque não realizado. Verifique o saldo ou o valor solicitado.");
        }
    }

    // Função para realizar a transferência entre contas
    private void realizarTransferencia(Usuario usuario) {
        System.out.print("Número da conta de origem: ");
        int numeroContaOrigem = scanner.nextInt();
        System.out.print("Número da conta de destino: ");
        int numeroContaDestino = scanner.nextInt();
        System.out.print("Valor da transferência: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir nova linha

        Conta contaOrigem = buscarContaPorNumero(usuario, numeroContaOrigem);
        Conta contaDestino = buscarContaPorNumero(usuario, numeroContaDestino);

        if (contaOrigem != null && contaDestino != null) {
            // Verificando se a transferência pode ser realizada
            if (contaOrigem.transferir(contaDestino, valor)) {
                System.out.println("Transferência realizada com sucesso.");
            } else {
                System.out.println("Erro ao realizar a transferência. Verifique o saldo da conta de origem.");
            }
        } else {
            System.out.println("Conta(s) não encontrada(s).");
        }
    }

    // Função para buscar uma conta por número
    private Conta buscarContaPorNumero(Usuario usuario, int numeroConta) {
        for (Conta conta : usuario.getContas()) {
            if (conta.getNumero() == numeroConta) {
                return conta;
            }
        }
        return null;
    }
}

