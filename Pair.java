// (c) 2020 Nathan Thimothe

public class Pair<K, V>{
    private K key;
    private V value;
    /**
     * Constructs a new empty pair.
     */
    public Pair(){
	this.key = null;
	this.value = null;
    }

    /**
     * Constructs a new pair from two K, V objects
     * @param key key of type K to be the key of a pair
     * @param value of type V to be t value of a pair
     */
    public Pair(K key, V value){
	this.key = key;
	this.value = value;
    }

    /**
     * Gets the key of a pair.
     * @return key of type K in Pair
     */
    public K getKey(){
	return this.key;
    }

    /**
     * Gets the value of a pair.
     * @return value of type V in Pair
     */
    public V getValue(){
	return this.value;
    }

    /**
     * Sets the key of a pair.
     * @param key key to be set for this pair instance
     */
    public void setKey(K key){
	this.key = key;
    }

    /**
     * Sets the value of a pair
     * @param value value to be set for this pair instance
     */
    public void setValue(V value){
	this.value = value;
    }
    
    /**
     * Provides a String represnetation of a pair object.
     * @return a string represnetation of a pair.
     */
    @Override
    public String toString(){
	StringBuffer s = new StringBuffer();
	return "Pair(" + this.key + ", " + this.value + ")";
    }

    /**
     * Indicates whether two pairs are equal.
     * @param obj other pair to be compared against current instance
     * @return true iff the keys are equal
     */
    @Override
    public boolean equals(Object obj){
	// if both objects refer to same object, return true
	if (this == obj) return true;

	// if the object is null or the classes are not the same, return false
	if (obj == null || (obj.getClass() != this.getClass()))
	    return false;

	return this.key.equals(((Pair)obj).key);
    }

    /**
     * Standard hashcode function.
     * @return a hash code for a pair
     */
    @Override
    public int hashCode(){
	return this.key.hashCode();
    }


    public static void main(String[] args){
	System.out.println("Testing simple pair class");

	Pair<String, String> a = new Pair<>();
	a.setKey("Nathan");
	a.setValue("Thimothe");

	Pair<String, String> b = new Pair<>("Michael", "Bistoury");

	Pair<String, String> c = a;

	Pair<String, String> d = new Pair<>(a.getKey(), a.getValue());
	Pair<int[], String> e = new Pair<>(new int[]{1,2,3,4}, "YES");
	

	System.out.println(a);
	System.out.println(b);
	System.out.println(c);
	System.out.println(d);
	System.out.println(e);
	int[] nums = e.getKey();
	nums[3] += 100;
	e.getKey()[1]++;
	e.getKey()[2]++;

	for (int i = 0; i < e.getKey().length; i++){
	    System.out.println(e.getKey()[i]);
	}

	System.out.println("are a and b equal? " + (a.equals(b)));
	System.out.println("are a and c equal? " + (a.equals(c)));
	System.out.println("are a and d equal? " + (a.equals(d)));

	
    }
}