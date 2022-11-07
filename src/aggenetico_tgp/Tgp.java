/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aggenetico_tgp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Alexandre Marinho
 */
public class Tgp {
    private int nVertice;
    private int dist;

    public Tgp(int nVertice) {
        this.nVertice = nVertice;
    }

   public Tgp(){
       
   }
    

    public int getnVertice() {
        return nVertice;
    }

    public void setnVertice(int nVertice) {
        this.nVertice = nVertice;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }
    
public int[][] inicializarGrafo(){//cria um grafo com o tamanho de nVertices
       int n = nVertice;
       int i,j;
       int mat[][] = new int[n][n];
       Random aleatorio = new Random();//gera um numero aleatório
       for(i=0;i<n;i++){
           for(j=0;j<n;j++){
               mat[i][j]= aleatorio.nextInt(100)+1;
           }
   }
       for(i=0;i<n;i++){
           for(j=0;j<n;j++){
               mat[i][j]= aleatorio.nextInt(100)+1;
           }
   }
       for(i=0;i<n;i++){
           mat[i][i]=0;
       }
     
      return mat;
}
     public void mostrarMatriz(int matIni[][]){//mostar a matriz(grafo)
         int i,j;
         int n = nVertice;
         int mat2[][] = matIni;
         for(i=0;i<n;i++){
             System.out.println();
           for(j=0;j<n;j++){
                
                System.out.print(mat2[i][j]);
                System.out.print(",");
           }
           }
         
     }
     public int[] zerarVet(int n){//zera o vetor
        int nVertice2 =getnVertice();
        int vetAux[] = new int [n];
        int i;
        for(i=0;i<n;i++){
            vetAux[i] = 0;
        }
        return vetAux;
    }
    public int[][] PopulacaoIni(int matAux [][]){//gera os cromossomos e os armazenas em uma matriz
 
        int i=0,j,a=0,aux=0;
        int nVertice2 = getnVertice();
        int vetAux[] = new int [nVertice2];
        int matC[][] = new int [200][nVertice2];
        Random al = new Random();
        int x = al.nextInt(nVertice2);
        
        for(i=0;i<200;i++){
            
                matC[i][0] = x;
            
        }
        
            for(i=0;i<200;i++){ // geração de cada cromossomo
                vetAux=zerarVet(nVertice2);
               
                vetAux[x] =  1;
                    
                aux = 1;
                
                for(j = 1; j < nVertice2; j++){ // geração de cada gene
                    a = al.nextInt(nVertice2); // tenta sortear, inicialmente, alguem não repetido
                    if(vetAux[a] == 0){
                        matC[i][j] = a;
                        vetAux[a] = 1;
                    }else{
                        while(vetAux[a] == 1){ //verificação de genes repetidos
                            a = al.nextInt(nVertice2);
                            if(vetAux[a] == 0){
                                matC[i][j] = a;
                                vetAux[a] = 1;
                                break;
                            }
                        }
                    }
                }
                
                
        
            }
            return matC;
     }
    public int[] fitn(int matIni[][],int matf[][]){//soma os pesos dos cromossomos
        int nVertice2 = getnVertice();
        //System.out.print(nVertice2);
        int matTgp[][] =  new int [200][nVertice2];
        int vetf[] = new int [200];
        int i,j,h,totalF=0,p1=0,p2=0;//p1 e p2 são as cordenadas dos valores das vertices
        matTgp=matIni;
        for(i=0;i<200;i++){
          
            
            totalF= 0;
            h=1;
            for(j=0;j<nVertice2;j++){
                p1 = matf[i][j];
                
               if(h<nVertice2){
                   p2 = matf[i][h];
                   h++;
               }else{
                   p2 = matf[i][0];
               }
                
                totalF = totalF + matTgp[p1][p2];//soma dos pesos
               //System.out.println(totalF);
            }
            vetf[i] = totalF;//vetor de fitn
        }
        return vetf;
    }
    public int  roleta(int tFit []){//Faz-se o uso de uma roleta fictícia, na qual cada cavidade (casa) representa um indivíduo
        int fitIv[] = new int [200];
        int total=0,i,j=0;
       
        for(i=0;i<200;i++){
            total=total+(tFit[i]);//calcula a soma de todos os fitness
        }
        for(i=0;i<200;i++){
           
            fitIv[i] = total - tFit[i];//inverte os valores (o menor vira maior e o maior o menor)
        }
        
        Random r = new Random();
        int fitR = r.nextInt(0,total*90);//gera um numero aleatorio de 0 ao total * 90(que é o maximo atingido)
        
       
        int totalParcial=0;
        i=-1;
        
        do {            
            i++;
            
         
            totalParcial = totalParcial + fitIv[i];
            
        } while (totalParcial<=fitR);//enquanto o totalParcial for menor que fitR
     
        return i;
        
    }
    public int [] SelecionarPaisRoleta(int tFit []){//seleciona dois pais que foram sorteados na rolta
        int pais[]= new int[2];
       int x=0,i,pai1,pai2,paiAux1,paiAux2;
        while(x!=1){
            paiAux1=roleta(tFit);
            paiAux2=roleta(tFit);
            pai1=paiAux1;
            if(paiAux2!= pai1){
                pai2 = paiAux2;
                
                pais[0]=pai1;
                pais[1]=pai2;
                x=1;
            }
            
        }
        
        return pais;//posição dos pais
    }
    public int torneio(int tFit []){/*É selecionado n indivíduos da população, aleatoriamente, e
escolhe-se o de melhor fitness entre os selecionados, de modo a
gerar uma população temporária;*/
        int fitTorneio[] = new int [40];// 20% da população de 200
        int vetAux[] = new int [nVertice];
        int total=0,i=0,j=1,tp=0,t=0,ta,p=0;
        Random r = new Random();
        int al = r.nextInt(200);
        //System.out.println(al);
        //adiciona e verifica se não a cromossomos repetidos
        fitTorneio[0]= al;
        vetAux=zerarVet(200);
        vetAux[al] = 1;
        while(i<40){
            al = r.nextInt(200);
            if(vetAux[al]==0){
                fitTorneio[i]= al;
                vetAux[al] = 1;
                i++;
            }else{
                al = r.nextInt(200);
            }
        }
        
     
        tp= tFit[1];
        for(i=0;i<40;i++){//o menor fitness será o vencedor
            t=fitTorneio[i];
            ta= tFit[t];
            if(ta<tp){
                tp = ta;
                p = i;
                
            }
            
        }
      
        return p;//retornar a posição
    }
    public int [] SelecionarPaisTorneio(int tFit []){//seleciona dois pais que foram vencedores do torneio
        int pais[]= new int[2];
       int x=0,i,pai1,pai2,paiAux1,paiAux2;
        while(x!=1){
            paiAux1=torneio(tFit);
            paiAux2=torneio(tFit);
            pai1=paiAux1;
            if(paiAux2!= pai1){
                pai2 = paiAux2;
                
                pais[0]=pai1;
                pais[1]=pai2;
                x=1;
            }
            
        }
       
        return pais;//posição dos pais
        
    }
    public int AvRepetido(int filho[],int p,int p1,int p2){
        int i,pv=p2-p1,r=0;
        for(i=0;i<=pv;i++){
            if(filho[p1+i]==p){
                r = 1;
                break;
            }else{
                r = 0;
            }
        }
        return r;
    }
    public int[][] crossoverR(int paiR[],int matC[][]){//não terminado
        int i=0,j,h=1,h2=1,p,p1,p2,ht=0,h3=0;
        int vet1 [] = new int[nVertice];
        int vet2 [] = new int[nVertice];
        int filho1 [] = new int[nVertice];
        int filho2 [] = new int[nVertice];
        int matP[][] = new int [2][nVertice];
        int matF[][] = new int [2][nVertice];
        
        Random r = new Random();
        p1 = nVertice/3;
        System.out.println(p1);
        p2=p1*2;
        for(i=0;i<2;i++){
            for(j=0;j<nVertice;j++){
                 matP[i][j]=matC[paiR[i]][j];
            }
        }
         h=p2+1;
         i=0;
         for(i=p1;i<=p2;i++){
             filho1[i] = matP[0][i];
             filho2[i] = matP[1][i];
         }
         for(i=p2+1;i<nVertice;i++){
            p=matP[0][i]; 
             if(AvRepetido(filho1, p,p1,p2)==0){
                 filho1[i]=matP[1][i];//parou aqui
             }
         }
        while(h!=p2){
            
            vet1[i] = matP[1][h];
            vet2[i] = matP[0][h];
            i++;
            if(h==nVertice-1){
                h=0;
               }else{
                    h++;
                    }
        }
      
      /*  filho1[al]= matP[0][al];
        filho1[p2]= matP[0][p2];
        filho2[al]= matP[1][al];
        filho2[p2]= matP[1][p2];
         /*for(i=0;i<nVertice;i++)
            System.out.print(filho1[i]+",");
         System.out.println("");
         for(i=0;i<nVertice;i++)
            System.out.print(vet1[i]+",");
         System.out.println("");
         i=0;
         h=0;
        
        while(h<nVertice){
            if(h!=p2 || h!=al){
                filho1[h] = vet1[i];
                filho2[h] = vet2[i];
            i++;
            h++;
            
        }else
            i=i+2;
       }*/
      /*  for(i=0;i<nVertice;i++)
            System.out.print(matP[0][i]+",");
        System.out.println("");
        for(i=0;i<nVertice;i++)
            System.out.print(filho1[i]+",");
        System.out.println("");
        for(i=0;i<nVertice;i++)
            System.out.print(matP[1][i]+",");
        */
     /* for(i=0;i<nVertice;i++){
           matF[0][i]=filho1[i];
           matF[1][i]=filho2[i];
      }
     
        return matF;
    }*/
   /* public int[][] crossoverT(int paiT[],int matC[][]){//não terminado
        int i=0,j,h=1,h2=1,al,p2,ht=0,h3=0;
        int vet1 [] = new int[nVertice];
        int vet2 [] = new int[nVertice];
        int filho1 [] = new int[nVertice];
        int filho2 [] = new int[nVertice];
        int matP[][] = new int [2][nVertice];
        int matF[][] = new int [2][nVertice];
        
        Random r = new Random();
        al = r.nextInt(nVertice);
        p2=al+1;
        for(i=0;i<2;i++){
            for(j=0;j<nVertice;j++){
                 matP[i][j]=matC[paiT[i]][j];
            }
        }
         h=p2+1;
         i=0;
        while(h!=p2){
            
            vet1[i] = matP[1][h];
            vet2[i] = matP[0][h];
            i++;
            if(h==nVertice-1){
                h=0;
               }else{
                    h++;
                    }
        }
      
        filho1[al]= matP[0][al];
        filho1[p2]= matP[0][p2];
        filho2[al]= matP[1][al];
        filho2[p2]= matP[1][p2];
         /*for(i=0;i<nVertice;i++)
            System.out.print(filho1[i]+",");
         System.out.println("");
         for(i=0;i<nVertice;i++)
            System.out.print(vet1[i]+",");
         System.out.println("");
         i=0;
         h=0;
        
        while(h<nVertice){
            if(h!=p2 || h!=al){
                filho1[h] = vet1[i];
                filho2[h] = vet2[i];
            i++;
            h++;
            
        }else
            i=i+2;
       }
      /*  for(i=0;i<nVertice;i++)
            System.out.print(matP[0][i]+",");
        System.out.println("");
        for(i=0;i<nVertice;i++)
            System.out.print(filho1[i]+",");
        System.out.println("");
        for(i=0;i<nVertice;i++)
            System.out.print(matP[1][i]+",");
        
      for(i=0;i<nVertice;i++){
           matF[0][i]=filho1[i];
           matF[1][i]=filho2[i];
      }*/
     
        return matF;
    }

