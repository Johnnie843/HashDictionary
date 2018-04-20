
public class HashTable<Key extends Comparable<? super Key>, E> {
	// We don't actually care if Key is Comparable, we only do it for consistency
	private int M;
	private KVpair<Key, E>[] T;
	private KVpair<Key, E> TombStone = new KVpair<Key, E>();// Tombstone to mark deleted data
	private static int insertionCount;// count accesses for inserting
	private static int deletionCount;// count accesses for deletion
	private int searchCount;// count accesses for searching
	private static int keyValue;

	/*
	 * (McCauley added) // this sfold method multiplies that ascii values in the string // by powers of 2, by bit-shifting left. // For example, 5 << 3 results is the same as 5 * 8 // So for a 4-char
	 * chunk of the string, say "aa32" // we'd have 97 * 2-to the 24th + 97 * 2-to the 16th, + 51 * // 2-to the 8th, + 50
	 */
	private int sfold(String x, int M) {
		char ch[];
		ch = x.toCharArray();
		int intlength = x.length() / 4;
		long sum = 0;
		int count = 0;
		for (int i = 0; i < intlength; i++) {
			sum += (((long) ch[count]) << 24) + (((long) ch[count + 1]) << 16) + (((long) ch[count + 2]) << 8) + ((long) ch[count + 3]);
			count += 4;
		}
		return (Math.abs((int) sum) % M);
	}

	/**
	 * If the key is an Integer, then use a simple mod function for the hash function. If the key is a String, then use folding.
	 */
	private int h(Key key) {
		Object keyO = (Object) key;
		if (keyO.getClass() == Integer.class)
			return (Integer) keyO % M;
		else if (keyO.getClass() == String.class)
			return sfold((String) keyO, M);
		else
			return key.hashCode() % M;
	}

	/** Implement collision resolution with linear probing */
	private int p(Key key, int slot) {
		return slot;
	}

	/* Constructor */
	@SuppressWarnings("unchecked") // Allow the generic array allocation
	HashTable(int m) {
		M = m;
		T = (KVpair<Key, E>[]) new KVpair[M];
	}

	/** Insert record r into T */
	// Bugged for now -- this skips tombstones
	public void hashInsert(Key k, E r) {
		insertionCount = 0;// set insertion count back to 0
		int home; // Home position for r
		int pos = home = h(k); // Initial position
		insertionCount++; // for the first key comparison
		for (int i = 1; T[pos] != null && T[pos] != TombStone; i++) {
			assert T[pos].key().compareTo(k) != 0 : "Duplicates not allowed";
			pos = (home + p(k, i)) % M; // Next probe slot
			insertionCount++;// access count
		}
		T[pos] = new KVpair<Key, E>(k, r); // Insert R
	}

	/** Search for the record with key k */
	E hashSearch(Key k) {
		int home; // Home position for k
		int pos = home = h(k); // Initial position
		for (int i = 1; (T[pos] != null) && (T[pos] == TombStone || T[pos].key().compareTo(k) != 0); i++) {
			pos = (home + p(k, i)) % M;// Next probe position
			searchCount++;// access count
		}
		if (T[pos] == null)
			return null; // K not in hash table
		else
			return T[pos].value(); // Found it
	}

	/** Remove a record with key value k from the hash table */
	E hashRemove(Key k) {
		int home; // Home position for k
		int pos = home = h(k); // Initial position
		for (int i = 1; (T[pos] != null) && (T[pos] == TombStone || T[pos].key().compareTo(k) != 0); i++) {
			pos = (home + p(k, i)) % M; // Next probe position
			deletionCount++;// access count
		}
		if (T[pos] == null)
			return null; // K not in hash table
		else { // Found it
			E e = T[pos].value();
			T[pos] = TombStone;
			return e;
		}
	}

	/** Remove some record from the hash table */
	E hashRemoveAny() {
		int i;
		for (i = 0; i < M; i++)
			if (T[i] != null && T[i] != TombStone)
				break;
		E e = T[i].value();
		T[i] = TombStone;
		return e;
	}

	// dump HT to see what is happening
	public void dumpTable() {
		System.out.println("The HT contains:");
		for (int i = 0; i < M; i++)
			if (T[i] != null)
				System.out.println(T[i].key() + ", " + T[i].value());
			else
				System.out.println(T[i]);
	}

	public void insert(int i, int j) {
		// TODO Auto-generated method stub

	}

	public static int getInsertionCount() {
		return insertionCount;
	}

	public static int getDeletionCount() {
		return deletionCount;
	}
	public int getSearchCount() {
		return searchCount;
	}

}