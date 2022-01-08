import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class BattleShip {

	static String[][] ocean;
	static int totalRows;
	static int totalColumns;
	static String[][] printOcean;
	static int totalBoats;

	public static void main( String[] args ) throws Exception {
		Scanner sc = new Scanner( System.in );
		printOcean = setOcean( sc, args );
		startGame( sc );
		sc.close();

	}

	public static String[][] setOcean( Scanner inputFileName, String[] args )
			throws FileNotFoundException {
		
		String filename;
		if(args.length!=0) {
			filename = args[0];
		} else {
			System.out.print( "Battle ship filename: " );
			filename  = inputFileName.nextLine();
		}
		
		File battleShipFile = new File( filename );
		Scanner scanFile = new Scanner( battleShipFile );

		for ( int line = 0; line < 2; line++ ) {
			String lineContents = scanFile.nextLine();
			String[] widthAndHeight = lineContents.split( "\\s+" );

			if ( widthAndHeight[0].toLowerCase().equals( "width" ) ) {
				totalColumns = Integer.parseInt( widthAndHeight[1] );
			} else if ( widthAndHeight[0].toLowerCase().equals( "height" ) ) {
				totalRows = Integer.parseInt( widthAndHeight[1] );
			}
		}

		ocean = new String[totalRows][totalColumns];
		String[][] intlOcean = new String[totalRows][totalColumns];

		int curr_row = 0;

		totalBoats = 0;

		while ( scanFile.hasNextLine() ) {
			String[] lineContents = scanFile.nextLine().split( "\\s+" );

			for ( int i = 1; i < lineContents.length; i++ ) {
				ocean[curr_row][i - 1] = lineContents[i];
				intlOcean[curr_row][i - 1] = ".";

				if ( !lineContents[i].toLowerCase().equals( "w" ) ) {
					totalBoats += 1;
				}
			}
			curr_row++;

		}

		scanFile.close();

		return intlOcean;

	}

	public static void startGame( Scanner scanInput ) {

		int cols;
		int rows;
		String id = "";
		printBattleField();
		boolean check = false;

		System.out.println( "totalboats: "+ totalBoats );
		
		while ( totalBoats > 0 ) {
			check = false;
			System.out.print( "enter column coordinate (0 <= column < "
					+ totalColumns + " ): " );
			cols = scanInput.nextInt();
			System.out.print( "enter row coordinate    (0 <= column < "
					+ totalRows + " ): " );
			rows = scanInput.nextInt();
			id = ocean[rows][cols];

			if(id.equals( "w" )) {
				System.out.println( "-----MISS-----" );
				printOcean[rows][cols] = "w";
				printBattleField();
				
			}
			
			else {
				System.out.println("-----HIT-----" );
				printOcean[rows][cols] = "x";
				totalBoats--;
				check = hitRow( rows, cols, id );
				System.out.println( "check: " +check);
				if(!check) {
					System.out.println( "In else" );
					hitColumn( rows, cols, id );
				}
				
				printBattleField();
			}
			
			System.out.println( "totalboats: " + totalBoats );	
		}
		
		System.out.println( "GAME OVER!" );

	}

	public static boolean hitRow( int rows, int cols, String id ) {
		String currId = "";
		int countRowHit = 0;
		
		boolean marked = false;
		
		System.out.println( "IN ROW" );

		for ( int c = cols ; c >= 0; c-- ) {

			currId = ocean[rows][c];

			if ( id.equals( currId ) && (c!=cols || cols==0) ) {
				if ( c == 0 && ocean[rows][c]
						.equals( ocean[rows][totalColumns - 1] ) ) {
					if(cols == 0) {
						marked = true;
						countRowHit += 1;
						totalBoats -= 1;
						printOcean[rows][c] = "x";
						printOcean[rows][totalColumns - 1] = "x";
					} else {
						marked = true;
						countRowHit += 2;
						totalBoats -= 2;
						printOcean[rows][c] = "x";
						printOcean[rows][totalColumns - 1] = "x";
					}
				} else {
					countRowHit++;
					totalBoats--;
					printOcean[rows][c] = "x";
				}

			} else {
				break;
			}
			
		}

		for ( int c = cols + 1; c < totalColumns; c++ ) {
			
			currId = ocean[rows][c];

			if ( id.equals( currId ) ) {
				
				countRowHit++;
				totalBoats--;
				printOcean[rows][c] = "x";
				if (c == (totalColumns-1) && marked == true) {
					totalBoats+=1;
				}
				
			} else {
				break;
			}
		}
		
		if(countRowHit > 0) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean hitColumn( int rows, int cols, String id ) {
		String currId = "";
		int countColHit = 0;

		for ( int r = rows - 1; r >= 0; r-- ) {
			currId = ocean[r][cols];
			if ( id.equals( currId ) ) {
				countColHit++;
				totalBoats--;
				printOcean[r][cols] = "x";

			} else {
				break;
			}
		}
		
		for ( int r = rows + 1; r < totalRows; r++ ) {
			currId = ocean[r][cols];

			if ( id.equals( currId ) ) {
				countColHit++;
				totalBoats--;
				printOcean[r][cols] = "x";

			} else {
				break;
			}
		}
		
		if(countColHit > 0) {
			return true;
		} else {
			return false;
		}

	}

	public static void printBattleField() {
		System.out.print( "    " );

		for ( int col = 0; col < totalColumns; col++ ) {
			System.out.print( col + "  " );
		}
		System.out.println( "----> Columns" );

		for ( int i = 0; i < totalRows; i++ ) {
			System.out.print( i + ":  " );

			for ( int j = 0; j < totalColumns; j++ ) {
				System.out.print( printOcean[i][j] + "  " );
			}
			System.out.println();
		}
		System.out.println();

	}

}
