import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class TTTDictionary implements TTTDictionaryADT {

    // Actually hashtable setup for separate chaining
    private ArrayList<LinkedList<TTTRecord>> hashTable;

    // Binary rotate
    private int rightRotate(int val, int dist) {
        return (val >> dist) | (val << (Integer.SIZE - dist));
    }

    private int hash(String sequence) {
        int hash = 0;

        // Add each char into a rotating 32 bit integer
        for(char c : sequence.toCharArray()) {
            hash = rightRotate(hash * 21, 5) + c * 3;
        }

        // Ensure value is positive and less than the hash table length
        return Math.abs(hash) % hashTable.size();
    }

    public TTTDictionary(int size) {
        hashTable = new ArrayList<>(Collections.nCopies(size, null));
    }

    public int put(TTTRecord newRecord) throws DuplicatedKeyException {
        int bucket = hash(newRecord.getConfiguration());
        if(hashTable.get(bucket) == null) {
            LinkedList<TTTRecord> recordList = new LinkedList<>();
            recordList.add(newRecord);
            hashTable.set(bucket, recordList);
            return 0;
        } else {
            for(TTTRecord record : hashTable.get(bucket)) {
                if(record.getConfiguration().equals(newRecord.getConfiguration())) {
                    throw new DuplicatedKeyException();
                }
            }
            hashTable.get(bucket).add(newRecord);
            return 1;
        }
    }

    public void remove(String config) throws InexistentKeyException {
        int bucket = hash(config);
        if(hashTable.get(bucket) != null) {
            for(TTTRecord record : hashTable.get(bucket)) {
                if(record.getConfiguration().equals(config)) {
                    hashTable.get(bucket).remove(record);
                    return;
                }
            }
        }
        throw new InexistentKeyException();
    }

    public TTTRecord get(String config) {
        int bucket = hash(config);
        if(hashTable.get(bucket) != null) {
            for(TTTRecord record : hashTable.get(bucket)) {
                if(record.getConfiguration().equals(config)) {
                    return record;
                }
            }
        }
        return null;
    }

    public int numElements() {
        int numElements = 0;
        for(LinkedList<TTTRecord> recordList : hashTable) {
            if(recordList != null) {
                numElements += recordList.size();
            }
        }
        return numElements;
    }

}
