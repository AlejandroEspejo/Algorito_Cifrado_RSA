package rsa;
import java.util.*;
public class RSA {
    static Scanner x=new Scanner(System.in);
    public static void main(String[] args) {
        while(true){
            menu();
            int op = x.nextInt();
            switch(op){
                case 1 -> {
                    generarClaves();
                }
                case 2 -> {
                    System.out.print("Ingrese el mensaje: ");
                    String m = x.next();
                    
                    System.out.print("Ingrese la clave publica: ");
                    long e = x.nextLong();
                    
                    System.out.print("Ingrese el modulo: ");
                    long n = x.nextLong();
                    
                    System.out.print("MENSAJE ENCRIPTADO: ");
                    long[] vector = encriptar(m, e, n);
                    for(int i = 0;i < vector.length;i++)
                        if (vector[i] != 0)
                            System.out.print(vector[i]+"-");
                    System.out.println();
                }
                case 3 -> {
                    System.out.print("Ingrese el mensaje encriptado: ");
                    String m = x.next();
                    
                    System.out.print("Ingrese la clave privada: ");
                    long d = x.nextLong();
                    
                    System.out.print("Ingrese el modulo: ");
                    long n = x.nextLong();
                    
                    System.out.print("MENSAJE Descifrado: ");
                    String mensaje = desencriptar(m, d, n);
                    System.out.println(mensaje);
                }
                case 4 -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("Seleccione una opcion existente");
                }
            }
        }
    }
    
    static long mcd(long a, long b){
        if(b != 0)
            return mcd(b, a % b);
        return a;
    }
    
    static long[] euclides(long a, long b){
        long []vector={b, 0, 1};
        if(a == 0)
            return vector;
        long []vector2 = new long[3]; 
        vector2 = euclides(b % a, a);
        long []vector3={vector2[0], vector2[2] - (b / a) * vector2[1], vector2[1]};
        return vector3;
    }
    
    static void generarClaves(){
        long p, q, n, phi, e, d;
        System.out.println("Ingrese dos valores primos p y q");
        p = x.nextInt();
        q = x.nextInt();
        n = p * q;
        phi = (p - 1) * (q - 1);
        do{
            System.out.println("Ingrese un valor coprimo y menor a "+phi);
            e = x.nextInt();
        }while(!(e < phi && mcd(e, phi) == 1));
        
        long []vector = euclides(e, phi);
        
        d = vector[1];
        while(d < 0){
            d = d + phi;
        }
        System.out.println("Modulo: "+n);
        System.out.println("clave publica: "+e);
        System.out.println("Clave privada: "+d);
    }
    
    static long modpow(long base, long exp, long mod){
        long r = 1;
        while(exp > 0){
            r = r * base;
            r = r % mod;
            exp--;
        }
        return r;
    }
    
    static long [] encriptar(String m, long e, long n){
        long []vector = new long[1000];
        for(int i = 0; i < m.length(); i++){
            vector[i] = modpow((long)m.charAt(i), e, n);
        }
        return vector;
    }
    
    static String desencriptar(String m, long d, long n){
        
        long []vector = new long[1000];
        String temp = "";
        int i;
        for(i = 0; i < m.length(); i++){
            if (m.charAt(i) == '-') {
                vector[i] = Integer.parseInt(temp);
                temp = "";
            } else {
                temp = temp + m.charAt(i);
            }
        }
        if (temp.length() > 0) {
            vector[i] = Integer.parseInt(temp);
        }
        
        String resultado = "";
        for(i = 0; i < vector.length; i++){
            if(vector[i] != 0){
                long numero = modpow(vector[i] , d, n); 
                resultado = resultado + (char) (numero);
            }
        }
        return resultado;
    }
    
    static void menu(){
        System.out.println("1. Generar claves");
        System.out.println("2. Encriptar");
        System.out.println("3. Desencriptar");
        System.out.println("4. Salir");
    }
}
