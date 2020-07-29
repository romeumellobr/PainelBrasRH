
package org.tensorflow.lite.examples.detection.Banco;



import org.tensorflow.lite.examples.detection.LoginActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//mport javax.swing.JOptionPane;

public class Conexao {

  private String driver = "com.mysql.jdbc.Driver";
  private String url = "jdbc:mysql://localhost:3306/mysql";
  private String usuario = "root";
  private String senha = "123";
  public Connection Conexao;
  public Statement statement;
  public ResultSet resultset;
 public boolean result = true;
    LoginActivity pri=null;
public   Conexao(String host, String porta, String banco, String usuarioe, String senhae, LoginActivity pri)
{
    this.pri=pri;
    url = "jdbc:mysql://"+host+":"+porta+"/"+banco;
    senha=senhae;
    usuario=usuarioe;
  
    try{
          Class.forName("com.mysql.jdbc.Driver");


        Conexao = DriverManager.getConnection(url,usuario,senha);
        //JOptionPane.showMessageDialog(null,"Conectou");
    }
    catch(ClassNotFoundException Driver)
    {
   System.out.println("Driver Nao Encontrado:"+Driver);
     result = false;
    }
    catch(SQLException Fonte)
    {
   System.out.println("Deu erro na Conexao com a fonte de dados: "+Fonte);

     //new EditarBanco().setVisible(true);

     result = false;


    }
   
}
public void desconecta(){

    boolean result = true;
    try{
        Conexao.close();
       // JOptionPane.showMessageDialog(null,"Banco fechado");
    }
    catch(SQLException erroSQL){
      System.out.println("Nao foi possivel"+ "fechar o banco de dados:"+erroSQL.getMessage());
  result = false;
    }

}

public void executeSQL(String sql){

    try{
        statement = Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultset = statement.executeQuery(sql);
    }
    catch(SQLException sqlex){
  pri.Mensagem("Erro banco","Nao foi possivel"+"executar o comando sql,"+sqlex+", "
             + "o sql passado foi "+sql+" \n original" + sqlex.getMessage());
    }

}



public boolean executeUpdateL(String sql){

    try{
        statement = Conexao.createStatement();
        statement.executeUpdate(sql);
   return true;
    }
    catch(SQLException sqlex){
 pri.MensagemOFF ("Erro Banco","Nao foi possivel"+"executar o comando sql,"+sqlex+", "
             + "o sql passado foi "+sql+" \n original" + sqlex.getMessage());
   return false;
    }

}

public String executeUpdateS(String sql){

    try{
        statement = Conexao.createStatement();
        statement.executeUpdate(sql);
   return "Sucesso";
    }
    catch(SQLException sqlex){
  
   return "Nao foi possivel"+"executar o comando sql,"+sqlex+", "
             + "o sql passado foi "+sql+" \n original" + sqlex.getMessage();
    }

}

public ArrayList retorna1SQL(String sql, String coluna) {
   ArrayList<String> acum = new ArrayList();
    try {
     
               statement = Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultset = statement.executeQuery(sql);
          while (next()) {
              try {
                  acum.add( resultset.getString(coluna));
              } catch (SQLException ex) {
                  Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return acum;
      } catch (SQLException ex) {
          Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
          
      return null;
      }
}


public ArrayList retornaNSQL(String sql, String[] coluna) {
   ArrayList<String> acum = new ArrayList();
    try {
               statement = Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultset = statement.executeQuery(sql);
          while (next()) {
              try {
                  for(int cont=0;cont<coluna.length;cont++)
                  acum.add( resultset.getString(coluna[cont]));
              } catch (SQLException ex) {
                  Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return acum;
      } catch (SQLException ex) {
          Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
      return null;
      }
}





public ArrayList retornaBancos() {
   ArrayList<String> acum = new ArrayList();
    try {
          ResultSet rs = Conexao.getMetaData().getCatalogs();
          while (rs.next()) {
              try {
                  acum.add( rs.getString(1));
              } catch (SQLException ex) {
                  Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return acum;
      } catch (SQLException ex) {
          Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
      return null;
      }
}


public ArrayList retornaTabelas(){
executeSQL("show tables");
  ArrayList<String> acum = new ArrayList();
while(next()){
    try {
     acum.add(resultset.getString(1)) ;
    } catch (SQLException ex) {
        Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
    return null;
    }

}
return acum;
}


public ArrayList retornaColunasTabela(String tabela){
executeSQL("describe "+tabela);
  ArrayList<String> acum = new ArrayList();
while(next()){
    try {
        acum.add(resultset.getString(1) );
    } catch (SQLException ex) {
        Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
    return null;
    }

}
return acum;
}



public ArrayList retornaTodaSql(String tabela) {
    ArrayList tabelas=retornaColunasTabela(tabela);
    int mx=tabelas.size();
  
       executeSQL("select * from "+tabela);
        
       ArrayList<String> acum = new ArrayList();
          while (next()) {
               ArrayList<String> acumi = new ArrayList();
                  for (int i = 0; i <= mx; i++) {
                      try {
                          acumi.add(tabelas.get(i)+" - "+resultset.getString(i+1));
                      } catch (SQLException ex) {
                          Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  }
                  acum.add(acumi.toString());
                  
          }
        
          return acum;
     
}

public int getNcolunas(){
   
     try {
            return resultset.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
       return 0;
        }
}
public String getString(int cl){
      try {
          return resultset.getString(cl);
      } catch (SQLException ex) {
          Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
      return null;
      }

}

public String getString(String cl){
      try {
          return resultset.getString(cl);
      } catch (SQLException ex) {
          Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
      return null;
      }

}



public boolean next(){
        try {
            return resultset.next();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
       return false;
        }

}
}


 