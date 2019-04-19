import java.util.ArrayList;
import java.util.List;

//Holds the Key, its occurrences, and the Letters that follow in an ArrayList.
public class Key {

	String key;
	int count = 0;

	//Letters that follow the key and their probabilities are stored here as Probability
	List<Probability> probabilities = new ArrayList<>();

	Key (String key) {
		this.key = key;
		this.count++;
	}

	//Updates occurrences of key
	public void updateCount () {
		this.count++;
	}

	//Gets the count
	public int getCount () {
		return this.count;
	}

	//Gets the list of Letters that follow the key and their probabilities
	public List<Probability> getProbabilities () {
		return probabilities;
	}

	@Override
	public boolean equals (Object o) {

		if (!(o instanceof Key)) {
			return false;
		}

		Key s = (Key) o;


		return s.key.equals(key);

	}

	@Override
	public int hashCode() {

		int result = 17;

		result = 37*result + key.hashCode();


		return result;
	}
}
