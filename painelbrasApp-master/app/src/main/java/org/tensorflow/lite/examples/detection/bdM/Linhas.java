package org.tensorflow.lite.examples.detection.bdM;

import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Linhas  {
	 public Statement statement=null;
	  public ResultSet cursor=null;
	int ncoluna=0;
	int nlinhas=0;
	int vez=0;
	public int colunarealT=0;
	public int cont=-1;
	public String table="";
	public colunaT colunas[]=null;
	public colunaT colunasrealT[]=null;
	public String consulta="";
	public String ordena="desc";
	public String novaconsulta="";
 public String consultaI="";
  
	public void add(Statement statement, Banco banco, String tabs, colunaT colunas[], String consultaI, String consulta){
		this.consultaI=consultaI;
             try {
                 this.statement=statement;
                 
 		this.cursor=statement.executeQuery(consultaI);
		           
		this.table=tabs;
           
                ncoluna=cursor.getMetaData().getColumnCount();
           
                cursor.last();
		nlinhas=cursor.getRow();
                cursor.beforeFirst();
		this.colunas=colunas;
		this.consulta=consulta;
		montacolunaR();
		
		
		 } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	public void add(Statement statement, String consulta, String tabs ){
 		try {
                    this.consulta=consulta;
                 this.statement=statement;
 		this.cursor=statement.executeQuery(consulta);
                
		this.table=tabs;
		   ncoluna=cursor.getMetaData().getColumnCount();
           
                cursor.last();
		nlinhas=cursor.getRow();
                cursor.beforeFirst();
		this.colunas=colunas;
                 } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
		montacolunaR();
	}
	
	
	public void reOrdena(String ordena, colunaT ...colunas){
		StringBuffer ordem=new StringBuffer("order by ");
		
		for(int cont=0;cont<colunas.length;cont++){
			
			if(cont+1==colunas.length){
				ordem.append(colunas[cont].C()+" "+ordena+" ");

				
			}else
			ordem.append(colunas[cont].C()+" "+ordena+", ");
			
			
		} 
		
		
		novaconsulta=consulta.replaceAll(BancoT.div, ordem.toString());
		
             try {
                 cursor=statement.executeQuery(novaconsulta);
                  cursor.beforeFirst();
             } catch (SQLException ex) {
                 Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
             }

vez=0;
cont=-1;

	}
	
	
	private void montacolunaR(){
	 
	 
		
		 ArrayList<colunaT> co=new ArrayList<colunaT>();
		int cs=0;
		for(int cont=0;cont<numcoluna();cont++){
			if(!getNaTabela(cont)){
				
				continue;
				
			}
			 
			co.add(colunas[cont]);
		 
		}
		
		colunasrealT= co.toArray(new colunaT[co.size()]);
            try {
                cursor.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
		vez=0;
		cont=-1;
		
	}
	
	public int numcolunaRT(){
		
		return colunasrealT.length;
		
	}
	
	public int numcoluna(){
		
		return ncoluna;
	}
	public int numlinha(){
		return size();
		
	}
	
	public int size(){
		 
		return nlinhas;
		
	}
	
public boolean real(int cont){
	
            try {
                if(cursor.getMetaData().getColumnType(cont)==6||
                        cursor.getMetaData().getColumnType(cont)==7){
                    
                    return true;
                }   } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
	
	return false;
}
	public boolean next(){

		try {
			if (size() > 0 && vez == 0) {
				vez = 1;
				cont++;
				try {
					return cursor.next();
				} catch (SQLException ex) {
					Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			boolean bol = false;
			try {
				bol = cursor.next();

				if (!bol) {
					vez = 0;
					cont = -1;
					cursor.beforeFirst();
				} else cont++;


			} catch (SQLException ex) {
				Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
			}
			return bol;
		}catch (Exception e){

			return false;
		}
	}
	
	public boolean getNaTabela(int ncoluna){

		 if(colunas!=null){
			 
			 return (colunas[ncoluna].natabela&&colunas[ncoluna].natabelausuario)?true:false;
			 
		 }
		 return false;	
		 
	
	
	}
	public synchronized String getSN(colunaT c, String tx){

		String dC=(c.getSoma())?c.T()+c.C():c.C();


		if(( get(c,dC)+"").contentEquals("null")){

			return tx;
		}

		return get(c,c.C())+"";
	}

	public synchronized String getSN(colunaT c){
		String dC=(c.getSoma())?c.T()+c.C():c.C();


		return getS(c,dC).replaceAll("null", "");
	}



	public String getC(int ncoluna){

		 if(colunas!=null){
			 
			 return colunas[ncoluna].coluna;
			 
		 }
            try {
                return cursor.getMetaData().getColumnName(ncoluna);
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
	}

	public String getT(int ncoluna){

		 if(colunas!=null){
			 
			 return colunas[ncoluna].tipo;
			 
		 }
		return "";
	}
	
	
	
	public String getPF(int ncoluna){

		 if(colunas!=null){
			 
			 return colunas[ncoluna].nomePref;
			 
		 }
		   try {
                return cursor.getMetaData().getColumnName(ncoluna);
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
	}
	


	public String getSN(colunaT c, int coluna, String tx){
		if( (get(c,coluna)+"").contentEquals("null")){
			
			return tx;
		}
		
		 return get(c,coluna)+"";

				
			}
public String getS(colunaT c, int coluna){
		
		
 return get(c,coluna)+"";

		
	}
	public String getS(int coluna){


		return get(null,coluna)+"";


	}
public String getSDF(colunaT c){

	 String dt[]=(get(c,c.C())+"").split("/");
	 if(dt.length<3)return (get(c,c.C())+"");
return dt[2]+"/"+dt[1]+"/"+dt[0] ;
}

public int getI(colunaT c, int ncoluna){

String cv=get(c,ncoluna)+"";
	if(cv.contains("null"))cv="0";
	
	if(cv.length()==0)return 0;;
	
	return Integer.parseInt(cv);
}
public double getD(colunaT c,int ncoluna){

String cv=get(c,ncoluna)+"";
	if(cv.contains("null"))cv="0";
	
	if(cv.length()==0)return 0;;
	
	return Double.parseDouble(cv);
}
public double getD(colunaT c, String ncoluna){

String cv=get(c,ncoluna)+"";
	if(cv.contains("null"))cv="0";
	
	return Double.parseDouble(cv);
}

public Boolean getB(colunaT c, int ncoluna){

	 
	if(getS(c,ncoluna).contains("true")||getS(c,ncoluna).contains("True"))
		return true;


		 
	return false;
}
public Boolean getB(colunaT c, String ncoluna){
if(getS(c,ncoluna).contains("true")||getS(c,ncoluna).contains("True"))
	return true;


	 
return false;

}
public String getS(colunaT c, String ncoluna){

	 
	return get(c,ncoluna)+"";
}



	public Object get(colunaT c, String ncoluna){

	if(size()==0)return null;
	if(cont==-1)next();

		if(c.tipo.contentEquals(BancoT.blob))
		{
			try{

				return cursor.getBlob(ncoluna);
			}catch(Exception e){
				e.printStackTrace(); Log.d("","ErroBD "+e.toString()+" "+c.C());

			}


		}
      
        
	try{
	return cursor.getString( (ncoluna));
	}catch(Exception e){
		e.printStackTrace(); 
		
	}
	try{
		return cursor.getDouble( (ncoluna));
		}catch(Exception e){
			e.printStackTrace();  
			
		}
	return null;
}


 
public int getSI(colunaT cl,int c){
if(getS(cl,c).contentEquals("null"))return 0;
	 
return Integer.parseInt(get(cl,c)+"");
}
public double getSD(colunaT c){

	 
return Double.parseDouble(get(c,c.C())+"");
}

public double getD(colunaT c){
	
String dC=(c.getSoma())?c.T()+c.C():c.C();
 
String cv=getS(c,dC);
if(cv.contains("null"))cv="0";

return Double.parseDouble(cv.replaceAll(",", "."));
}
public int getTI(colunaT c){
	String dC=(c.getSoma())?c.T()+c.C():c.C();

 
return Integer.parseInt(get(c,dC)+"");
}

public Date getDate(colunaT c){
 

 
return  (Date) get(c);
}

public boolean getB(colunaT c){
	String dC=(c.getSoma())?c.T()+c.C():c.C();

 
return getS(c,dC).contains("true");
}

public String getS(colunaT c){
	String dC=(c.getSoma())?c.T()+c.C():c.C();

 
return get(c,dC)+"";
}

public Object get(colunaT c){
	String dC=(c.getSoma())?c.T()+c.C():c.C();

	
if(size()==0)return null;
if(cont==-1)next();

	if(c.tipo.contentEquals(BancoT.blob))
	{try{
		return cursor.getBytes((dC));
	}catch(Exception e){
		e.printStackTrace();

	}
	}

if(c.tipo.contentEquals(BancoT.date))
{try{
return cursor.getDate((dC));
}catch(Exception e){
	e.printStackTrace();  
	
}
}
try{
return cursor.getString( (dC));
}catch(Exception e){
	e.printStackTrace();  
	
}
try{
	return cursor.getDouble( (dC));
	}catch(Exception e){
		e.printStackTrace();  
		
	}
return null;
}



	public Object get(colunaT c, int coluna){
	 
		if(size()==0)return null;
		if(cont==-1)next();



		if(c!=null&&c.tipo.contentEquals(BancoT.blob))
		{
			try{

				return cursor.getBlob(coluna+1);
			}catch(Exception e){
				e.printStackTrace(); Log.d("","ErroBD "+e.toString()+" "+c.C());

			}


		}

		if(colunas[coluna].tipo.contentEquals(BancoT.real)||
                        colunas[coluna].tipo.contentEquals(BancoT.numero)){
		//	Log.d("Entrou Aqui X","Entrou Aqui X "+cursor.getFloat(coluna));
	
		try{
                    
			return cursor.getFloat(coluna+1);
			}catch(SQLException e){
				
				System.out.println("EROOO "+coluna);
			
                        e.printStackTrace(); 
                        }

		
		}
		try{
		return cursor.getString(coluna+1);
		}catch(Exception e){
                    System.out.println("EROOO 1 "+coluna);
			e.printStackTrace();  
			 
		}
		
		return null;
	}
	
	public String getS(colunaT c, int linha, int coluna){
            try {
                cursor.absolute(linha);
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
		
			 return get(c,coluna)+"";
		
	}
	
	
	public Object linha(colunaT c, int linha, int coluna){
		 try {
                cursor.absolute(linha);
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }

		return get(c,coluna);
		
	}
	public String linhaS(int linha, int coluna){
 		 try {
                cursor.absolute(linha);
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
		try{
			return cursor.getString(coluna);
			}catch(Exception e){
				e.printStackTrace();  
				
			}
			 return null;
		
	}
	
	public ArrayList ar(colunaT ...c){
             try {
                 cursor.beforeFirst();
             } catch (SQLException ex) {
                 Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
             }
		ArrayList l=new ArrayList();
	while(next()){
		String acum="";
		for(int cont=0;cont<c.length;cont++){
			
			acum+=get(c[cont],c[cont].C())+"\n";
			
			
		}
		
		l.add(acum);
		
	}
            try {
                cursor.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
	return l;
		
	}
	
	
	public String[] av( ){
		vez=0;
             try {
                 cursor.beforeFirst();
             } catch (SQLException ex) {
                 Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
             }
		StringBuffer acum=new StringBuffer();
	while(next()){
		 
		for(int cont=0;cont<numcoluna();cont++){
			
			acum.append(get(colunasrealT[cont],cont)+BancoT.div);
			
			
		}
		
 		
	}
	vez=0;
            try {
                cursor.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
	return acum.toString().split(BancoT.div);
		
	}

	public synchronized String[] av(colunaT c ){
		vez=0;
		try {
			cursor.beforeFirst();
		} catch (SQLException ex) {
			Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
		}
		StringBuffer acum=new StringBuffer();
		while(next()){

			for(int cont=0;cont<numcoluna();cont++){
				if(c.CT().contentEquals(colunas[cont].CT()))
				{
					acum.append(get(c,cont)+BancoT.div);

				}


			}


		}
		vez=0;
		try {
			cursor.beforeFirst();
		} catch (SQLException ex) {
			Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
		}
		return acum.toString().split(BancoT.div);

	}
	
	public ArrayList ar( ){
		vez=0;
            try {
                cursor.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
		ArrayList l=new ArrayList();
	while(next()){
		StringBuffer acum=new StringBuffer();
		for(int cont=0;cont<numcoluna();cont++){
			
			acum.append(get(colunasrealT[cont],cont)+", ");
			
			
		}
		
		l.add(acum.toString());
		
	}
            try {
                cursor.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
	vez=0;
	return l;
		
	}
	
	public ArrayList arC( ){
		vez=0;
            try {
                cursor.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
		ArrayList l=new ArrayList();
	while(next()){
		StringBuffer acum=new StringBuffer();
		
		for(int cont=0;cont<numcoluna();cont++){
			 
			acum.append(getC(cont)+" "+get(colunasrealT[cont],cont)+", ");
			
			
		}
		
		l.add(acum.toString());
		
	}
	vez=0;
            try {
                cursor.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
	return l;
		
	}
        
        public void vaiInicio(){
            	vez=0;
            try {
                cursor.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	public void dA(){
		if(size()==0)return;
		String del="delete from "+table+" where ";
		String acum="";
		
		for(int cont=0;cont<numcoluna();cont++){
                    try {	
                        acum+=" "+cursor.getMetaData().getColumnName(cont)+"='"+cursor.getString(cont)+"' and";
                    } catch (SQLException ex) {
                        Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
		acum=acum.substring(0, acum.length()-3);
             try {
                 statement.executeUpdate(del+acum);
             } catch (SQLException ex) {
                 Logger.getLogger(Linhas.class.getName()).log(Level.SEVERE, null, ex);
             }
	}
 
	
	
    
    
//	public Linhas clone()  {
//	    try {
//	    	
//			return (Linhas) super.clone();
//			
//		} catch (CloneNotSupportedException e) {
//			 
//			e.printStackTrace();
//			return null;
//		}
//	}
 
	
}
