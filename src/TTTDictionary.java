import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class TTTDictionary implements TTTDictionaryADT {

    private ArrayList<LinkedList<TTTRecord>> hashTable;
    private int hashTableCapacity;

    private int hash(String sequence) {
        if(sequence.length() == 0) {
            return 0;
        }

        int hash = sequence.charAt(0);
        for(int i = 1; i < sequence.length(); i++) {
            hash = (hash >> 7) | (hash << (Integer.SIZE - 7));
            hash = hash ^ (21 * sequence.charAt(i));
        }
        hash %= hashTable.size();
        return Math.abs(hash);
    }

    public TTTDictionary(int size) {
        System.out.println("Creating hash table: " + size);
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
            System.out.println("Collision");
            return 1;
        }
    }

    public void remove(String config) throws InexistentKeyException {
        int bucket = hash(config);
        if(hashTable.get(bucket) != null) {
            for(TTTRecord record : hashTable.get(bucket)) {
                if(record.getConfiguration().equals(config)) {
                    hashTable.get(bucket).remove(record);
                    break;
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
