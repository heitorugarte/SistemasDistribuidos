import java.util.Random;

public class ExterminadorDeProcessos extends Thread {

	private long tempoParaMatarProcesso = 80000;

	public void run() {
		while (true) {
			try {
				Thread.sleep(tempoParaMatarProcesso);
				Random dado = new Random();
				int sorteio = dado.nextInt(Main.processos.size());
				Main.processos.get(sorteio).desativar();
				System.out.println("Exterminador: O processo " + Main.processos.get(sorteio).getIdProcesso()
						+ " foi desativado.\n");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
