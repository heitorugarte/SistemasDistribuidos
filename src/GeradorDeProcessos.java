public class GeradorDeProcessos extends Thread {

	private long tempoParaGerar = 30000;

	public void run() {
		int id = 0;
		while (true) {
			Processo novo;
			if (Main.processos.size() == 0) {
				novo = new Processo(id++);
				novo.setSucessor(novo);
			} else {
				novo = new Processo(id++);
				novo.setAntecessor(Main.processos.get(id - 2));
				novo.setSucessor(Main.processos.get(0));
				Main.processos.get(id - 2).setSucessor(novo);
			}
			System.out.println("Gerador: Um novo processo foi criado. (Processo " + novo.getIdProcesso() + ")\n");
			novo.start();
			Main.processos.add(novo);
			try {
				Thread.sleep(tempoParaGerar);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}