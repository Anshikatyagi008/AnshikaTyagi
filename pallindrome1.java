import java.util.Scanner;
public class pallindrome1 {
        public static void main(String[] args) {
        
    
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();

        int i=0,j=str.length()-1;
        boolean flag=true;
        while(i<j){
            if(str.charAt(i)!=str.charAt(j)){
                flag=false;
                break;
            }
            i++;
            j--;
        }
        System.out.println(flag);


        sc.close();

    }
}
/*import java.util.Scanner;
    public class pallindrome{
        public static void main(String[] args) {
            int m,s=0,r,n;
            Scanner sc = new Scanner(System.in);
            n = sc.nextInt();
            m = n;
            while(n>0){
                r= n%10;
                s = (s*10)+r;
                n = n/10;
            }
            if(s==m)
                System.out.println("pallindrome");
            
    
            else
                System.out.println("Not pallindrom");
            sc.close();
            
        }
    } */
