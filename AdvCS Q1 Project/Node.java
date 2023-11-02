public class Node<E> {
    
    private E data;
    private Node<E> next;
    private Node<E> prev;

    public Node(E el){
        data = el;
    }

    public E get(){
        return data;
    }
    public Node<E> next(){
        return next;
    }
    public void setNext(Node<E> newNext){
        next = newNext;
    }
    public Node<E> prev(){
        return prev;
    }
    public void setPrev(Node<E> newPrev){
        prev = newPrev;
    }
    public void setData(E newData){
        data = newData;
    }
    @Override
    public boolean equals(Object obj){
        return data.equals(obj);
    }
}
