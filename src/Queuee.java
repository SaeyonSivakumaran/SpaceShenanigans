
class Queuee<E>{
  private Node<E> head;
  private Node<E> tail;
  
  public void enqueue(E item){
    if (head==null) {
      head=new Node<E>(item,null, null);
      tail=head;
      return;
    }
    
    tail.setNext(new Node<E>(item, null, tail));
    tail=tail.getNext();
    return;
  }
  
  public boolean hasItem(){
    if (this.head==null){
      return false;
    }
    return true;
  }
  
  public E dequeue(){
	  if (this.head==null) {
		  return null;
	  }
    Node<E> tempNode=this.head;
    this.head=this.head.getNext();
    if (this.head!=null){
      this.head.setPrev(null);
    }
    return tempNode.getItem();
    
  }
}

// ********************** A Node in the linked list *********
class Node<T> { 
  private T item;
  private Node<T> next;
  private Node<T> prev;
  
  public Node(T item) {
    this.item=item;
    this.next=null;
    this.prev=null;
  }
  
  public Node(T item, Node<T> next, Node<T> prev) {
    this.item=item;
    this.next=next;
    this.prev=prev;
  }
  
  public Node<T> getNext(){
    return this.next;
  }
  
  public void setNext(Node<T> next){
    this.next = next;
  }
  
  public Node<T> getPrev(){
    return this.prev;
  }
  
  public void setPrev(Node<T> prev){
    this.prev=prev;
  }
  
  public T getItem(){
    return this.item;
  }
  
}