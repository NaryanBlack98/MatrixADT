class List{

   private class Node{
      // Fields
      Object data;
      Node next;
      Node previous;
      // Constructor
      Node(Object data) { 
         this.data = data; 
         next = null; 
         previous = null;
      }
      // toString:  Overides Object's toString method.
      public String toString() { return String.valueOf(data); }
   }

   // Fields
   private Node front;
   private Node back;
   private Node current;
   private int index;
   private int length;

   // Constructor
   List() { front = null; back = null; current =  null; index = -1; length = 0; }


   // Access Functions //////////////////////////////////////////////////////////////////

   // getFront(): returns front element in this queue
   // Pre: !this.isEmpty()
   Object getFront(){
      if( this.isEmpty() ){
         throw new RuntimeException("List Error: getFront() called on empty List");
      }
      
      current = front;
      current.next = front.next;
      current.previous = null;
      index = 0;
      return front.data;
   }

   // getLength(): returns length of this queue
   int getLength() { return length; }

   // isEmpty(): returns true if this is an empty queue, false otherwise
   boolean isEmpty() { return length==0; }

   // offEnd: returns true if current is undefined
   boolean offEnd() {
      boolean flag = true; 
      if(current == null){
         return flag;  
      }
      else{
        return false; 
      }
   }  
   // getIndex(): If current is defined, returns its position in 
               //this List, ranging from 0 to getLength()-1 inclusive.
               //If current element is undefined, returns -1.
   int getIndex(){
         return index;
   } 
   // getBack(): returns true if this is an empty queue, false otherwise
   Object getBack(){
      if(this.isEmpty()){
         throw new RuntimeException("List Error: getBack() called on empty List");
      }
      current = back;
      current.next = null;
      current.previous = back.previous;
      index = length-1;
      return back.data;
   }    
   // getCurrent(): returns current element
   Object getCurrent(){
      if(this.isEmpty()){
         throw new RuntimeException("List Error: getCurrent() called on empty List");
      }
      if(this.offEnd()){
         throw new RuntimeException("List Error: getCurrent() called on undefined current");
      }
      return current.data;
   }  

   // Manipulation Procedures ///////////////////////////////////////////////////////////

   // makeEmpty()
   void makeEmpty(){
      front.next = front.previous = null;
      front = null;
      back.next = back.previous = null;
      back = null;
      current.next = current.previous = null;
      current = null;
      index = -1;
      length = 0;
      //while(this.isEmpty()!=true){
        // System.out.println(this);
        // deleteBack();
      //}
   }

   //moveTo(int i)
   void moveTo(int i){
      if (i == 0){
         current = front;
         //current.next = front.next;
         //current.previous = null;
         index = 0;
      }
      if (index == -1){
         index = 0;
         current = front;
         //current.next = front.next;
         //current.previous = null;
         for(int j = index; j<i; j++){
            moveNext();
         }
      } 
      if(i < index){   
         for(int j = index; j>=i; j--){
            movePrev();
         }
      }
      if(i > index){   
         for(int j = index; j<i; j++){
            moveNext();
         }
      } 
   }

   //movePrev()
   void movePrev(){
      Node tmp = new Node(1);
      if(this.isEmpty()){
         throw new RuntimeException("List Error: movePrev() called on empty List");
      }
      if(this.offEnd()){
         throw new RuntimeException("List Error: movePrev() called on undefined current");
      }
      index--;
      //tmp.next = current.previous.next;
      //tmp.previous = current.previous.previous;
      current = current.previous;
      //current.next = tmp.next;
      //current.previous = tmp.previous; 
   }

   //moveNext()
   void moveNext(){
      Node tmp = new Node(1);
      if(this.isEmpty()){
         throw new RuntimeException("List Error: moveNext() called on empty List");
      }
      if(this.offEnd()){
         throw new RuntimeException("List Error: moveNext() called on undefined current");
      }
      index++;
      //tmp.previous = current.next.previous;
      //tmp.next = current.next.next;
      current = current.next;
     // current.next = tmp.next;
      //current.previous = tmp.previous; 
   }

   //insertBeforeCurrent(int data)
   void insertBeforeCurrent(Object data){
      Node node = new Node(data);
      if(this.isEmpty()){
         throw new RuntimeException("List Error: insertBeforeCurrent() called on empty List");
      }
      if(this.offEnd()){
         throw new RuntimeException("List Error: insertBeforeCurrent() called on undefined current");
      }
      if(current == front){
         insertFront(data);
      } 
      else{
         node.next = current; 
         node.previous = current.previous;
         current.previous.next = node;
         current.previous = node;
         current = node;
         //current.previous = node.previous;
         //current.next = node.next;
         length++;
      }
   }

   //insertAfterCurrent(int data)
   void insertAfterCurrent(Object data){
      Node node = new Node(data);
      if(this.isEmpty()){
         throw new RuntimeException("List Error: insertAfterCurrent() called on empty List");
      }
      if(this.offEnd()){
         throw new RuntimeException("List Error: insertAfterCurrent() called on undefined current");
      }
      if(current == back){
         insertBack(data);
      }
      else{
         node.next = current.next;
         node.previous = current;
         current.next.previous = node;
         current.next = node;
         current = node;
         //current.next = node.next;
         //current.previous = node.previous;
         index++;
         length++;
      }
   } 
   
   //deleteCurrent()
   void deleteCurrent(){
      if(this.isEmpty()){
         throw new RuntimeException("List Error: deleteCurrent() called on empty List");
      }
      if(this.length>1){
         if(this.index == 0){
            //System.out.println("List:deleteFront()");
            deleteFront();
         }
         else if(this.index == this.length-1){
            //System.out.println("List:deleteBack()");
            deleteBack();
         }
         else{ 
            //System.out.println("List:deleteMiddle()");
            current.previous.next = current.next;
            current.next.previous = current.previous;
            length--;
         }
         current.next = null;
         current.previous = null;
         current = null;
         index = -1; 
      }
      else{
         //System.out.println("List:deleteRest()");
         front.next = front.previous = null;
         front = null;
         back.next = back.previous = null;
         back = null;
         current.next = current.previous = null;
         current = null;
         index = -1;
         length--;
      }
   } 

   // insertBack(): appends data to back of this List 
   void insertBack(Object data){
      Node node = new Node(data);
      if( this.isEmpty() ) { 
         front = node;
         back = node;
         current = node; 
         index = 0;
      }
      else {
         current = back;
         back.next = node; 
         back = node;
         back.previous = current;
         current = node; 
         current.previous = back.previous;
         current.next = null; 
      }
      length++;
      index = length-1;
   }

   // insertFront(): appends data to front of this List 
   void insertFront(Object data){
      Node node = new Node(data);
      if( this.isEmpty() ) {
         front = node;
         back = node; 
         current = node; 
         index = 0;
      }
      else {
         current = front; 
         front.previous = node;
         front = node;
         front.next = current;
         current = node; 
         current.next = front.next;
         current.previous = null; 
      }
      length++;
      index = 0;
   }

   // deleteFront(): deletes element from front of this queue
   // Pre: !this.isEmpty()
   void deleteFront(){
      if(this.isEmpty()){
         throw new RuntimeException("List Error: deleteFront() called on empty List");
      }
      if(this.length>1) {
         front = front.next;
         front.previous = null;
         current = front;
         current.previous = null;
         current.next = front.next;
      }
      else{
         front.next = null;
         front.previous = null;
         front = null;
         back.next = null;
         back.previous = null;
         back = null;
         current.next = current.previous = null;
         current = null;
         index = -1;
      }
      length--;
   }

   // deleteBack(): deletes element from back of this queue
   // Pre: !this.isEmpty()
   void deleteBack(){
      if(this.isEmpty()){
         throw new RuntimeException("List Error: deleteBack() called on empty List");
      }
      if(this.length>1) {
         back = back.previous;
         back.next = null;
         current = back;
         current.next = null;
         current.previous = back.previous;
         length--;
         index = length-1;
      }
      else{
         //System.out.println("kill all "+getLength());
         front.next = null;
         front.previous = null;
         front = null;
         back.next = null;
         back.previous = null;
         back = null;
         current.next = current.previous = null;
         current = null;
         index = -1;
         length--;
      }
   }

   // Other Functions ///////////////////////////////////////////////////////////////////

   // toString():  overides Object's toString() method.
   public String toString(){
      String str = "";
      for(Node N=front; N!=null; N=N.next){
         str += N.toString() + " ";
      }
      return str;
   }

   // equals(): returns true if this Queue is identical to  Q, false otherwise.
   boolean equals(List Q){
      boolean flag  = true;
      Node N = this.front;
      Node M = Q.front;

      if( this.length==Q.length ){
         while( flag && N!=null){
            flag = (N.data==M.data);
            N = N.next;
            M = M.next;
         }
         return flag;
      }else{
         return false;
      }
   }

   // copy(): returns a new Queue identical to this one.
   List copy(){
      List Q = new List();
      Node N = this.front;

      while( N!=null ){
         Q.insertBack(N.data);
         N = N.next;
      }
      return Q;
   }

}
