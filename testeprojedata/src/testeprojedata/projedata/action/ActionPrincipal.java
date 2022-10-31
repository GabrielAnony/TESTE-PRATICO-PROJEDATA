package testeprojedata.projedata.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import testeprojedata.projedata.form.FuncionarioForm;
import testeprojedata.projedata.form.PessoaForm;

public class ActionPrincipal {

	public void executa() {

		try {
			// ---------------------------------------------------------------------------------------//
			System.out.println("3.1 � Inserir todos os funcion�rios, na mesma ordem e informa��es da tabela acima.");
			List<FuncionarioForm> func = this.inserirFuncionario();
			// func.forEach(System.out::println);

			// ---------------------------------------------------------------------------------------//
			System.out.println("3.2 � Remover o funcion�rio �Jo�o� da lista.");
			for (FuncionarioForm aux : func) {
				if (aux.getPessoa().getNome().equals("Jo�o")) {
					func.remove(aux);
					break;
				}
			}
			// func.forEach(System.out::println);

			// ---------------------------------------------------------------------------------------//
			System.out.println("3.3 � Imprimir todos os funcion�rios com todas suas informa��es, sendo que:");
			// � informa��o de data deve ser exibido no formato dd/mm/aaaa;
			// � informa��o de valor num�rico deve ser exibida no formatado com
			// separador de milhar como ponto e decimal como v�rgula.
			for (FuncionarioForm aux : func) {
				System.out.println(this.retornaFunc(aux));
			}

			// ---------------------------------------------------------------------------------------//
			System.out.println(
					"3.4 � Os funcion�rios receberam 10% de aumento de sal�rio, atualizar a lista de funcion�rios com novo valor.");
			for (FuncionarioForm aux : func) {
				System.out.println(this.retornaFunc(this.retornaAumento(aux)));
			}

			// ---------------------------------------------------------------------------------------//
			System.out.println(
					"3.5 � Agrupar os funcion�rios por fun��o em um MAP, sendo a chave a �fun��o� e o valor a �lista de funcion�rios�.");
			HashMap<String, List<FuncionarioForm>> hashAgrup = new HashMap<String, List<FuncionarioForm>>();

			for (FuncionarioForm aux : func) {
				String funcao = aux.getFuncao();
				List<FuncionarioForm> funcionarios = hashAgrup.get(funcao);
				if (funcionarios == null) {
					funcionarios = new ArrayList<>();
					funcionarios.add(aux);
					hashAgrup.put(funcao, funcionarios);
					continue;
				}
				funcionarios.add(aux);
			}
			// System.out.println(hashAgrup);

			// ---------------------------------------------------------------------------------------//
			System.out.println("3.6 � Imprimir os funcion�rios, agrupados por fun��o.");
			for (Entry<String, List<FuncionarioForm>> agrupar : hashAgrup.entrySet()) {
				List<FuncionarioForm> aux = agrupar.getValue();
				String nomesFunc = "";
				for (FuncionarioForm funcionario : aux) {
					nomesFunc += funcionario.getPessoa().getNome() + " - ";
				}
				System.out.println(agrupar.getKey() + ": " + nomesFunc.substring(0, nomesFunc.length() - 3));
			}

			// ---------------------------------------------------------------------------------------//
			System.out.println("3.8 � Imprimir os funcion�rios que fazem anivers�rio no m�s 10 e 12.");
			for (FuncionarioForm aux : func) {
				if (aux.getPessoa().getDataNasc().getMonthValue() == 10
						|| aux.getPessoa().getDataNasc().getMonthValue() == 12) {
					System.out.println(aux.getPessoa().getNome() + " /// " + aux.getPessoa().getDataNasc());
				}
			}

			// ---------------------------------------------------------------------------------------//
			System.out.println("3.9 � Imprimir o funcion�rio com a maior idade, exibir os atributos: nome e idade.");
			FuncionarioForm maiorIdade = func.get(0);
			for (FuncionarioForm aux : func) {
				if (aux.getPessoa().getDataNasc().isBefore(maiorIdade.getPessoa().getDataNasc())) {
					maiorIdade = aux;
				}
			}
			System.out.println(maiorIdade.getPessoa().getNome() + " "
					+ Period.between(maiorIdade.getPessoa().getDataNasc(), LocalDate.now()).getYears() + " Anos");

			// ---------------------------------------------------------------------------------------//
			System.out.println("3.10 � Imprimir a lista de funcion�rios por ordem alfab�tica.");
			ArrayList nomes = new ArrayList<>();
			for (FuncionarioForm aux : func) {
				nomes.add(aux.getPessoa().getNome());
			}
			Collections.sort(nomes);
			System.out.println(nomes);

			// ---------------------------------------------------------------------------------------//
			System.out.println("3.11 � Imprimir o total dos sal�rios dos funcion�rios.");
			double salarioTotal = 0;
			for (FuncionarioForm aux : func) {
				salarioTotal = salarioTotal + aux.getSalario().doubleValue();
			}
			Locale localBR = new Locale("pt", "BR");
			NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(localBR);
			System.out.println("o total dos sal�rios dos funcion�rios: " + formatadorMoeda.format(salarioTotal));

			// ---------------------------------------------------------------------------------------//
			System.out.println(
					"3.12 � Imprimir quantos sal�rios m�nimos ganha cada funcion�rio, considerando que o sal�rio m�nimo � R$1212.00.");
			DecimalFormat df = new DecimalFormat("#.0");
			for (FuncionarioForm aux : func) {
				double qtdSalario = 0;
				qtdSalario = aux.getSalario().doubleValue() / 1212;

				System.out.println(aux.getPessoa().getNome() + " ganha " + df.format(qtdSalario) + " sal�rios m�nimo");

			}

			System.out.println("FIM");

		} catch (Exception e) {
			System.out.println("Ocorreu um erro durante a execu��o da fun��o executa da classe ActionPrincipal. Erro: "
					.concat(e.getMessage()));
		}

	}

