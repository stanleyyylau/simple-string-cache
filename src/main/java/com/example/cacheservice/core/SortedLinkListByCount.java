package com.example.cacheservice.core;

public class SortedLinkListByCount {

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

    public SortedLinkListByCount() {
        dummyHead = new Node();
        size = 0;
    }

    // always add to head
    public synchronized void addToHead(CacheEntity e) {
        Node newNode = new Node();
        newNode.e = e;
        newNode.next = dummyHead.next;

        dummyHead.next = newNode;
        size++;
    }

    public synchronized void updateByKey(String key, Integer newCount) {
        Node pre = dummyHead;
        Node target;
        for (int i = 0; i < size; i++) {
            if (key.equals(pre.next.e.getKey())) {
                target = pre.next;

                // pre and next is the same count
                target.e.setCount(newCount);
                if (target.next != null && target.next.e.getCount().equals(newCount)) {
                    break;
                }

                while (target.next != null && newCount > target.next.e.getCount()) {
                    // move target down the list by one node
                    pre.next = target.next;
                    target.next = pre.next.next;
                    pre.next.next = target;
                }
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

    public String getHeadKey() {
        Node target = dummyHead.next;
        if (target != null) {
            return target.e.getKey();
        }
        return null;
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
        SortedLinkListByCount sortedLinkList = new SortedLinkListByCount();
        sortedLinkList.addToHead(new CacheEntity("key1", 0));
        sortedLinkList.addToHead(new CacheEntity("key2", 0));
        sortedLinkList.addToHead(new CacheEntity("key3", 0));
        sortedLinkList.addToHead(new CacheEntity("key4", 0));
        sortedLinkList.addToHead(new CacheEntity("key5", 0));
        System.out.println(sortedLinkList);
        sortedLinkList.updateByKey("key2", 1);
        System.out.println(sortedLinkList);
        sortedLinkList.updateByKey("key3", 1);
        System.out.println(sortedLinkList);
        sortedLinkList.updateByKey("key3", 3);
        System.out.println(sortedLinkList);

        sortedLinkList.removeByKey("key2");

        System.out.println(sortedLinkList);
    }
}
