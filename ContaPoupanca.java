// Classe ContaPoupanca
class ContaPoupanca extends Conta {
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
}