	private FuncionarioForm retornaAumento(FuncionarioForm aux) {
		double salario = aux.getSalario().doubleValue();
		double aumento = 10.0;
		double valorTotal = salario + (salario * aumento / 100);
		aux.setSalario(new BigDecimal(valorTotal));
		return aux;

	}

	private String retornaFunc(FuncionarioForm aux) {
		DateTimeFormatter forma = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String data = aux.getPessoa().getDataNasc().format(forma);
		Locale localBR = new Locale("pt", "BR");
		NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(localBR);

		return "Nome :" + aux.getPessoa().getNome() + "//DataNasc: " + data + "//Fun��o: " + aux.getFuncao()
				+ "//Salario: " + formatadorMoeda.format(aux.getSalario());
	}

	public List<FuncionarioForm> inserirFuncionario() {
		try {
			final List<FuncionarioForm> funcionarios = new ArrayList<>();

			funcionarios.add(new FuncionarioForm(new BigDecimal(2009.44), "Operador",
					new PessoaForm("Maria", LocalDate.parse("2000-10-18"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(2285.38), "Operador",
					new PessoaForm("Jo�o", LocalDate.parse("1990-05-12"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(9836.14), "Coordenador",
					new PessoaForm("Caio", LocalDate.parse("1961-05-02"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(19119.88), "Diretor",
					new PessoaForm("Miguel", LocalDate.parse("1988-10-14"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(2234.68), "Recepcionista",
					new PessoaForm("Alice", LocalDate.parse("1995-01-05"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(1582.72), "Operador",
					new PessoaForm("Heitor", LocalDate.parse("1999-11-19"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(4071.84), "Contador",
					new PessoaForm("Arthur", LocalDate.parse("1993-03-31"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(3017.45), "Gerente",
					new PessoaForm("Laura", LocalDate.parse("2000-07-08"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(1606.85), "Eletricista",
					new PessoaForm("Helo�sa", LocalDate.parse("2003-05-24"))));
			funcionarios.add(new FuncionarioForm(new BigDecimal(2799.93), "Gerente",
					new PessoaForm("Helena", LocalDate.parse("1996-09-02"))));

			return funcionarios;

		} catch (Exception e) {
			System.out.println(
					"Ocorreu um erro durante a execu��o da fun��o inserirFuncionario da classe ActionPrincipal. Erro: "
							.concat(e.getMessage()));
			return Collections.emptyList();
		}

	}

}
