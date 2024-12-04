# lp2-banco
Projeto da disciplina Linguagem de Programação 2


# Sistema Bancário (Banco) em Java

Este é um sistema bancário simples, implementado em Java, que permite gerenciar usuários, contas (corrente e poupança), e realizar operações como depósitos, saques e transferências.

## Estrutura do Projeto

O projeto é organizado em vários arquivos, cada um representando uma classe ou parte importante do sistema:

- **`Usuario.java`**: Representa o usuário do banco. Gerencia informações pessoais e as contas associadas.
- **`Conta.java`**: Classe abstrata que define o comportamento básico das contas bancárias.
- **`ContaCorrente.java`**: Implementação da classe `Conta` para contas correntes.
- **`ContaPoupanca.java`**: Implementação da classe `Conta` para contas poupança.
- **`Banco.java`**: Classe principal que gerencia o fluxo do programa, incluindo cadastro de usuários, autenticação, e operações financeiras.

---

## Como Compilar e Executar

### Pré-requisitos
- Java Development Kit (JDK) instalado na máquina (versão 8 ou superior).
- Um terminal ou IDE para rodar o código.

### Passos

1. **Clonar o repositório:**
   Clone o repositório para sua máquina local:
   ```bash
   git clone <URL-DO-REPOSITORIO>
   cd sistema-bancario
3. **Compilar os Arquivos:**
   Compile todos os arquivos .java no projeto:
   ```bash
   javac *.java
4. **Executar o Programa:**
   Execute a classe principal Banco:
   ```bash
   java Banco
   
