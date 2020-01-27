import java.io.*;
import java.util.Scanner;

class Matrix{
   
   // Makes a new n x n zero Matrix. pre: n>=1
   private class Entry{
      double value;
      int col;
      Entry(double value, int col){
         this.value = value;
         this.col = col;
      }
      public boolean equals(Entry e){
         if(e.col != this.col || e.value != this.value){
            return(false);
         }
         else{
            return(true);
         }
      }
      public String toString(){
         String str = "";
         str += "(" + col + ", " + value+ ")";
         return str;
      }
   }
   // Fields
   private List[] row;
   private int nnz;
   private int size;
  
   // Constructor ////////////////////////
   Matrix(int n){
      int i;
      row = new List[n+1];
      for(i=1;i<n+1;i++){
         List L = new List();
         //Entry entry = new Entry(0,0);
         //L.insertBack(entry);
         row[i] = L;
      }
      nnz = 0;
      size = n;
   }
   
   void changeEntry(int i, int j, double x){
      Entry entry = new Entry(x, j);
      int q; 
      int index;
      boolean replace = false;
      
      if(i > this.size || j > this.size || i < 1 || j < 1){
         throw new RuntimeException("Matrix Error: attempting to change entries with indices outside the matrix size");
      }
       
      if (row[i].isEmpty()){
         if (x != 0){
            row[i].insertBack(entry);
         }
         //System.out.println("Add First");
      }
      else if(!row[i].isEmpty()){
         row[i].moveTo(0);
         Entry listE = (Entry) row[i].getCurrent();
         for(q=0;q<=row[i].getLength();q++){
            //System.out.println("iterate "+i);
            //Replacement-----------------------------
                  //System.out.println("entry.col = "+ entry.col);
                  //System.out.println("listE.col = "+ listE.col);
            if(entry.col==listE.col){ 
               replace = true;
               if(row[i].getIndex() == row[i].getLength()-1){
                  row[i].insertBack(entry);
                  row[i].movePrev();
                  row[i].deleteCurrent();
                  row[i].moveTo(row[i].getLength()-1);
                  //System.out.println("Replace Back");
                  break;
               }
               else if(row[i].getIndex() == 0){
                  row[i].insertFront(entry);
                  row[i].moveNext();
                  row[i].deleteCurrent();
                  row[i].moveTo(0);
                  //System.out.println("Replace Front");
                  break;
               }
               else{
                  index = row[i].getIndex();
                  row[i].insertBeforeCurrent(entry);
                  row[i].moveNext();
                  row[i].deleteCurrent();
                  row[i].moveTo(index);
                  //System.out.println("Replace Current");
                  break;
               }
            }
            //Add to Front-----------------------------
            else if(entry.col < listE.col && row[i].getIndex() == 0){
               row[i].insertFront(entry);
               //System.out.println("Add Front");
               break;
            }
            //Add in Middle-----------------------------
            else if(entry.col < listE.col && row[i].getIndex() > 0){
               row[i].insertBeforeCurrent(entry);
               //System.out.println("Add Middle");
               break;
            } 
            //Add to Back-----------------------------
            else if(entry.col > listE.col){
               if (row[i].getIndex() == row[i].getLength()-1){
                  row[i].insertBack(entry);
                  //System.out.println("Add Back");
                  //this.getMatrix();
                  break;
               }
               //Move to Next Entry----------------------
               else{ 
                  row[i].moveNext();
               }
            }
            listE = (Entry) row[i].getCurrent();
         } 
      } 
      if(x == 0){
         if(row[i].getIndex()>0){
            index = row[i].getIndex();
            row[i].deleteCurrent();
            row[i].moveTo(index-1);
         }else if(row[i].getIndex() == 0 && row[i].getLength() > 1){
            index = 0;
            row[i].deleteCurrent();
            row[i].moveTo(index);
         }if(replace == true){
            nnz = nnz-1;
         }
      }
      else{
         nnz = nnz+1;
      }
   }
      // pre: 1<=i<=getSize(), 1<=j<=getSize() 

   // Access functions /////////////////// 
   int getSize(){
      return(size);
   }  
   
   int getNNZ(){
      return(nnz);
   }  
  
   boolean atEnd(int i){
      if(this.row[i].getIndex()<this.row[i].getLength()-1){
         return(false);
      }else{
         return(true);
      }
   } 

   public boolean equals(Matrix x){
      int i;
      for(i=1;i<=this.size;i++){
         if(x.row[i].equals(this.row[i])){
            continue;
         }
         else{
            return(false);
         }
      } 
      return(true);   
   } // overrides Object's equals() method


   // Manipulation procedures //////////// 
   
   void makeZero(){
      int i;
      for(i=1;i<size+1;i++){
         if(row[i].isEmpty()){
            continue;
         }
         else{
            row[i].makeEmpty();
         }
      }
   }  
  

