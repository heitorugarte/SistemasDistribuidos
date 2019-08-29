
public class Processo {

	private int id;
	private boolean ativo;
	private Processo sucessor, antecessor;

	public Processo(int id) {
		this.id = id;
	}

	public Processo(int id, Processo antecessor) {
		this.id = id;
		this.antecessor = antecessor;
	}

	public Processo(int id, Processo sucessor, Processo antecessor) {
		this.id = id;
		this.sucessor = sucessor;
		this.antecessor = antecessor;
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
}
