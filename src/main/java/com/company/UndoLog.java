package com.company;

import java.util.HashMap;

public class UndoLog {
    public static HashMap<Integer,Entry> txMap;    // key存放事务Id，value指向Entry链表

    public UndoLog(){
        this.txMap = new HashMap<Integer,Entry>();
    }

    public static Boolean register(int txId){
        txMap.put(txId,null);
        return true;
    }

    public Boolean insert(int txId, Entry log){
        // 头插法
        Entry head = txMap.get(txId);
        log.ptr = head;
        txMap.put(txId,log);
        return true;
    }

    public Boolean rollback(int txId){
        // 找到链表的头
        Entry temp = txMap.get(txId);

        // 每一个都callback
        while(temp!=null){
            // 读取undolog种类
            switch (temp.undoType){
                case 1:
                    // 调用一个jedis.SETNX(temp.key,temp.value)
                    System.out.printf("jedis.SETNX(%s,%s)\n",temp.key,temp.value);
                case 2:
                    // 调用jedis.DEL(temp.key)
                    System.out.printf("jedis.DEL(%s)\n",temp.key);
                case 3:
                    // 调用jedis.SET(temp.key,temp.value)
                    System.out.printf("jedis.SET(%s,%s)\n",temp.key,temp.value);
            }
            temp = temp.ptr;
        }

        // 删除txId和对应的链表
        txMap.remove(txId);

        return true;
    }
}

