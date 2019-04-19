//Holds the letters that follow a key and their probabilities
public class Probability implements Comparable<Probability>{

	String letter;
	int count = 0;
	double probability = 0;

	Probability (String letter) {
		this.letter = letter;
		this.count++;
	}

	//Updates occurrences of letter
	public void updateCount () {
		this.count++;
	}

	//Gets the count
	public int getCount () {
		return this.count;
	}

	//Updates probability of letter
	public void setProbability (double result) {
		this.probability = result;
	}

	//Gets probability of letter
	public double getProbability () {
		return this.probability;
	}

	@Override
	public boolean equals (Object o) {

		if (!(o instanceof Probability)) {
			return false;
		}

		Probability s = (Probability) o;

		return s.letter.equals(letter);

	}

	@Override
	public int hashCode() {

		int result = 17;

		result = 37*result + letter.hashCode();


		return result;
	}

	@Override
	public int compareTo (Probability other) {
		int result = this.letter.compareTo(other.letter);
		return result;
	}
}
