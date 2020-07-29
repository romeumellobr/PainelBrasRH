package org.tensorflow.lite.examples.detection.bdM;

import android.widget.TextView;


import org.tensorflow.lite.examples.detection.Biblis.Datas;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public 
class colunaT implements Cloneable, Serializable {
    public String avulso;
    public String condi="=";
	boolean autoincrement=false;
	public String nomePref="";
	public String pselect="";
	public String pcreate="";
 String coluna="";
 String tabela="";
	 Object dados="";
public String tipo="";
boolean estado=true;
public static int like=0;
public static int and=1;
public static int or=2;
public static int igual=3;
public static  int like1=4;
public static  int maiorigual=5;
public static  int menorigual=6;
public boolean sinc=true; 
public String total="";
public  int mtipo=-1;
int ct=-1;
boolean soma=false;
public boolean natabela=true;
public boolean natabelausuario=true;

    public String getAvulso() {
        return avulso;
    }

    public void setAvulso(String avulso) {
        this.avulso = avulso;
    }




public colunaT(String coluna, String tabela, String tipo, boolean estado, String nomePref, boolean natabela){
	this.coluna=coluna;
	this.tabela=tabela;
	 this.tipo=tipo;
	 this.natabela=natabela;
	 if(tipo.contentEquals( BancoT.auto_increment))autoincrement=true;
	 this.estado=estado;
	 coluna=tabela+"."+coluna;
	 this.nomePref=nomePref;
	 if (estado)
     {
         boolean b = true;
         for (int cont = 0; cont < Banco.tabelas.size(); cont++)
         {

             if (Banco.tabelas.get(cont).C().contentEquals(C())&&
            		 Banco.tabelas.get(cont).T().contentEquals(T()
                 ))
             {

                 b = false;
             }

         }
         if (b)
             Banco.tabelas.add(this);
     }
	 
	
	 

}





public colunaT(String coluna, String tabela, String tipo, boolean estado, String nomePref){
	this.coluna=coluna;
	this.tabela=tabela;
	 this.tipo=tipo;
	 if(tipo.contentEquals( BancoT.auto_increment))autoincrement=true;
	 this.estado=estado;
	 coluna=tabela+"."+coluna;
	 this.nomePref=nomePref;
	 if (estado)
     {
         boolean b = true;
         for (int cont = 0; cont < Banco.tabelas.size(); cont++)
         {

             if (Banco.tabelas.get(cont).C().contentEquals(C())&&
            		 Banco.tabelas.get(cont).T().contentEquals(T()
                 ))
             {

                 b = false;
             }

         }
         if (b)
             Banco.tabelas.add(this);
     }
	 
	
	 

}
	public colunaT(String coluna, String tabela, String tipo, boolean estado){
		this.coluna=coluna;
		this.tabela=tabela;
		 this.tipo=tipo;
		 if(tipo.contentEquals( BancoT.auto_increment))autoincrement=true;
		 this.estado=estado;
		 coluna=tabela+"."+coluna;
		 this.nomePref=coluna;
		 if (estado)
	     {
	         boolean b = true;
	         for (int cont = 0; cont < Banco.tabelas.size(); cont++)
	         {

	             if (Banco.tabelas.get(cont).C().contentEquals(C())&&
	            		 Banco.tabelas.get(cont).T().contentEquals(T()
	                 ))
	             {

	                 b = false;
	             }

	         }
	         if (b)
	             Banco.tabelas.add(this);
	     }	 
	}
	
	
	
	public colunaT(String coluna, String tabela, String tipo, boolean estado, boolean sinc){
		this.coluna=coluna;
		this.tabela=tabela;
		 this.tipo=tipo;
		 this.sinc=sinc;
		 if(tipo.contentEquals( BancoT.auto_increment))autoincrement=true;
		 this.estado=estado;
		 coluna=tabela+"."+coluna;
		 this.nomePref=coluna;
		 if (estado)
	     {
	         boolean b = true;
	         for (int cont = 0; cont < Banco.tabelas.size(); cont++)
	         {

	             if (Banco.tabelas.get(cont).C().contentEquals(C())&&
	            		 Banco.tabelas.get(cont).T().contentEquals(T()
	                 ))
	             {

	                 b = false;
	             }

	         }
	         if (b)
	             Banco.tabelas.add(this);
	     } 
	}
	
	
	
	
	public colunaT setCondi(String condi){
            this.condi=condi; return this;
            
        }
	public colunaT setSoma(boolean soma){
		
		this.soma=soma;
		return this;
	}
	
	public boolean getSoma(){return soma;}
	public colunaT CS(String coluna){
		this.coluna=coluna;
		return this;
	}
	
	
	
	public void setTotal(String total){
		
		this.total=total;
		
		
	}
	
	
	public String getTotal(){
		
		return this.total;
		
	}
	
	public String S(){
		return D()+"";
		
	}
	
	public String DD(){
		 
		if(((String)dados).length()==0)return "-1";
		return ((String)dados).replaceAll(",", ".");
		
	}
	
	public Object D(){
	 
		return dados;
		
	}
	public void add(Object ob){
		dados=ob+"";
		
	}
	
	
	public colunaT SD(String dados, int mtipo, int ct){
		this.dados=dados;
		this.mtipo=mtipo;
		this.ct=ct;
		
		return this;
		
	}

	public colunaT ObI(Object dados){
		this.dados=( (TextView)dados).getText().toString();
		return this.clone();

	}
	public colunaT Dado(Object dados){
		this.dados=dados;
		return this;

	}

	public colunaT DataM(Object dados){
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date data = formato.parse(dados+"");
		dados=	new Datas().getDateMysql(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.dados=dados;
		return this;

	}


	public String T(){
		
		return tabela;
		
	}
public String CT(){
		
		return tabela+"."+coluna;
		
	}

public String C(){
	
	return  coluna;
	
}
 

public String CPref(){
	
	return nomePref;
	
}
public String P(){
	
	return tipo;
	
}
	
public boolean E(){
	
	return estado;
	
}

public String pSelect(){
	
	return pselect;
	
}
public String pCreate(){
	
	return pcreate;
	
}

public colunaT clone()  {
    try {
    	
		return (colunaT) super.clone();
		
	} catch (CloneNotSupportedException e) {
		 
		e.printStackTrace();
		return null;
	}
}

    public Object getDados() {
        return dados;
    }

    public void setDados(Object dados) {
        this.dados = dados;
    }




}
