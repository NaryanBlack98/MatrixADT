import java.io.*;
import java.util.Scanner;

class Sparse{
   public static void main(String[] args) throws IOException{
      Scanner in = null;
      PrintWriter out = null;
      String line = null;
      String[] token = null;
      int i, j, n, lineNumber = 0;
      int order = 0;
      int isthird = 1;
      int row = 1;
      int column = 1;  
      int msize = 0;
      int size = 0;
      int tokenInt; 
      double tokenDouble = 0;
      boolean isTrue = true;

      if(args.length < 2){
         System.out.println("Usage: FileIO infile outfile");
         System.exit(1);
      }

      in = new Scanner(new File(args[0]));
      out = new PrintWriter(new FileWriter(args[1]));

      //Main-loop that goes a line at a time 
      line = in.nextLine()+" ";    // add extra space so split works right
      token = line.split("\\s+");  // split line around white space
      n = token.length;
      msize = Integer.parseInt(token[1]);
      size = Integer.parseInt(token[0]);
      line = in.nextLine()+" ";    // add extra space so split works right
 
      Matrix A = new Matrix(size);
      Matrix B = new Matrix(size);
      Matrix C = new Matrix(size);

      while(in.hasNextLine()){
            line = in.nextLine()+" ";    // add extra space so split works right
            token = line.split("\\s+");  // split line around white space
            n = token.length;
            for(i=0; i<n; i++){
               if(isthird == 3){
                  tokenDouble = Double.parseDouble(token[i]);
                  if(msize >= 0){
                     A.changeEntry(row, column, tokenDouble);
                     //System.out.println(A.getNNZ());
                  }
                  else{
                     B.changeEntry(row, column, tokenDouble);
                     //System.out.println(B.getNNZ());
                     //System.out.println(tokenDouble);
                  }
                  isthird = 1;
               }
               else if(isthird == 2){
                  tokenInt = Integer.parseInt(token[i]);
                  column = tokenInt;
                  isthird++;
               }
               else if(isthird == 1){
                  tokenInt = Integer.parseInt(token[i]);
                  row = tokenInt;
                  isthird++;
               }
            }
         msize = msize-1;
      }
      //Print A
      out.println("A has "+A.getNNZ()+" non-zero entries:");
      out.println(A);      
      //Print B
      out.println("B has "+B.getNNZ()+" non-zero entries:");
      out.println(B);      
      //Print 1.5*A
      out.println("(1.5)*A = ");
      C = A.scalarMult(1.5);
      out.println(C);      
      //Print A+B
      out.println("A+B =");
      C = A.add(B);
      out.println(C);      
      //Print A+A
      out.println("A+A =");
      C = A.add(A);
      out.println(C);      
      //Print B-A
      out.println("B-A =");
      C = B.sub(A);
      out.println(C);      
      //Print A-A
      out.println("A-A =");
      C = A.sub(A);
      out.println(C);      
      //Print Transpose(A)
      out.println("Transpose(A) =");
      C = A.transpose();
      out.println(C);      
      //Print A*B
      out.println("A*B =");
      C = A.mult(B);
      out.println(C);      
      //Print B*B
      out.println("B*B =");
      C = B.mult(B);
      out.println(C);      
      
      in.close();
      out.close();
   }
}
