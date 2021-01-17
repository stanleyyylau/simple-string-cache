package com.example.cacheservice.core;

import java.util.ArrayList;
import java.util.List;

public class SortedLinkListByExpire {

    int size;
    Node dummyHead;

    private class Node {
        public Node next;
        public CacheEntity e;

        public Node(CacheEntity e, Node next) {
            this.next = next;
            this.e = e;
        }

        public Node(CacheEntity e){
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString(){
            if (e == null) {
                return "Dummy Head";
            }
            return e.getKey() + "(" + e.getCount() + ")";
        }
    }

    public SortedLinkListByExpire() {
        dummyHead = new Node();
        size = 0;
    }

    public synchronized void add(CacheEntity e) {
        Node pre = dummyHead;

        if (size == 0) {
            pre.next = new Node(e, null);
            size++;
            return;
        }

        for (int i = 0; i <= size; i++) {
            if (pre.next == null) {
                // insert at the end
                pre.next = new Node(e, null);
                size++;
                break;
            }
            if (pre.next.e.getExpireDate().compareTo(e.getExpireDate()) >= 0) {
                // just insert
                pre.next = new Node(e, pre.next);
                size++;
                break;
            }
            pre = pre.next;
        }
    }

    public synchronized CacheEntity removeByKey(String key) {
        Node pre = dummyHead;
        for (int i = 0; i < size ; i++) {
            if (key.equals(pre.next.e.getKey())) {
                break;
            }
            pre = pre.next;
        }
        Node delNode = pre.next;
        pre.next = delNode.next;
        delNode.next = null;
        size--;
        return delNode.e;
    }

    public List<String> collectExpireKeys() {
        Node pre = dummyHead;
        List<String> expiredKeys = new ArrayList<String>();
        // avoid full scan
        while (pre.next != null && CommonUtil.isDateExpire(pre.next.e.getExpireDate())) {
            expiredKeys.add(pre.next.e.getKey());
            pre = pre.next;
        }
        return expiredKeys;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(size + " : ");
        Node cur = dummyHead.next;
        while(cur != null){
            res.append(cur + "  ->  ");
            cur = cur.next;
        }
        res.append("NULL");

        return res.toString();
    }

    public static void main(String[] args) {
        SortedLinkListByExpire sortedLinkList = new SortedLinkListByExpire();
        sortedLinkList.add(new CacheEntity("key1", 0, CommonUtil.getDateByExpire(140l)));
        sortedLinkList.add(new CacheEntity("key2", 0, CommonUtil.getDateByExpire(500l)));
        sortedLinkList.add(new CacheEntity("key3", 0, CommonUtil.getDateByExpire(200l)));
        sortedLinkList.add(new CacheEntity("key4", 0, CommonUtil.getDateByExpire(300l)));
        sortedLinkList.add(new CacheEntity("key5", 0, CommonUtil.getDateByExpire(100l)));

        System.out.println(sortedLinkList);
    }
}
