import java.util.ArrayList;

public class Processo extends Thread {

	private int id;
	private boolean ativo, isCoordenador;
	private static Processo coordenador = null;
	private static boolean eleicao = false;
	private Processo sucessor, antecessor;

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

	public boolean fazerRequisicao() {
		if (Processo.coordenador != null && Processo.coordenador.isAtivo()) {
			System.out.println("Processo " + this.getIdProcesso() + ": Fez uma requisicao ao coordenador (Processo "
					+ Processo.coordenador.getIdProcesso() + ") com sucesso.\n");
			return true;
		} else {
			System.out.println(
					"Processo " + this.getIdProcesso() + ": Verificou que o coordenador não está respondendo.");
			return false;
		}
	}

	public void run() {
		System.out.println("Processo " + this.getIdProcesso() + ": Começou a executar.");
		this.ativo = true;
		while (true) {
			try {
				if (!isCoordenador) { // -> O processo só faz uma requisicao se não for coordenador
					if (!fazerRequisicao() && !Processo.eleicao) { // Se o coordenador não estiver ativo (retornar
																	// false)
																	// && não houver um processo de eleição em aberto
						iniciarEleicao(); // Inicia uma eleicao
					}
				}
				Thread.sleep(tempoParaRequisicao); // A cada 25 segundos o processo faz uma requisicao
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void iniciarEleicao() {
		System.out.println("Processo " + this.getIdProcesso() + ": Iniciou uma eleição de novo coordenador.\n");
		Processo.eleicao = true;
		ArrayList<Processo> processos = new ArrayList<Processo>();
		processos.add(this);

		if (this.sucessor != null) {
			this.sucessor.procedimentoEleicao(processos);
		}
	}

	public void procedimentoEleicao(ArrayList<Processo> processos) {
		if (processos.get(0) == this) {
			String estadoDaLista = "";
			for (Processo p : processos) {
				estadoDaLista += p.getIdProcesso() + ", ";
			}
			System.out.println("Eleição: A mensagem de eleição voltou à origem. Estado da lista de processos: \n"
					+ estadoDaLista + "\n");
			Processo maiorProcesso = this;
			for (Processo p : processos) {
				if (p.getIdProcesso() > maiorProcesso.getIdProcesso()) {
					maiorProcesso = p;
				}
			}
			maiorProcesso.virarCoordenador();
			System.out.println(
					"Eleição: novo coordenador definido. (Processo " + Processo.coordenador.getIdProcesso() + ")\n");
		} else {
			processos.add(this);
			if (this.sucessor != null) {
				this.sucessor.procedimentoEleicao(processos);
			} else {
				processos.get(0).procedimentoEleicao(processos);
			}
		}
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
