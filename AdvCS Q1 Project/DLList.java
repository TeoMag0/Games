public class DLList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLList(){
        head = new Node<E>(null);
        tail = new Node<E>(null);
        head.setNext(tail);
        head.setPrev(tail);
        tail.setNext(head);
        tail.setPrev(head);

        size = 0;
    }

    public void add(E data){
        Node<E> newNode = new Node<E>(data);

        newNode.setPrev(tail.prev());
        newNode.prev().setNext(newNode);
        tail.setPrev(newNode);
        newNode.setNext(tail);

        size++;
    }

    public E get(int index){
        Node<E> node = findNodeByIndex(index);
        try{
            return node.get();
        }catch(Exception e){return null;}
    }
    
    public void remove(int index){
        Node<E> node = findNodeByIndex(index);
        node.prev().setNext(node.next());
        node.next().setPrev(node.prev());
        size--;
    }

    public void remove(E el){
        Node<E> node = findNodeWithElement(el);
        node.prev().setNext(node.next());
        node.next().setPrev(node.prev());
        size--;
    }

    public DLList<E> clone(){
        DLList<E> clone = new DLList<E>();
        for(int i=0;i<size();i++){
            clone.add(get(i));
        }
        return clone;
    }

    public void clear(){
        while(size >0){
            remove(0);
        }
    }

    public void set(int index, E obj){
        findNodeByIndex(index).setData(obj);
    }

    private Node<E> findNodeByIndex(int ind){
        Node<E> curNode;
        if(ind < size/2){
            curNode = head.next();
            for(int i=0;i<size;i++){
                if(i == ind)
                    return curNode;
                curNode = curNode.next();
            }
        }
        else{
            curNode = tail.prev();
            for(int i=size-1;i>=0;i--){
                if(i == ind)
                    return curNode;
                curNode = curNode.prev();
            }
        }
        return null;
    }

    private Node<E> findNodeWithElement(E el){
        Node<E> curNode = head.next();
        for(int i=0;i<size;i++){
            if(curNode.get().equals(el))
                return curNode;
            curNode = curNode.next();
        }
        return null;
    }

    public int size(){
        return size;
    }

    @Override
    public String toString(){
        String s = "[ ";
        for(int i=0;i<size();i++){
            s += get(i)+", ";
        }
        return s.substring(0, s.length()-2)+" ]";
    }
}
