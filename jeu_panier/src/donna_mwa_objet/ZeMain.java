package donna_mwa_objet;

import java.util.concurrent.CompletableFuture;

public class ZeMain {

	public static void main(String[] args) throws Exception {
		Communicateur c =  new Communicateur();
		/*
		System.out.println("Appel sync...");
		System.out.println(" !Réponse sync! "+c.get("https://fafa.kroko.xyz/~grasset/cpi2/calculer.php"));
		System.out.println("Fin appel sync.");
		
		*/
		System.out.println("Appel async...");
		CompletableFuture<String> cf =  c.getAsync("https://fafa.kroko.xyz/~grasset/cpi2/calculer.php");		
		cf.thenApply(s->{
				System.out.println("\n !Réponse async! "+s);
				return s;
		});
		System.out.println("Fin appel async.");

		for (int i=0; i<100; i++) {
			System.out.print(i+".");
			Thread.sleep(100);
		}
	}

}
