import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Processo> processos = new ArrayList<Processo>();

		int quantidadeDeProcessos = 10;

		for (int i = 0; i < quantidadeDeProcessos; i++) {
			if (processos.size() == 0) {
				processos.add(new Processo(i));
			} else {
				processos.add(new Processo(i, processos.get(i - 1)));
				processos.get(i-1).setSucessor(processos.get(i));
				processos.get(i).setAntecessor(processos.get(i-1));
			}
		}
		
		for (int i = 0; i < processos.size(); i++)
		{
			System.out.println(processos.get(i).ToString() + "\n\n");
		}

		
		
	}

}
