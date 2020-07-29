
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

public class ConexaoPostGree implements ConexaoG {

  private String driver = "com.mysql.jdbc.Driver";
  private String url = "jdbc:mysql://localhost:3306/mysql";
  private String usuario = "root";
  private String senha = "123";
  public Connection Conexao;
  public Statement statement;
  public ResultSet resultset;
 public boolean result = true;
 LoginActivity pri=null;
public boolean Conecta(String host, String porta, String banco, String usuarioe, String senhae, LoginActivity pri)
{
    this.pri=pri;
   driver ="org.postgresql.Driver";
    url = "jdbc:postgresql://"+host+":"+porta+"/"+banco;
    senha=senhae;
    usuario=usuarioe;
  
    try{
          Class.forName(driver);


        Conexao = DriverManager.getConnection(url,usuario,senha);
        result=true;
        //JOptionPane.showMessageDialog(null,"Conectou");
    }
    catch(ClassNotFoundException Driver)
    {

        pri.Mensagem("Erro",""+Driver.toString());
        result = false;
    }
    catch(SQLException Fonte)
    {
        pri.Mensagem("Erro",""+Fonte.toString());

        //new EditarBanco().setVisible(true);

        result = false;


    }
    return result;
   
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


    public String getErro(){

        return erro;
    }
    public String banco, table;
    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getBanco() {
        return banco;
    }
    public void setBanco(String banco){

        this.banco=banco;

    }


    public void setTable(String table){

        this.table=table;

    }
    public String erro="";
    public boolean executeUpdateL(String sql){

        try{
            statement = Conexao.createStatement();
            statement.executeUpdate(sql);
            return true;
        }
        catch(SQLException sqlex){
            pri.MensagemOFF ("Erro Banco","Nao foi possivel"+"executar o comando sql,"+sqlex+", "
                    + "o sql passado foi "+sql+" \n original" + sqlex.getMessage());

            erro="Nao foi possivel"+"executar o comando sql,"+sqlex+", "
                    + "o sql passado foi "+sql+" \n original" + sqlex.getMessage();
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

            erro="Nao foi possivel"+"executar o comando sql,"+sqlex+", "
                    + "o sql passado foi "+sql+" \n original" + sqlex.getMessage();

            return "Nao foi possivel"+"executar o comando sql,"+sqlex+", "
                    + "o sql passado foi "+sql+" \n original" + sqlex.getMessage();
        }

    }
    public String getFonte(){return fontPostGreeSql;}
public ArrayList retorna1SQL(String sql, String coluna) {
   ArrayList<String> acum = new ArrayList();
    try {
     
               statement = Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultset = statement.executeQuery(sql);
          while (next()) {
              try {
                  acum.add( resultset.getString(coluna));
              } catch (SQLException ex) {
                  Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return acum;
      } catch (SQLException ex) {
          Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
          
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
                  Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return acum;
      } catch (SQLException ex) {
          Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
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
                  Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return acum;
      } catch (SQLException ex) {
          Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
      return null;
      }
}


public ArrayList retornaTabelas(String nomebanco){
executeSQL("SELECT table_name \n" +
        "FROM information_schema.tables \n" +
        "WHERE table_schema = 'public'\n" +
        "ORDER BY table_name;");
  ArrayList<String> acum = new ArrayList();
while(next()){
    try {
     acum.add(resultset.getString(1)) ;
    } catch (SQLException ex) {
        Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
    return null;
    }

}
return acum;
}


public ArrayList retornaColunasTabela(String tabela){
executeSQL("SELECT\n" +
        "\t COLUMN_NAME\n" +
        " FROM \n" +
        "\t information_schema.COLUMNS\n" +
        " WHERE\n " +
        "\t TABLE_NAME = '"+tabela+"' ");
  ArrayList<String> acum = new ArrayList();
while(next()){
    try {
        acum.add(resultset.getString(1) );
    } catch (SQLException ex) {
        Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
    return null;
    }

}
return acum;
}


    ArrayList<Object[]> acum = new ArrayList();

    @Override
    public ArrayList<Object[]> retornaTodaSql() {
        return acum;
    }

    /*     */   public ArrayList<Object[]> retornaTodaSql(String bancoc, String tabela) {
        /* 159 */     ArrayList tabelas = retornaColunasTabela(tabela);
        setColunas(tabelas);
        /* 161 */     int mx = tabelas.size();
        /*     */
        /* 163 */     executeSQL("select * from " + tabela);
        /*     */
        /* 165 */    acum = new ArrayList();
        /*     */
        /*     */
        /* 168 */     acum.add(tabelas.toArray());
        /*     */
        /* 170 */     while (next()) {
            /* 171 */       ArrayList<String> acumi = new ArrayList();
            /* 172 */       for (int i = 0; i < mx; i++) {
                /*     */         try {
                    /* 174 */           acumi.add(this.resultset.getString(i + 1)+" ");
                    /* 175 */         } catch (SQLException ex) {
                    /* 176 */          pri.Mensagem("ERRO",""+ex.toString());
                    /*     */         }
                /*     */       }
            /* 179 */       acum.add(acumi.toArray());
            /*     */     }
        /*     */
        /*     */
        /* 183 */     return acum;
        /*     */   }
    /*     */
    /*     */
public int getNcolunas(){
   
     try {
            return resultset.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
       return 0;
        }
}
public String getString(int cl){
      try {
          return resultset.getString(cl);
      } catch (SQLException ex) {
          Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
      return null;
      }

}

public String getString(String cl){
      try {
          return resultset.getString(cl);
      } catch (SQLException ex) {
          Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
      return null;
      }

}



public boolean next(){
        try {
            return resultset.next();
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoPostGree.class.getName()).log(Level.SEVERE, null, ex);
       return false;
        }

}

ArrayList<String> cln=new ArrayList<String>();

    public ArrayList<String> getColunas(){

    return cln;

    };
    public void setColunas(ArrayList<String> colunas){
        cln=colunas;

    };

    ArrayList<String> bancos=new ArrayList<>();
    ArrayList<String> tabelas=new ArrayList<>();
    @Override
    public ArrayList<String> setBancos(String banco) {
        this.bancos.add(banco);
        return bancos;
    }

    @Override
    public ArrayList<String> setTabelas(ArrayList<String> tabelas) {
        this.tabelas=tabelas;
        return tabelas;
    }

    @Override
    public ArrayList<String> getBancos() {
        return bancos;
    }

    @Override
    public ArrayList<String> getTabelas() {
        return tabelas;
    }
}


 