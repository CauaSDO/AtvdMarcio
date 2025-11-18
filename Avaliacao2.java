import java.util.Scanner;

public class Avaliacao2 {

    public static class Pessoa {
        String nome;
        int idade;
        double peso;
        double altura;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Capacidade do vetor de pessoas: ");
        int capacidade = Integer.parseInt(sc.nextLine().trim());
        Pessoa[] v = new Pessoa[capacidade];
        int qtd = 0;

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1 - Cadastrar pessoa");
            System.out.println("2 - Imprimir pessoas");
            System.out.println("3 - Índice da mais velha com IMC Magreza");
            System.out.println("4 - Ordenar por nome");
            System.out.println("5 - Média de idade por limite de IMC");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            String op = sc.nextLine().trim();

            if (op.equals("1")) {
                qtd = cadastrarPessoa(v, qtd, sc);
            } else if (op.equals("2")) {
                imprimirPessoas(v, qtd);
            } else if (op.equals("3")) {
                int idx = maisVelhaIMCMagreza(v, qtd);
                if (idx == -1) System.out.println("Nenhuma pessoa com IMC < 18.5.");
                else System.out.println("Índice: " + idx + " (nome: " + v[idx].nome + ")");
            } else if (op.equals("4")) {
                insertionSortPorNome(v, qtd);
                System.out.println("Ordenado.");
            } else if (op.equals("5")) {
                System.out.print("Limite IMC: ");
                double limite = Double.parseDouble(sc.nextLine().trim());
                double media = mediaIdadePorIMC(v, qtd, limite);
                if (Double.isNaN(media)) System.out.println("Nenhuma pessoa atende.");
                else System.out.printf("Média de idade: %.2f%n", media);
            } else if (op.equals("0")) {
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }

        sc.close();
    }

    public static int cadastrarPessoa(Pessoa[] v, int qtd, Scanner sc) {
        if (qtd >= v.length) return qtd;

        String nome;
        while (true) {
            System.out.print("Nome: ");
            nome = sc.nextLine().trim();
            if (nome.isEmpty()) continue;
            int achou = buscarPorNome(v, qtd, nome);
            if (achou != -1) System.out.println("Nome já existente.");
            else break;
        }

        Pessoa p = new Pessoa();
        p.nome = nome;

        while (true) {
            try {
                System.out.print("Idade: ");
                p.idade = Integer.parseInt(sc.nextLine().trim());
                if (p.idade < 0) continue;
                break;
            } catch (Exception e) {}
        }

        while (true) {
            try {
                System.out.print("Peso: ");
                p.peso = Double.parseDouble(sc.nextLine().trim());
                if (p.peso <= 0) continue;
                break;
            } catch (Exception e) {}
        }

        while (true) {
            try {
                System.out.print("Altura: ");
                p.altura = Double.parseDouble(sc.nextLine().trim());
                if (p.altura <= 0) continue;
                break;
            } catch (Exception e) {}
        }

        v[qtd] = p;
        return qtd + 1;
    }

    public static int buscarPorNome(Pessoa[] v, int qtd, String nome) {
        for (int i = 0; i < qtd; i++) {
            if (v[i] != null && v[i].nome.equalsIgnoreCase(nome)) return i;
        }
        return -1;
    }

    public static void imprimirPessoas(Pessoa[] v, int qtd) {
        if (qtd == 0) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }
        for (int i = 0; i < qtd; i++) {
            Pessoa p = v[i];
            double imc = calcularIMC(p);
            System.out.printf("%d: %s | Idade: %d | Peso: %.2f | Altura: %.2f | IMC: %.2f%n",
                    i, p.nome, p.idade, p.peso, p.altura, imc);
        }
    }

    public static double calcularIMC(Pessoa p) {
        return p.peso / (p.altura * p.altura);
    }

    public static int maisVelhaIMCMagreza(Pessoa[] v, int qtd) {
        int idx = -1;
        int maiorIdade = -1;
        for (int i = 0; i < qtd; i++) {
            double imc = calcularIMC(v[i]);
            if (imc < 18.5) {
                if (v[i].idade > maiorIdade) {
                    maiorIdade = v[i].idade;
                    idx = i;
                }
            }
        }
        return idx;
    }

    public static void insertionSortPorNome(Pessoa[] v, int qtd) {
        for (int i = 1; i < qtd; i++) {
            Pessoa key = v[i];
            int j = i - 1;
            while (j >= 0 && v[j].nome.compareToIgnoreCase(key.nome) > 0) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = key;
        }
    }

    public static double mediaIdadePorIMC(Pessoa[] v, int qtd, double limiteIMC) {
        int soma = 0;
        int count = 0;
        for (int i = 0; i < qtd; i++) {
            double imc = calcularIMC(v[i]);
            if (imc >= limiteIMC) {
                soma += v[i].idade;
                count++;
            }
        }
        if (count == 0) return Double.NaN;
        return (double) soma / count;
    }
}
