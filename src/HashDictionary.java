/** Dictionary implemented using hashing. */
class HashDictionary<Key extends Comparable<? super Key>, E> implements Dictionary<Key, E> {
	private static final int defaultSize = 1019;
	private HashTable<Key, E> T; // The hash table
	private static int count; // # of records now in table
	private static int maxsize; // Maximum size of dictionary
	private static int accessCount;

	HashDictionary() {
		this(defaultSize);
	}
	HashDictionary(int sz) {
		T = new HashTable<Key, E>(sz);
		count = 0;
		maxsize = sz;
	}

	public void clear() {
		/** Reinitialize */
		T = new HashTable<Key, E>(maxsize);
		count = 0;
	}

	public void insert(Key k, E e) { /** Insert an element */
		assert count < maxsize : "Hash table is full";
		T.hashInsert(k, e);
		count++;
	}

	public E remove(Key k) { /** Remove an element */
		E temp = T.hashRemove(k);
		if (temp != null)
			count--;
		return temp;
	}

	public E removeAny() { /** Remove some element. */
		if (count != 0) {
			count--;
			return T.hashRemoveAny();
		} else
			return null;
	}

	/** Find a record with key value "k" */
	public E find(Key k) {
		return T.hashSearch(k);
	}

	/** Return number of values in the hash table */
	public int size() {
		return count;
	}
	public static int getMaxSize() {
		return maxsize;
	}
	public static float loadFactor() {
		float currentLoadFactor = (float) count / maxsize;
		return currentLoadFactor;
	}

	public static int getAccessCount() {

		return accessCount;
	}
	public void dumpDictionary() {
		System.out.println("This dictionary contains " + count + " of " + maxsize + " possible records.");

		T.dumpTable();
	}
}