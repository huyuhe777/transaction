package com.company;

public class Entry {
    public
    int undoType;         // 类型：1-insert,2-delete,3-update
    int txId;             // 事务id
    Entry ptr;            // 指向下一条entry
    String key;           // Redis中存储的key，需要进一步抽象
    String value;         // Redis中存储的value，需要进一步抽象

    Entry(int type, int txId, Entry ptr,String key, String value){
        if (type == 1) {
            this.undoType = 2;
        } else if (type == 2) {
            this.undoType = 1;
        } else {
            this.undoType = 3;
        }
        this.txId =txId;
        this.ptr = ptr;
        this.key = key;
        this.value = value;
    }

}
