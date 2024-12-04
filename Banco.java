import java.util.*;

// Classe principal Banco
class Banco {
    private List<Usuario> usuarios;
    private Scanner scanner;

    public Banco() {
        usuarios = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        usuarios.add(new Usuario(nome, cpf, senha));
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public Usuario autenticarUsuario() {
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

    public void cadastrarConta(Usuario usuario) {
        System.out.println("1. Conta Corrente\n2. Conta Poupança");
        int opcao = scanner.nextInt();
        System.out.print("Saldo inicial: ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine(); // Consumir nova linha

        Conta conta;
        if (opcao == 1) {
            conta = new ContaCorrente(usuario, saldoInicial);
        } else {
            conta = new ContaPoupanca(usuario, saldoInicial);
        }

        usuario.adicionarConta(conta);
        System.out.println("Conta cadastrada com sucesso! Número da conta: " + conta.getNumero());
    }

    public void realizarOperacoes(Usuario usuario) {
        while (true) {
            System.out.println("\n1. Consultar Saldo\n2. Depositar\n3. Sacar\n4. Transferir\n5. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            if (opcao == 5) {
                System.out.println("Saindo...");
                break;
            }

            System.out.print("Número da conta: ");
            int numeroConta = scanner.nextInt();
            scanner.nextLine();

            Conta conta = buscarContaPorNumero(usuario, numeroConta);
            if (conta == null) {
                System.out.println("Conta não encontrada.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("Saldo: " + conta.getSaldo());
                    break;
                case 2:
                    System.out.print("Valor para depósito: ");
                    double valorDeposito = scanner.nextDouble();
                    scanner.nextLine();
                    conta.depositar(valorDeposito);
                    System.out.println("Depósito realizado com sucesso!");
                    break;
                case 3:
                    System.out.print("Valor para saque: ");
                    double valorSaque = scanner.nextDouble();
                    scanner.nextLine();
                    if (conta.sacar(valorSaque)) {
                        System.out.println("Saque realizado com sucesso!");
                    } else {
                        System.out.println("Saldo insuficiente.");
                    }
                    break;
                case 4:
                    System.out.print("Número da conta destino: ");
                    int numeroContaDestino = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Valor para transferência: ");
                    double valorTransferencia = scanner.nextDouble();
                    scanner.nextLine();

                    Conta contaDestino = buscarContaPorNumeroGeral(numeroContaDestino);
                    if (contaDestino != null && conta.transferir(contaDestino, valorTransferencia)) {
                        System.out.println("Transferência realizada com sucesso!");
                    } else {
                        System.out.println("Erro na transferência.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private Conta buscarContaPorNumero(Usuario usuario, int numeroConta) {
        for (Conta conta : usuario.getContas()) {
            if (conta.getNumero() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    private Conta buscarContaPorNumeroGeral(int numeroConta) {
        for (Usuario usuario : usuarios) {
            for (Conta conta : usuario.getContas()) {
                if (conta.getNumero() == numeroConta) {
                    return conta;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Banco banco = new Banco();

        while (true) {
            System.out.println("\n1. Cadastrar Usuário\n2. Login\n3. Sair");
            int opcao = banco.scanner.nextInt();
            banco.scanner.nextLine(); // Consumir nova linha

            if (opcao == 3) {
                System.out.println("Encerrando o sistema...");
                break;
            }

            switch (opcao) {
                case 1:
                    banco.cadastrarUsuario();
                    break;
                case 2:
                    Usuario usuario = banco.autenticarUsuario();
                    if (usuario != null) {
                        banco.cadastrarConta(usuario);
                        banco.realizarOperacoes(usuario);
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
