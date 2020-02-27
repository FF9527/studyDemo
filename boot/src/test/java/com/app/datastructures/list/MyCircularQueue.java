package com.app.datastructures.list;

/**
 * 循环队列
 * @author:wuqi
 * @date:2020/2/26L
 * @description:com.app.datastructures.list
 * @version:1.0
 */
public class MyCircularQueue {

    private int[] array;
    private int front = 0;
    private int rear;

    /**
     * Initialize your data structure here. Set the size of the queue to be k.
     */
    public MyCircularQueue(int k) {
        this.array = new int[k + 1];
    }

    /**
     * Insert an element into the circular queue. Return true if the operation is successful.
     */
    public boolean enQueue(int value) {
        if (this.isFull()) {
            return false;
        }
        array[rear] = value;
        rear = (rear + 1) % array.length;
        return true;
    }

    /**
     * Delete an element from the circular queue. Return true if the operation is successful.
     */
    public boolean deQueue() {
        if (this.isEmpty()) {
            return false;
        }
        //System.out.println(array[front]);
        front = (front + 1) % array.length;
        return true;
    }

    /**
     * Get the front item from the queue.
     */
    public int Front() {
        if(this.isEmpty()){
            return -1;
        }
        return array[front];
    }

    /**
     * Get the last item from the queue.
     */
    public int Rear() {
        if(this.isEmpty()){
            return -1;
        }
        return array[(rear + array.length - 1) % array.length];
    }

    /**
     * Checks whether the circular queue is empty or not.
     */
    public boolean isEmpty() {
        return front == rear;
    }

    /**
     * Checks whether the circular queue is full or not.
     */
    public boolean isFull() {
        return front == (rear + 1) % array.length;
    }

    public static void main(String[] args) {
        MyCircularQueue queue = new MyCircularQueue(3);
        System.out.println(queue.enQueue(1));
        System.out.println(queue.enQueue(2));
        System.out.println(queue.enQueue(3));
        System.out.println(queue.enQueue(4));
        System.out.println(queue.Rear());
        System.out.println(queue.isFull());
        System.out.println(queue.deQueue());
        System.out.println(queue.enQueue(4));
        System.out.println(queue.Rear());
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
        System.out.println(queue.isEmpty());
    }
}

