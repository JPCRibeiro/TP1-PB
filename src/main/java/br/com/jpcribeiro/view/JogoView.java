package br.com.jpcribeiro.view;

import br.com.jpcribeiro.model.Jogo;
import br.com.jpcribeiro.service.JogoService;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class JogoView {
    private final JogoService service = new JogoService();
    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        boolean rodando = true;

        while (rodando) {
            System.out.println("**** Steam ****");
            System.out.println("1 - Adicionar Jogo");
            System.out.println("2 - Listar Jogo");
            System.out.println("3 - Atualizar Jogo");
            System.out.println("4 - Remover Jogo");
            System.out.println("0 - Sair");
            System.out.println("Escolha uma opção:");

            int opcao = -1;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, tente novamente!");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarJogo();
                    break;
                case 2:
                    listarJogos();
                    break;
                case 3:
                    atualizarJogo();
                    break;
                case 4:
                    removerJogo();
                    break;
                case 0:
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente!");
            }
        }
        System.out.println("Programa encerrado.");
    }

    private void removerJogo() {
        try {
            System.out.println("ID do jogo para remover");
            int id = Integer.parseInt(scanner.nextLine());
            service.remover(id);
            System.out.println("Jogo removido com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido");
        } catch (NoSuchElementException e) {
            System.out.println("Jogo não encontrado");
        }
    }

    private void atualizarJogo() {
        try {
            System.out.println("ID do jogo para atualizar");
            int id = Integer.parseInt(scanner.nextLine());
            Jogo jogo = service.buscar(id);
            if (jogo == null) {
                System.out.println("Jogo não encontrado");
                return;
            }
            System.out.println("Novo nome do jogo: ");
            String nome = scanner.nextLine();
            System.out.println("Novo preço do jogo: ");
            double preco = Double.parseDouble(scanner.nextLine());
            System.out.println("Nova descrição do jogo: ");
            String descricao = scanner.nextLine();
            jogo.setNome(nome);
            jogo.setPreco(preco);
            jogo.setDescricao(descricao);
            service.atualizar(jogo);
            System.out.println("Jogo atualizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Jogo não encontrado");
        }
    }

    private void listarJogos() {
        System.out.println("Lista de jogos: ");
        for (Jogo p : service.listar()) {
            System.out.printf("ID: %d | Nome: %s | Preço: R$ %.2f\n", p.getId(), p.getNome(), p.getPreco());
        }
        if (service.listar().isEmpty()) {
            System.out.println("Nenhum jogo cadastrado");
        }
    }

    private void adicionarJogo() {
        try {
            System.out.println("Nome do jogo: ");
            String nome = scanner.nextLine();
            System.out.println("Preço do jogo: ");
            double preco = Double.parseDouble(scanner.nextLine());
            System.out.println("Descrição do jogo: ");
            String descricao = scanner.nextLine();
            int id = service.gerarId();
            Jogo jogo = new Jogo(id, descricao, preco, descricao);
            service.adicionar(jogo);
            System.out.println("Jogo adicionado com sucesso! ID: " + id);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