    public int[][] mutacao(int matFR[][]){
        Random r = new Random();
        Random r2 = new Random();
        int al,al2,i,j,j2;
        int matFM [][] = new int [2][nVertice];
        al=r.nextInt(nVertice);
        al2=r2.nextInt(nVertice);
        matFM = matFR;
        j = matFR[0][al];
        j2 = matFR[1][al];
        matFM[0][al]=matFM[0][al2];
        matFM[0][al2]=j; 
        matFM[1][al]=matFM[1][al2];
        matFM[1][al2]=j2; 
       return matFM;
     
    }
    public void melhorOpPaisT(int matIni[][],int paiT[][]){
        int matTgp[][] =  new int [200][nVertice];
        int vetf[] = new int [2];
        int i,j,h,totalF=0,p1=0,p2=0,mt,m;//p1 e p2 são as cordenadas dos valores das vertices
        
        matTgp=matIni;
        for(i=0;i<2;i++){
          
            
            totalF= 0;
            h=1;
            for(j=0;j<nVertice;j++){
                p1 = paiT[i][j];
                
               if(h<nVertice){
                   p2 = paiT[i][h];
                   h++;
               }else{
                   p2 = paiT[i][0];
               }
                
                totalF = totalF + matTgp[p1][p2];//soma dos pesos
               //System.out.println(totalF);
            }
            vetf[i] = totalF;//vetor de fitn
        }
        if(vetf[0]>vetf[1]){
            m=0;
            mt=vetf[0];
        }else{
            m=1;
            mt=vetf[1];
        }
       System.out.println("Melhor Opção Torneio com o fitness" + mt);
        for(i=0;i<nVertice;i++){
             System.out.print(paiT[m][i]+",");
        }
       
        
    }
    public void melhorOpPaisR(int matIni[][],int paiR[][]){
        int matTgp[][] =  new int [200][nVertice];
        int vetf[] = new int [2];
        int i,j,h,totalF=0,p1=0,p2=0,mt,m;//p1 e p2 são as cordenadas dos valores das vertices
        
        matTgp=matIni;
        for(i=0;i<2;i++){
          
            
            totalF= 0;
            h=1;
            for(j=0;j<nVertice;j++){
                p1 = paiR[i][j];
                
               if(h<nVertice){
                   p2 = paiR[i][h];
                   h++;
               }else{
                   p2 = paiR[i][0];
               }
                
                totalF = totalF + matTgp[p1][p2];//soma dos pesos
               //System.out.println(totalF);
            }
            vetf[i] = totalF;//vetor de fitn
        }
        if(vetf[0]>vetf[1]){
            m=0;
            mt=vetf[0];
        }else{
            m=1;
            mt=vetf[1];
        }
        System.out.println("Melhor Opção Roleta com o fitness" + mt);
        for(i=0;i<nVertice;i++){
             System.out.print(paiR[m][i]+",");
        }
       
        
    }
    