   Matrix copy(){
      int i, j;
      Matrix M = new Matrix(this.size);
      Entry e;      
      Entry newe = new Entry(0,0);      

      for(i=1;i<size+1;i++){
         if(row[i].getLength() > 0){
            row[i].moveTo(0);
         }
         else{
            continue;
         }
         for(j=0;j<row[i].getLength();j++){
            e = (Entry) row[i].getCurrent();
            newe.col = e.col;
            newe.value = e.value;
            M.changeEntry(i, newe.col, newe.value);
            if(j+1<row[i].getLength()){
                  row[i].moveNext();
            }
         }
      }
      return(M);
   } 
   
   Matrix scalarMult(double x){
      int i = 1;
      int j;
      Matrix M = new Matrix(this.size);
      Entry e;  
      Entry newe = new Entry(0,0);
 
      for(i=1;i<size+1;i++){
         //System.out.println("i = "+i);
         if(row[i].getLength() > 0){
            row[i].moveTo(0);
         }
         else{
            continue;
         }
         for(j=0;j<row[i].getLength();j++){
            e = (Entry) row[i].getCurrent();
            newe.value = x*e.value;
            newe.col = e.col;
            M.changeEntry(i, newe.col, newe.value);
            if(j+1<row[i].getLength()){
                  row[i].moveNext();
            }
         }     
      }
      return(M);
   }

   Matrix add(Matrix M){
      int i = 1;
      int j, k, x,index;
      Matrix Q;
      List L = new List();
      
      Entry e, q;
      Entry newe = new Entry(0,0);

      if(this.size != M.size){
         throw new RuntimeException("Matrix Error: called addition on different sized matrices");
      }
 
      Q = M.copy();
       
      for(i=1;i<this.size+1;i++){
         if(!this.row[i].isEmpty() && !Q.row[i].isEmpty()){
            this.row[i].moveTo(0);
            Q.row[i].moveTo(0);
         }
         else if(!this.row[i].isEmpty() && Q.row[i].isEmpty()){
            L = this.row[i].copy();
            Q.row[i] = L;
            continue;
         }
         else if(this.row[i].isEmpty()){
            continue;
         }
         for(x=1;x<=this.row[i].getLength();x++){
            //System.out.println("row = "+ i);
            e = (Entry) this.row[i].getCurrent();
           
            while(true){
               q = (Entry) Q.row[i].getCurrent();
               //System.out.println("e.col = "+e.col+", q.col = "+q.col);
               if(e.col < q.col){
                  //System.out.println("e<q");
                  newe.col = e.col;
                  newe.value = e.value;
                   //  System.out.println(newe);
                  Q.changeEntry(i, newe.col, newe.value);
                  break;
               }
               if(e.col == q.col){
                  //System.out.println("e+q");
                  q.value = e.value+q.value;
                  q.col = e.col;
                     //System.out.println(q);
                     Q.changeEntry(i, q.col, q.value);
                  if(Q.row[i].getIndex()<Q.row[i].getLength()-1){
                     Q.row[i].moveNext();
                  }   
                  break;
               }
               if(e.col > q.col){
                  if (Q.row[i].getIndex()==Q.row[i].getLength()-1){
                     //System.out.println("e>q");
                     newe.col = e.col;
                     newe.value = e.value;
                     //System.out.println(newe);
                     Q.changeEntry(i, newe.col, newe.value);
                     break;
                  }
                  else{
                     Q.row[i].moveNext();
                     continue;
                  }   
               }
            }
            if(x == this.row[i].getLength()){
               break;
            }else{
               this.row[i].moveNext();
            }
         }
      }
      return(Q);
   }
    

   Matrix sub(Matrix M){
      int i = 1;
      int j, k, x,index;
      Matrix Q = new Matrix(M.size);
      
      Entry m, q, l;
      Entry newe = new Entry(0,0);
      List L = new List();

      Q = this.copy();
      
      if(this.size != M.size){
         throw new RuntimeException("Matrix Error: called subtraction on different sized matrices");
      }
      if(this.equals(M)){
         Q.makeZero();
         //System.out.println("make zero");
         return(Q);
      }
      else{
      for(i=1;i<this.size+1;i++){
         if(!M.row[i].isEmpty() && !Q.row[i].isEmpty()){
            M.row[i].moveTo(0);
            Q.row[i].moveTo(0);
         }
         else if(!M.row[i].isEmpty() && Q.row[i].isEmpty()){
            L = M.row[i].copy();
            L.moveTo(0);
            for(x=0;x<L.getLength()-1;x++){
               l = (Entry) L.getCurrent();
               l.value = -1*l.value;
               if(x<L.getLength()){
                  L.moveNext();
               }
            }
            Q.row[i] = L;
            continue;
         }
         else if(M.row[i].isEmpty()){
            continue;
         } 
         for(x=1;x<=M.row[i].getLength();x++){
            //System.out.println("row = "+ i);
            m = (Entry) M.row[i].getCurrent();
           
            while(true){
               q = (Entry) Q.row[i].getCurrent();
               //System.out.println("m.col = "+m.col+", q.col = "+q.col);
               if(m.col < q.col){
                  //System.out.println("m<q");
                  newe.col = m.col;
                  newe.value = 0-m.value;
                  Q.changeEntry(i, newe.col, newe.value);
                  break;
               }
               else if(m.col == q.col){
                  //System.out.println("q-m");
                  q.value = q.value-m.value;
                  index = Q.row[i].getIndex();
                  Q.changeEntry(i, q.col, q.value);
                  if(!Q.atEnd(i) && !Q.row[i].isEmpty()){
                     Q.row[i].moveNext();
                  }   
                  break;
               }
               else if(m.col > q.col){
                  if (Q.row[i].getIndex()==Q.row[i].getLength()-1){
                     //System.out.println("m>q");
                     newe.col = m.col;
                     newe.value = 0-m.value;
                     //Q.row[i].insertBack(newe);
                     Q.changeEntry(i, newe.col, newe.value);
                     break;
                  }
                  else{
                     Q.row[i].moveNext();
                     continue;
                  }   
               }
            }
            if(x == M.row[i].getLength()){
               break;
            }else{
               M.row[i].moveNext();
            }
         }
      }
      }
      return(Q);
   }
      // pre: getSize()==M.getSize() 

