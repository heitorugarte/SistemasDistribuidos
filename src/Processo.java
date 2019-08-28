
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

	public int getId() {
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
			return "Id: " + this.id + "\nAtivo: " + this.ativo + "\nId Sucessor: " + this.sucessor.getId()
					+ "\nId Antecessor: " + this.antecessor.getId();
		} else if (this.antecessor == null && this.sucessor != null) {
			return "Id: " + this.id + "\nId sucessor: " + this.sucessor.getId() + "\nProcesso inicial";
		} else {
			return "Id: " + this.id + "\nId Antecessor: " + this.antecessor.getId() + "\nProcesso final";
		}
	}
}
