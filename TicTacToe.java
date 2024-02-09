import java.util.Scanner;

public class TicTacTo {

	static Scanner reader = new Scanner(System.in);

	//fonction pour afficher une bannière de bienvenue :
	public static void banner() {
		String line1 = "**********************************************************\n";
		String line2 = "**                                                      **\n";
		String line3 = "**          BIENVENUE AU JEU DU TIC TAC TOE !           **\n";

		String welcome = line1 + line2 + line3 + line2 + line1;
		System.out.println(welcome);
	}

	//fonction pour afficher la matrice
	public static void printMatrice(char[][]matrice) {

		int i = 0;
		int j = 0;

		System.out.println("     1     2     3");	//mise en forme avec numéro des colonnes au dessus de la grille
		for(i=0; i<matrice.length; i++){
			for(j=0; j<matrice[i].length; j++){
				if(j<1){
					System.out.print(i+1 + " ");	//on imprime le numéro de chaque ligne en ajoutant 1 pour une meilleure compréhension pour l'utilisateur
					System.out.print(" [ " + matrice[i][j] + " ]");
				} else {
					System.out.print(" [ " + matrice[i][j] + " ]");
				}
			}
			System.out.println();
		}
	}

	//fonction qui va vérifier les différentes conditions de victoire
	public static char checkVictory(char[][]matrice){

		int i = 0;
		int j = 0;

		for(i=0; i<matrice.length; i++){
			char checkLine = matrice[i][0];					// 1ère condition vérifiée : les lignes.
			boolean inLine = true;							// On itère à travers chaque ligne et on attribue la valeur à vérifier au 1er indice de chaque ligne
			if(matrice[i][0] == '-'){						// On sort de la boucle s'il n'y a ni 'X' ni 'O'
				inLine = false;
			} else{
				for(j=1; j<matrice.length; j++){			// Sinon, on va scanner tous les éléments après le 1er de chaque ligne (qui a déjà été vérifié)
					if(matrice[i][j] != checkLine){			// Et on sort également de la boucle si on ne retrouve pas la même valeur
						inLine = false;
					}
				}
			}
			if(inLine){										// On retourne la valeur du 1er élément de la ligne si notre booléen ressort true.
				return checkLine;
			}
		}

		for(i=0; i<matrice.length; i++){					// Construction similaire pour les colonnes 
			char checkCol = matrice[0][i];					// Sauf qu'au lieu de prendre le 1er élément de chaque lingne ([i][0]),
			boolean inCol = true;							// on prend le 1er élément de chaque colonne ([0][i]).
			if(matrice[0][i] == '-'){
				inCol = false;
			} else{
				for(j=1; j<matrice.length; j++){
					if(matrice[j][i] != checkCol){
						inCol = false;
					}
				}
			}
			if(inCol){										// La condition de retour est semblable aux lignes
				return checkCol;
			}
		}

		
		char checkDiag1 = matrice[0][0];					// La construction est différente pour la 1ère diagonale.
		boolean inDiag1 = true;								// On part d'en haut à gauche pour finir en bas à droite. 
		if(matrice[0][0] == '-'){							// On prend donc comme référence le tout 1er élément du tableau : [0][0]
			inDiag1 = false;
		} else{
			for(i=0; i<matrice.length; i++){				// Ici, une seule boucle for suffit car l'indice des lignes est le même que celui des colonnes
				if(matrice[i][i] != checkDiag1){			// On vérifie donc la valeur de [i][i]
					inDiag1 = false;
				}
			}
		}
		if(inDiag1){
			return checkDiag1;
		}


		char checkDiag2 = matrice[0][2];					// Pour la 2ème diagonale, je n'ai pas trouvé le moyen d'itérer "proprement" dans les éléments.
		boolean inDiag2 = true;								// Je fais donc une vérification "manuelle", élément par élément.
		if(matrice[0][2] == '-'){
			inDiag2 = false;
		} else if(matrice[1][1] == matrice[0][2] && matrice[2][0] == matrice[0][2]){
			inDiag2 = true;
			return checkDiag2;
		}

		
		return ' ';									// Si aucune victoire n'a été détectée, la fonction renvoit un char vide,
	}												// et ne déclenchera pas le booléen de fin de partie.	


	//fonction servant à attribuer le bon symbole au bon joueur
	public static char switchToken(boolean permut, String p1, String p2){

		char token = ' ';										// On déclare une variable char vide

		if(permut){
			System.out.println(p1 + ", à vous de jouer (X) :");		// On change la valeur du token selon la valeur du booléen 
			token = 'X';										// qui sert à changer de joueur dans la fonction main
		} else{
			System.out.println(p2 + ", à vous de jouer (O) :");
			token = 'O';
		}

		return token;											// On retourne le token modifié

	}


