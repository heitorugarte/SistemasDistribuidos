import java.util.ArrayList;

public class Processo extends Thread {

	// Id de identifica��o do processo
	private int id;

	/*
	 * ativo: Determina se o processo est� executando (respondendo) ou n�o.
	 * isCoordenador: Determina se o processo � coordenador.
	 */
	private boolean ativo, isCoordenador;

	// Refer�ncia est�tica ao coordenador dos processos.
	private static Processo coordenador = null;

	// eleicao: Refer�ncia est�tica para informar se est� ocorrendo um processo de
	// elei��o.
	private static boolean eleicao = false;

	// Refer�ncias para criar o encadeamento entre os processos.
	private Processo sucessor, antecessor;

	// Vari�veis para controle do tempo entre requisi��es e desativa��o do
	// coordenador.
	private long tempoParaRequisicao = 25000;
	private long tempoParaCoordenadorDesativar = 100000;

	public Processo(int id) {
		this.id = id;
		this.isCoordenador = false;
	}

	public int getIdProcesso() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Processo getSucessor() {
		return sucessor;
	}

	public void setSucessor(Processo sucessor) {
		this.sucessor = sucessor;
	}

	public Processo getAntecessor() {
		return antecessor;
	}

	public void setAntecessor(Processo antecessor) {
		this.antecessor = antecessor;
	}

	public void run() {
		System.out.println("Processo " + this.getIdProcesso() + ": Come�ou a executar.");
		this.ativo = true;
		while (true) {
			try {
				if (!isCoordenador) { // -> O processo s� faz uma requisicao se n�o for coordenador
					if (!fazerRequisicao() && !Processo.eleicao) { // Se o coordenador n�o estiver ativo
						// && n�o houver um processo de elei��o em aberto
						iniciarEleicao(); // Inicia uma eleicao
					}
				}
				Thread.sleep(tempoParaRequisicao); // A cada 25 segundos o processo faz uma requisicao
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean fazerRequisicao() {
		if (Processo.coordenador != null && Processo.coordenador.isAtivo()) {
			System.out.println("Processo " + this.getIdProcesso() + ": Fez uma requisicao ao coordenador (Processo "
					+ Processo.coordenador.getIdProcesso() + ") com sucesso.\n");
			return true;
		} else {
			System.out.println(
					"Processo " + this.getIdProcesso() + ": Verificou que o coordenador n�o est� respondendo.");
			return false;
		}
	}

	private void iniciarEleicao() {
		System.out.println("Processo " + this.getIdProcesso() + ": Iniciou uma elei��o de novo coordenador.\n");
		Processo.eleicao = true;
		ArrayList<Processo> processos = new ArrayList<Processo>();
		processos.add(this);

		if (this.sucessor != null) {
			this.sucessor.procedimentoEleicao(processos);
		}
	}

	public void procedimentoEleicao(ArrayList<Processo> processos) {
		if (this.ativo) {
			if (processos.get(0) == this) {
				Processo maiorProcesso = this;
				for (Processo p : processos) {
					if (p.getIdProcesso() > maiorProcesso.getIdProcesso()) {
						maiorProcesso = p;
					}
				}
				maiorProcesso.virarCoordenador();
				System.out
						.println("Elei��o: novo coordenador definido. (Processo " + Processo.coordenador.getIdProcesso()
								+ ")\nLista final de processos na elei��o: \n" + ImprimirListaEleicao(processos));
			} else {
				processos.add(this);
				System.out.println("Elei��o: O processo " + this.getIdProcesso()
						+ " est� passando adiante a mensagem de elei��o. Estado da lista:\n"
						+ ImprimirListaEleicao(processos));
				if (this.sucessor != null) {
					this.sucessor.procedimentoEleicao(processos);
				} else {
					processos.get(0).procedimentoEleicao(processos);
				}
			}
		} else {
			if (this.sucessor != null) {
				this.sucessor.procedimentoEleicao(processos);
			} else {
				processos.get(0).procedimentoEleicao(processos);
			}
		}
	}

	private String ImprimirListaEleicao(ArrayList<Processo> processos) {
		String estadoDaLista = "";
		int quantidadeDeProcessos = processos.size() - 1;
		int index = 0;
		for (Processo p : processos) {
			if (index < quantidadeDeProcessos) {
				estadoDaLista += p.getIdProcesso() + ", ";
			} else {
				estadoDaLista += p.getIdProcesso();
			}
		}
		return estadoDaLista;
	}

	public void desativar() {
		this.ativo = false;
	}

	public void virarCoordenador() {
		Processo.coordenador = this;
		Processo.eleicao = false;
		this.isCoordenador = true;
		try {
			Thread.sleep(tempoParaCoordenadorDesativar);
			System.out.println("Coordenador (processo " + this.getIdProcesso() + "): ficou inativo.\n");
			this.ativo = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String ToString() {
		if (sucessor != null && antecessor != null) {
			return "Id: " + this.id + "\nAtivo: " + this.ativo + "\nId Sucessor: " + this.sucessor.getIdProcesso()
					+ "\nId Antecessor: " + this.antecessor.getIdProcesso();
		} else if (this.antecessor == null && this.sucessor != null) {
			return "Id: " + this.id + "\nId sucessor: " + this.sucessor.getIdProcesso() + "\nProcesso inicial";
		} else {
			return "Id: " + this.id + "\nId Antecessor: " + this.antecessor.getIdProcesso() + "\nProcesso final";
		}
	}
}
