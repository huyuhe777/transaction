package com.company;

import org.junit.Test;
import redis.clients.jedis.Jedis;


class UndoLogTest {
    @Test
    public void testJedis() throws Exception{
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 1 new Undolog
        UndoLog undolog = new UndoLog();

        // 2 收到新的transaction-先注册事务
        int txId = 20;
        UndoLog.register(txId);

        // 3 逐步写入undolog
        // 3.1 插入insert undolog
        String key1 = "key1";
        String value1 = "1234";
        Entry log1 = new Entry(1,txId,null,key1,value1);
        undolog.insert(txId,log1);

        // 3.3 插入update
        String value2 = "5678";
        Entry log3 = new Entry(3,txId,null,key1,value2);
        undolog.insert(txId,log3);

        // 3.2 插入delete
        Entry log2 = new Entry(2,txId,null,key1,value1);
        undolog.insert(txId,log2);

        // 4 rollback
        undolog.rollback(txId);
    }

}