	// fonction qui demande au joueur l'emplacement pour placer son pion
	public static void placeToken(char pion, char[][]matrice){

		int row = 0;
		int col = 0;

		boolean validPlace = false;		// variable booléenne qui servira à stopper la boucle quand l'utilisateur entre des coordonnées valides

		while(!validPlace){				//démarrage de la boucle 
			
			System.out.println("Indiquez la ligne (entre 1 et 3) :");
			row = reader.nextInt()-1;										// On demande des coordonnées entre 1 et 3 pour une meilleure compréhension 
			System.out.println("Indiquez la colonne (entre 1 et 3) :");
			col = reader.nextInt()-1;

			if(row<0 || col<0 || row>2 || col>2){															// Bloc de conditions qui se répètera dans la boucle while
				System.out.println("Cet emplacement est en dehors du plateau, veuillez recommencer :");		// si les coordonnées sont en dehors des limites du tableau
			} else if(matrice[row][col] != '-'){															// ou si l'emplacement désigné n'est pas un symbole "neutre"
				System.out.println("Cet emplacement est déjà pris, veuillez recommencer :");
			} else {
				validPlace = !validPlace;				// Si aucune des conditions ci-dessus, on sort de la boucle en inversant la valeur du booléen
			}
		}

		matrice[row][col] = pion;		// Et en fin de méthode, on attribue le pion à l'endroit indiqué par le joueur
		
	}


	// fonction pour vérifier si la grille est remplie, sert à gérer les cas de match nul
	public static boolean gridFull(char[][]matrice){

		boolean isGridFull = true;		// De la même manière que la méthode checkVictory, on déclare un booléen renvoyé par la méthode et nous servira dans la main
		char valeurRef = '-';			// A l'inverse de checkVictory, on ne se base pas sur la valeur d'un élément du tableau. On déclare directement le pion "neutre"


			for(int i=0; i<matrice.length; i++){			// On traverse la matrice en vérifiant qu'aucun élément n'est égal au pion neutre, le '-'
				for(int j=0; j<matrice[i].length; j++){
					if(matrice[i][j] == '-'){
						isGridFull = false;					// Si plus aucun élément ne vaut '-', alors on inverse la valeur du booléen
					}
				}
			}

		return isGridFull;			// On renvoie le booléen qui servira de condition dans la fonction main
		
	}

	public static void main(String[] args) {

		char answer;		// Cette variable sert à demander si on recommence une partie

		banner();			// On appelle la fonction banner pour accueillir comme il se doit nos joueurs.

		do{					// 1ère boucle do...while pour recommencer une partie selon la réponse obtenue


				int size = 3;
				char[][] tictactoe = new char[size][size];		//
																//
				for(int i=0; i<tictactoe.length; i++) {			// On initie la grille avec un tableau2D de 3x3 qu'on remplit d'un char "neutre"
					for(int j=0; j<tictactoe.length; j++) {		// Ce tableau sera le paramètre des 2 méthodes écrites plus haut
						tictactoe[i][j] = '-';					//
					}
				}


			printMatrice(tictactoe);				// On appelle au début du jeu l'affichage de la grille

			int round = 1;						// On déclare une variable pour compter les tours, variable qui sera incrémentée à chaque fin de tour.

			char pion = ' ';

			//System.out.println("\nAppuyez sur entrée pour démarrer une partie");
			//reader.nextLine();
			System.out.println("\nJoueur 1, quel est votre nom ?");
			String player1 = reader.nextLine();						
			System.out.println("Joueur2, quel est votre nom ?");
			String player2 = reader.nextLine();

			boolean permutPlayer = true;				// Ce booléen sert à changer de joueur et à attribuer le bon symbole. True pour joueur 1 et false pour joueur 2

			boolean finJeu = false;				// Ce booléen servira à marquer la fin d'une partie. Il est déclaré false à la base,
												// et devient true en cas de victoire ou d'égalité.
			while(!finJeu) {					// Il est la condition de cette boucle while qui encadre un tour complet.

				System.out.println("Round " + round);		// On indique aux joueurs le tour actuel

				pion = switchToken(permutPlayer, player1, player2); // On appelle la fonction switchToken sur la variable pion pour lui attribuer le bon symbole,
																	// et appeler le bon joueur
				

				placeToken(pion, tictactoe);		// On appelle ensuite la fonction placeToken pour demander au joueur de choisir un emplacement

				//check victory
				if(checkVictory(tictactoe) == 'X'){
					System.out.println(player1 + " a gagné !");		// Après chaque coup, on vérifie les conditions de victoire. On appelle notre méthode dans
					finJeu = true;									// une boucle if else if. Si le char renvoyé par la méthode est 'X', fin du jeu, message personnalisé
					printMatrice(tictactoe);							// et on affiche une dernière fois le plateau.

				} else if(checkVictory(tictactoe) == 'O'){
					System.out.println(player2 + " a gagné !");		// De même si le char renvoyé est 'O'
					finJeu = true;
					printMatrice(tictactoe);

				} else if(gridFull(tictactoe) == true){				// Si aucune victoire, on vérifie si le plateau est rempli.
					System.out.println("Egalité !");				// Si c'est le cas, ça signifie qu'il y a match nul et on sort de la boucle de jeu
					finJeu = true;
					printMatrice(tictactoe);

				} else {
					permutPlayer = !permutPlayer;					// Si aucune des conditions n'est vérifiée, le jeu continue : on change de joueur
					round++;										// et on passe au tour suivant et on affiche le plateau
					printMatrice(tictactoe);
				}	
			}

			
			reader.nextLine();
			System.out.println("Souhaitez-vous refaire une partie ? (O/N)");		// Une fois la partie finie, on donne la possibilité de recommencer.
			answer = reader.next().charAt(0);
			reader.nextLine();
			
		} while(answer == 'O' || answer == 'o');

		System.out.println("Bye !");		// Fin du programme
		
	}
	
}







