import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class BabyNames {

	public static void main(String[] args) throws FileNotFoundException {

		String gender = null;
		int min = 0;
		int max = 0;
		int order = 0;
		int numberofnames = 0;

		Scanner input = new Scanner(System.in);

		//Get User input
		System.out.println("Baby Name Generator");
		System.out.println("Male or Female? (type m or f)");
		gender = input.next();
		System.out.println("Minimum name length?");
		min = input.nextInt();
		System.out.println("Maximum name length?");
		max = input.nextInt();
		System.out.println("Markov Order?");
		order = input.nextInt();
		System.out.println("Number of names to generate?");
		numberofnames = input.nextInt();

		input.close();

		//Open appropriate file to read based on gender input
		Scanner file = null;

		if (gender.equals("m")) {
			file = new Scanner (new File("src\\hw4\\namesBoys.txt"));
		}

		else if (gender.equals("f")) {
			file = new Scanner (new File("src\\hw4\\namesGirls.txt"));
		}

		List<String> names = new ArrayList<>();

		//Add names read into an ArrayList
		while (file.hasNext()) {
			names.add(file.next());
		}

		file.close();

		//Pass parameters to create Markov Model
		Markov model = new Markov (names, min, max, order, numberofnames);

		//Generates the Markov Model
		model.generateModel();

		//Generates names
		List<String> generatednames = model.generateNames();

		System.out.println();

		for (String name : generatednames) {
			System.out.println(name);
		}
	}

}
