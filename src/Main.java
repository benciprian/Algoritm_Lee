import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static int citIntreg(String sir){
        try{
            System.out.print(sir);
            Scanner S= new Scanner(System.in);
            int I=S.nextInt();
            return I;
        }
        catch(Exception E){
            System.out.println("Ai gresit, mai incearca");
            return citIntreg(sir);
        }
    }
    public static int [][] citLab(int A[][]){
        int m,n;
        try {
            BufferedReader fisIn =
                    new BufferedReader(new FileReader("c:\\Users\\Cioban\\eclipse-workspace\\AlgoritmulLie\\src\\Labirint.txt"));
            String s;
            s=fisIn.readLine();
            String felii[]=s.split(",");
            m=Integer.parseInt(felii[0]);		//m=numarul de linii
            n=Integer.parseInt(felii[1]);		//n=numarul de coloane
            A=new int[m+1][n+1];
            for(int i=1;i<=m; i++){
                s = fisIn.readLine();
                String lab[]=s.split(",");

                for(int j=1;j<=n;j++){
                    A[i][j]=Integer.parseInt(lab[j-1]);
                }
            }

            fisIn.close();
        } // try
        catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        } // catch
        return A;
    }
    public static void AfisLab(int A[][]){
        System.out.println("Matricea labirintului");
        int m=A.length;
        int n=A[0].length;
        System.out.print(" ");
        for(int j=1; j<n;j++)
            System.out.format("%4d",j);

        System.out.println();
        for(int i=1;i<m; i++)
        { System.out.format("%3d",i);
            for(int j=1; j<n; j++)
                if(A[i][j]==-1)
                    if(i==1 || i==m-1) System.out.print("  - ");
                    else               System.out.print("  | ");
                else
                if(A[i][j]==0) System.out.print("  o ");
                else if(A[i][j]>9)System.out.format("%4d",A[i][j]);
                else         System.out.format("%3d ",A[i][j]);

            System.out.println();
        }
    }
    public static void  Iesire(int A[][]){
        int m=A.length-1;
        int n=A[0].length-1;
        System.out.println("m="+m+" n="+n);
        int i0,j0,iFin,jFin;            //(i0,j0) pornirea iar (iFin,jFin) coordonatele de iesire
        boolean Start;
        do{  Start=true;
            System.out.println("citirea coord initiale");
            i0=citIntreg("linia=");
            j0=citIntreg("coloana=");
            if(A[i0][j0]==-1){			///validam
                System.out.println("locul de start e invalid");
                Start=false;
            }
        }
        while (!Start);

        iFin=i0; jFin=j0;
        A[i0][j0]=1;
        int k=1;
        System.out.println("("+i0+","+j0+")");
        AfisLab(A);
        int c=citIntreg("continua:");
        //4-vecinii lui A[i][j]=k se pun pe k+1 (4-vecinii egali cu 0)
        //se vor crea siruri strict crescatoare spre iesire
        //iesirea este data de expresia
        while (!((iFin<=1) || (iFin>=m) || (jFin<=1) || (jFin>=n))) {//test de iesire
            for (int i=1;i<=m;i++)					//parcurgerea matricii
                for (int j=1;j<=n; j++)
                    if(A[i][j]==k){
                        if(A[i-1][j]==0) {              //pentru 4-vecinul de sus
                            A[i-1][j]=k+1;
                            if(i==2) {iFin=1; jFin=j;}; //stabilirea iesirii pe linia de sus
                        }
                        if(A[i+1][j]==0) {              //pentru 4-vecinul de jos
                            A[i+1][j]=k+1;
                            if(i==m-1) {iFin=m; jFin=j;}//stabilirea iesirii pe linia de jos
                        }
                        if(A[i][j-1]==0) {              //pentru 4-vecinul din stanga
                            A[i][j-1]=k+1;
                            if(j==2)  {jFin=1; iFin=i;} //stabilirea iesirii pe coloana din stanga
                        }
                        if(A[i][j+1]==0) {              //pentru 4-vecinul de dreapta
                            A[i][j+1]=k+1;
                            if(j==n-1) {jFin=n;iFin=i;} //stabilirea iesirii pe coloana din dreapta
                        }
                    }
            k++;
            AfisLab(A);
            c=citIntreg("continua:");
        }
        System.out.println("("+iFin+","+jFin+")"); //afisarea coordonatelor de iesire
        int SirI[]=new int[k+1];
        int SirJ[]=new int[k+1];
        System.out.println("k="+k);
        int lung=k;
        int i=iFin,j=jFin;
        SirI[k]=iFin;           //in sirul de cupluri se pune dinspre coada spre inceput,deci de la k la 1
        SirJ[k]=jFin;           //k se foloseste ca indice in Sir
        while(i!=i0 || j!=j0){
            if(A[i][j]==k)
                if (A[i-1][j]==k-1) {  //unul din 4-vecini care are valoare k-1 este urmatorul
                    k--;                 //element in sir
                    SirI[k]=--i;
                    SirJ[k]=j;
                }
                else
                if(A[i+1][j]==k-1) {
                    k--;
                    SirI[k]=++i;
                    SirJ[k]=j;
                }
                else
                if(A[i][j-1]==k-1){
                    k--;
                    SirI[k]=i;
                    SirJ[k]=--j;
                }
                else
                if(A[i][j+1]==k-1){
                    k--;
                    SirI[k]=i;
                    SirJ[k]=++j;
                }
        }
        System.out.print("sirul pozitiilor:");
        for (int l=1;l<=lung;l++)                  ///afisarea vectorilor de pozitii
            System.out.print("("+SirI[l]+","+SirJ[l]+"),");
    }
    public static void main(String[] args) {
        int A[][]=null;
        A=citLab (A);
        AfisLab(A);
        Iesire (A);
        System.out.println("\nProgram terminated");
    }
}
