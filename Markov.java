import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Markov {

	List<String> names;
	int min;
	int max;
	int order;
	int numberofnames;

	//Hashtable will store a key of length n (order), and an object Key which records occurrences of key and List of letters that follow
	//and their probabilities
	Hashtable<String, Key> probabilities = new Hashtable<>();

	Markov (List<String> names, int min, int max, int order, int numberofnames) {
		this.names = names;
		this.min = min;
		this.max = max;
		this.order = order;
		this.numberofnames = numberofnames;
	}

	//Generates the Markov Model
	public void generateModel () {

		for (int i = 0; i < names.size(); i++) {

			String name = names.get(i);

			//Adds underscores to front and end of name based on Markov order
			for (int j = 0; j < order; j++) {
				name = "_" + name + "_";
			}

			//Iterates over the name starting at first letter after an underscore
			for (int k = order; k < name.length(); k++) {

				//Grabs the letter at position k, and the 2 letters before it which will be the key
				String letter = name.substring(k, k + 1).toLowerCase();
				String key = name.substring(k - order, k).toLowerCase();

				//Checks if key already exists in Hashtable
				if (probabilities.containsKey(key)) {

					//Grabs Key object to update occurrences
					Key tempkey = probabilities.get(key);
					tempkey.updateCount();

					//Grabs ArrayList of Letters that follow the key
					List<Probability> tempprobs = tempkey.getProbabilities();
					//Creates temporary Probability object using the letter in order to use "contains"
					Probability temp = new Probability(letter);

					//If letter already exists, meaning it has been recorded to follow the key
					if (tempprobs.contains(temp)) {
						//Grabs the Probability object of the letter and updates its occurrences
						Probability tempprob = tempprobs.get(tempprobs.indexOf(temp));
						tempprob.updateCount();
					}
					//If letter does not exist, add it to the ArrayList of letters that follow the key
					else {
						Probability newprob = new Probability (letter);
						tempprobs.add(newprob);
					}
				}
				//If key does not exist in Hashtable, create it with key and letter and add to Hashtable
				else {
					Key tempkey = new Key(key);
					Probability newprob = new Probability(letter);
					tempkey.getProbabilities().add(newprob);
					probabilities.put(key, tempkey);
				}
			}

		}

		//Calculating Probabilities
		//Get Key objects from Hashtable
		for (Map.Entry<String, Key> entry : probabilities.entrySet()) {
			Key tempkey = entry.getValue();

			//Occurrences of the key
			int keycount = tempkey.getCount();

			//ArrayList of all the letters that follow the key
			List<Probability> tempprobs = tempkey.getProbabilities();

			//Get occurrences of each letter and divide by occurrences of key for probability
			for (Probability tempprob : tempprobs) {
				double prob = (double)tempprob.getCount() / keycount;
				tempprob.setProbability(prob);
			}

		}

	}

	//Generates the names
	public List<String> generateNames () {

		List<String> generatednames = new ArrayList<>();
		int count = 0;

		while (count < numberofnames) {

			String name = "";

			//Start new name with underscore(s) based on Markov order
			for (int i = 0; i < order; i++) {
				name += "_" ;
			}

			//Loop to construct a name until an underscore is added
			do {
				//Begin constructing name. Initial key is underscore(s), moves along one space each iteration.
				String key = name.substring(name.length() - order);
				Key tempkey = probabilities.get(key);

				//Get Probabilities (letters) that follow the key
				List<Probability> tempprobs = tempkey.getProbabilities();

				//Sorts the ArrayList of letters a-z
				Collections.sort(tempprobs);

				//Random number between 0 and 1
				double random = Math.random();

				//Checks probability of each letter that follows key against the random number
				for (Probability tempprob : tempprobs) {
					if (random < tempprob.getProbability()) {
						name += tempprob.letter;
						break;
					}
					else {
						random = random -  tempprob.getProbability();
					}
				}
			}

			while (! name.substring(name.length() - 1).equals("_") );

			//Gets rid of underscores, and Capitalizes 1st letter
			name = name.substring(order, name.length() - 1);
			String upper = name.substring(0,1).toUpperCase();
			name = upper + name.substring(1);

			//If name is unique in regards to original list of names and generated names
			//and also meets length requirements, then add it to list of generated names
			if (!names.contains(name) && !generatednames.contains(name)) {
				if (name.length() >= min && name.length() <= max) {
					generatednames.add(name);
					count++;
				}
			}
		}

		return generatednames;

	}
}
