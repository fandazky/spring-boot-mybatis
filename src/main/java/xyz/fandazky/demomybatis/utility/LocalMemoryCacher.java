package xyz.fandazky.demomybatis.utility;

import java.util.*;

public class LocalMemoryCacher<K, T> {
    private long timeToLive;
    private HashMap<K, T> cacheMap;

    public LocalMemoryCacher(long timeToLive) {
        this.timeToLive = timeToLive * 1000;

        cacheMap = new HashMap<K, T>();

//        if (timeToLive > 0 && timeInterval > 0) {
//
//            Thread t = new Thread(new Runnable() {
//                public void run() {
//                    while (true) {
//                        try {
//                            Thread.sleep(timeInterval * 1000);
//                        } catch (InterruptedException ex) {
//                        }
//
//                    }
//                }
//            });
//
//            t.setDaemon(true);
//            t.start();
//        }
    }

    // PUT method
    public void put(K key, T value) {
        synchronized (cacheMap) {
            CachedObject c = new CachedObject((String) value);
            cacheMap.put(key, (T) c);
        }
    }

    // GET method
    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (cacheMap) {
            CachedObject c = (CachedObject) cacheMap.get(key);

            if (c == null)
                return null;
            else {
                if (c.lastAccessed < System.currentTimeMillis() - timeToLive) {
                    this.remove(key);
                    return null;
                }
                return (T) c.value;
            }
        }
    }

    // REMOVE method
    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    // Get Cache Objects Size()
    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    // CLEANUP method
    public void cleanup() {

        long now = System.currentTimeMillis();
        List<String> deleteKey = null;

        synchronized (cacheMap) {
            Iterator<?> itr = cacheMap.entrySet().iterator();

            deleteKey = new ArrayList<String>((cacheMap.size() / 2) + 1);
            CachedObject c = null;

            while (itr.hasNext()) {
                String key = (String) itr.next();
                c = (CachedObject) ((Map.Entry<?, ?>) itr).getValue();
                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (String key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}