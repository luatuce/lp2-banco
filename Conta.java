// Classe abstrata Conta
abstract class Conta {
    private static int contadorNumeroConta = 1;
    private int numero;
    protected double saldo;
    private Usuario usuario;

    public Conta(Usuario usuario, double saldoInicial) {
        this.numero = contadorNumeroConta++;
        this.saldo = saldoInicial;
        this.usuario = usuario;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public abstract void depositar(double valor);

    public abstract boolean sacar(double valor);

    public boolean transferir(Conta destino, double valor) {
        if (sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }
}