   Matrix transpose(){
      int i, j;
      Matrix M = new Matrix(this.size);
      Entry e;      
      Entry newe = new Entry(0,0);      

      for(i=1;i<size+1;i++){
         if(row[i].getLength() > 0){
            row[i].moveTo(0);
         }
         else{
            continue;
         }
         for(j=0;j<row[i].getLength();j++){
            //System.out.println("add");
            e = (Entry) row[i].getCurrent();
            newe.col = e.col;
            newe.value = e.value;
            M.changeEntry(newe.col, i, newe.value);
            if(j < row[i].getLength()-1){
                  row[i].moveNext();
            }
         }
      }
      return(M);
   }// returns a new Matrix having the same entries as this Matrix 
   
   Matrix mult(Matrix Q){
      int i = 1;
      int columncount;
      double temp = 0, temp1 = 0;
      boolean first = true;
      Matrix M = new Matrix(this.size);
      Matrix O = new Matrix(this.size);
      M = Q.copy();
      
      Entry e, m;
      Entry newe = new Entry(0,0);
      
      if(this.size != M.size){
         throw new RuntimeException("Matrix Error: called multiplication on different sized matrices");
      }
      for(i=1;i<=this.size;i++){
         columncount = 1;
         while(columncount<=this.size){
            if(!this.row[i].isEmpty()){
               this.row[i].moveTo(0);
            }
            else if(this.row[i].isEmpty()){
               break;
            }
            while(true){
               e = (Entry) this.row[i].getCurrent();
               if(!M.row[e.col].isEmpty()){
                  if(first){
                     M.row[e.col].moveTo(0);
                  }
                  m = (Entry) M.row[e.col].getCurrent();
                  //System.out.println("m.value "+m.value);
                  //System.out.println("e.index "+this.row[i].getIndex());
                  //System.out.println("e.col "+e.col);
                  if(m.col == columncount){
                     //System.out.println("m.col = columncount");
                     temp = e.value*m.value;
                     temp1 = temp + temp1;
                     if(this.row[i].getIndex() < this.row[i].getLength()-1){
                        //System.out.println("moveNext()");
                        this.row[i].moveNext();
                        first = true;
                        continue;
                     }else{
                        //System.out.println("break");
                        break;
                     }//already have elements in columns
                  }
                  else if(m.col < columncount){
                     if(M.row[e.col].getIndex() < M.row[e.col].getLength()-1){
                        //System.out.println("m.col < columncount");
                        M.row[e.col].moveNext();
                        first = false;
                        continue;
                     }
                     else{
                       // System.out.println("break");
                        break;
                     }
                  }
                  else if(m.col > columncount){
                     //System.out.println("m.col > columncount");
                     if(this.row[i].getIndex() < this.row[i].getLength()-1){
                        this.row[i].moveNext();
                        first = true;
                        continue;  
                     }
                     else{
                        //System.out.println("break");
                        break;
                     }
                  }
               }
               else{
                  if(this.row[i].getIndex() < this.row[i].getLength()-1){
                     this.row[i].moveNext();
                     first = true; 
                     continue;  
                  }
                  else{
                     break;
                  }
                  
               }
            }
            newe.value = temp1; 
            if(newe.value > 0){
               //System.out.println("newe = "+newe.value+" "+newe.col);
               newe.col = columncount;
               O.changeEntry(i, columncount, newe.value);
            }
            temp1 = 0;       
            columncount = columncount+1;
            first = true;
         }
      }
      return(O);
   }


   // Other functions //////////////////// 
   public String toString(){ // overrides Object's toString() method
      String str = "";
      int i;
      for(i=1;i<=this.size;i++){
         if(this.row[i].isEmpty()){
            continue;
         }
         else{
            str += i + ":" + this.row[i].toString() + "\n";
         }
      }
      return str;
   }
}