    public void Ag_Tgp(){
        int i,j;
        int matIni[][] = new int [nVertice][nVertice];
        matIni=inicializarGrafo();//Cria o grafo
        int matC[][] = new int [200][nVertice];
        matC = PopulacaoIni(matIni);// cria a população de cromossomos
        int tFit[] = new int [200];
        tFit = fitn(matIni,matC);//total de fitiness
        int paiR [] = new int [2];
        int paiT [] = new int [2];
        int matFR [][] = new int [2][nVertice];
        int matFT [][] = new int [2][nVertice];
        int matFRM [][] = new int [2][nVertice];
        int matFTM [][] = new int [2][nVertice];
        int matPTO [][] = new int [2][nVertice];
        int matPRO [][] = new int [2][nVertice];
        paiR = SelecionarPaisRoleta(tFit);//pais escolhidos no metodo da roleta
        paiT = SelecionarPaisTorneio(tFit);//pais escolhidos no metodo da roleta
       for(i=0;i<2;i++){
            for(j=0;j<nVertice;j++){
                 matPTO[i][j]=matC[paiT[i]][j];
            }
        }
       for(i=0;i<2;i++){
            for(j=0;j<nVertice;j++){
                 matPRO[i][j]=matC[paiR[i]][j];
            }
        }
        mostrarMatriz(matIni);
        System.out.println("");
        mostrarMatCr(matC);
        System.out.println("");
        System.out.println("---//---");
       // mostrarFit(tFit);
        matFR=crossoverR(paiR, matC);//Limitação dando erro
        //matFT=crossoverT(paiR, matC);//Limitação dando erro
        if(nVertice>1000){
            matFRM = mutacao(matFR);//Limitação dando erro
            matFTM = mutacao(matFT);//Limitação dando erro
        }
        melhorOpPaisT(matIni, matPTO);//Pega o melhor Fitness dos candidatos do torneio
        System.out.println("");
        melhorOpPaisR(matIni, matPRO);//Pega o melhor Fitness dos candidatos da Roleta
    }
    
    public void mostrarMatCr(int matC[][]){//mostra a matriz de cromossomos(população)
         int i,j;
         int n = nVertice;
         System.out.println("---//---");
         for(i=0;i<200;i++){
             System.out.println();
           for(j=0;j<n;j++){
                System.out.print(matC[i][j]);
                System.out.print(",");
           }
           }
    }
    public void mostrarFit(int vetAux[]){//mostra o fitness de cada cromosssomo
        int i;
        for(i=0;i<200;i++){
            System.out.print(vetAux[i]+",");
        }
    }
}
