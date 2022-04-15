package br.com.treinaweb.jpa.ui;

import java.util.List;
import java.util.Scanner;

import br.com.treinaweb.jpa.models.Pessoa;
import br.com.treinaweb.jpa.service.impl.PessoaService;
import br.com.treinaweb.jpa.service.interfaces.PessoaBuscaPeloNome;

public class Main {
	private static Scanner SCANNER = new Scanner(System.in);
	private static PessoaBuscaPeloNome PESSOASERVICE = new PessoaService();

	public static void main(String[] args) {
		int opcao = 0;
		while (opcao != 6) {

			System.out.println("\nEscolha uma opção:");
			System.out.println("1. Listar Pessoas");
			System.out.println("2. Inserir Pessoa");
			System.out.println("3. Atualizar Pessoa");
			System.out.println("4. Excluir Pessoa");
			System.out.println("5. Listar Pessoa pelo nome");
			System.out.println("6. Sair");
			System.out.print("\nSua opção: ");

			opcao = SCANNER.nextInt();
			SCANNER.nextLine();
			switch (opcao) {
			case 1: {
				listarPessoas();
				break;
			}
			case 2: {
				inserirPessoa();
				break;
			}
			case 3: {
				atualizarPessoa();
				break;
			}
			case 4: {
				excluirPessoa();
				break;
			}
			case 5: {
				pesquisarPessoaPeloNome();
				break;
			}

			default:
				System.out.println("***Opção inválida***");
				break;
			}

		}
		SCANNER.close();
		System.out.println("Bye! ;)");
	}

	private static void pesquisarPessoaPeloNome() {
		System.out.println("\n***Pesquisa de pessoa por nome***");
		System.out.print("Digite o nome a ser pesquisado: ");
		String nomeASerPesquisado = SCANNER.nextLine();
		PESSOASERVICE.searchByName(nomeASerPesquisado).forEach(p -> {
			System.out.println(
					String.format("%d - %s %s - %d anos", p.getId(), p.getNome(), p.getSobrenome(), p.getIdade()));
		});

	}

	private static void excluirPessoa() {
		System.out.println("\n***Exclusão de Pessoa***");
		System.out.print("Digite o ID da pessoa a ser excluída: ");
		int idPessoaASerRemovida = SCANNER.nextInt();
		SCANNER.nextLine();
		PESSOASERVICE.deleteById(idPessoaASerRemovida);
		System.out.println("Pessoa excluída com sucesso!");
	}

	private static void atualizarPessoa() {
		System.out.println("--Atualização da Pessoa --");
		System.out.print("Digite o ID da pessoa a ser atualizada: ");
		int idPessoa = SCANNER.nextInt();
		SCANNER.nextLine();
		Pessoa pessoaAtual = PESSOASERVICE.getById(idPessoa);
		if (pessoaAtual != null) {
			System.out.println("Pessoa encontrada: ");
			System.out.println(String.format(" - Nome: %s", pessoaAtual.getNome()));
			System.out.println(String.format(" - Sobrenome: %s", pessoaAtual.getSobrenome()));
			System.out.println(String.format(" - Idade: %d", pessoaAtual.getIdade()));
			System.out.print("--Novo Nome: ");
			pessoaAtual.setNome(SCANNER.nextLine());
			System.out.print("--Novo Sobrenome: ");
			pessoaAtual.setSobrenome(SCANNER.nextLine());
			System.out.print("--Nova Idade: ");
			pessoaAtual.setIdade(SCANNER.nextInt());
			PESSOASERVICE.update(pessoaAtual);
			System.out.println("Pessoa atualizada com sucesso!");
		} else {
			System.out.println("ID não encontrado!");
		}
	}

	private static void inserirPessoa() {
		System.out.println("***Inclusão de Pessoa***");
		Pessoa novaPessoa = new Pessoa();
		System.out.print("Nome: ");
		novaPessoa.setNome(SCANNER.nextLine());
		System.out.print("Sobrenome: ");
		novaPessoa.setSobrenome(SCANNER.nextLine());
		System.out.print("Idade: ");
		novaPessoa.setIdade(SCANNER.nextInt());
		PESSOASERVICE.insert(novaPessoa);
		System.out.println("--Pessoa inserida com sucesso!--");

	}

	private static void listarPessoas() {
		System.out.println("***GERENCIAMENTO DE PESSOAS***");
		System.out.println(">>Lista de Pessoas Cadastradas: \n");
		try {
			List<Pessoa> pessoas = PESSOASERVICE.all();
			pessoas.forEach(p -> {
				System.out.println(
						String.format("%d - %s %s - %d anos", p.getId(), p.getNome(), p.getSobrenome(), p.getIdade()));
			});
			if (pessoas.isEmpty()) {
				System.out.println(" - Não existem pessoas cadastradas.");
			}
		} catch (Exception e) {
			System.out.println("Houve um erro ao utilizar a JPA: " + e.getMessage());

		}

	}

}
