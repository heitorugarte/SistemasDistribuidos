
public class Processo extends Thread {

	private int id;
	private boolean ativo, isCoordenador;
	private static Processo coordenador;
	private Processo sucessor, antecessor;

	public Processo(int id) {
		this.id = id;
		this.isCoordenador = false;
	}

	public Processo(int id, Processo antecessor) {
		this.id = id;
		this.antecessor = antecessor;
		this.isCoordenador = false;
	}

	public Processo(int id, Processo sucessor, Processo antecessor) {
		this.id = id;
		this.sucessor = sucessor;
		this.antecessor = antecessor;
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

	public boolean fazerRequisicao() {
		if (Processo.coordenador.isAtivo()) {
			return true;
		}
		return false;
	}

	public void run() {
		System.out.println("O processo " + this.getIdProcesso() + " está executando.");
		while (true) {
			try {
				if (!isCoordenador) {
					if (!fazerRequisicao()) {
						// Eleicao
					}
				}
				Thread.sleep(25000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
