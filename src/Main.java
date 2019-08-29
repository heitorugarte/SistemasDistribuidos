import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		//Lista de processos
		ArrayList<Processo> processos = new ArrayList<Processo>();

		int quantidadeDeProcessos = 10;

		/*
		 * Instancia os processos, estabelece a liga��o entre sucessor e antecessores
		 * e os insere na lista.
		 * 	-> quantidadeDeProcessos: determina a quantidade de processos � serem iniciados.
		 */
		for (int i = 0; i < quantidadeDeProcessos; i++) {
			if (processos.size() == 0) {
				processos.add(new Processo(i));
			} else {
				processos.add(new Processo(i, processos.get(i - 1)));
				processos.get(i-1).setSucessor(processos.get(i));
				processos.get(i).setAntecessor(processos.get(i-1));
			}
		}
		
		/*
		 * Visualiza��o do estado inicial dos processos (p�s-cria��o)
		 */
		for (int i = 0; i < processos.size(); i++)
		{
			System.out.println(processos.get(i).ToString() + "\n\n");
		}
		
		
	}

}
