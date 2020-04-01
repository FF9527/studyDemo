package com.app.alg;

/**
 * @author:wuqi
 * @date:2020/3/20
 * @description:com.app.alg
 * @version:1.0
 */
public class OrderList {

    private class Node {

        private Node next;
        private int data;

        public Node() {

        }

        public Node(Node next, int data) {
            this.next = next;
            this.data = data;
        }
    }

    public Node orderList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = new Node();
        Node tmpNode = null;
        while (head != null) {
            tmpNode = head.next;
            head.next = newHead.next;
            newHead.next = head;
            head = tmpNode;

        }
        return newHead.next;
    }

    public static void main(String[] args) {
        OrderList orderList = new OrderList();
        Node node1 = orderList.new Node(null, 1);
        Node node2 = orderList.new Node(node1, 2);
        Node node3 = orderList.new Node(node2, 3);
        Node node4 = orderList.new Node(node3, 4);
        Node node5 = orderList.new Node(node4, 5);
        Node newHead = orderList.orderList(node5);
        while (newHead != null) {
            System.out.print(newHead.data);
            newHead = newHead.next;
        }
    }
}
