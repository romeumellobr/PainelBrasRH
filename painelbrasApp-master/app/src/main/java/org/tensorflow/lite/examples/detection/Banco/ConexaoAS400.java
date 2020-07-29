
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

public class ConexaoAS400 implements  ConexaoG{

  private String driver = "com.mysql.jdbc.Driver";
  private String url = "jdbc:mysql://localhost:3306/mysql";
  private String usuario = "root";
  private String senha = "123";
  public Connection Conexao;
  public Statement statement;
  public ResultSet resultset;
 public boolean result = true;
 LoginActivity pri=null;
public boolean Conecta(String banco, String host, String porta, String usuarioe, String senhae, LoginActivity pri)
{
    this.pri=pri;
    driver ="com.ibm.as400.access.AS400JDBCDriver";
    url =   "jdbc:as400://"+host+":"+porta+"/"+banco;
    senha=senhae;
    usuario=usuarioe;
  
    try{
          Class.forName(driver);


        Conexao = DriverManager.getConnection(url,usuario,senha);
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

    ArrayList<Object[]> acum = new ArrayList();

    @Override
    public ArrayList<Object[]> retornaTodaSql() {
        return acum;
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
        statement = Conexao.createStatement();
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

public ArrayList retorna1SQL(String sql, String coluna) {
   ArrayList<String> acum = new ArrayList();
    try {
     
               statement = Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultset = statement.executeQuery(sql);
          while (next()) {
              try {
                  acum.add( resultset.getString(coluna));
              } catch (SQLException ex) {
                  Logger.getLogger(ConexaoAS400.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return acum;
      } catch (SQLException ex) {
          Logger.getLogger(ConexaoAS400.class.getName()).log(Level.SEVERE, null, ex);
          
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
                  Logger.getLogger(ConexaoAS400.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return acum;
      } catch (SQLException ex) {
          Logger.getLogger(ConexaoAS400.class.getName()).log(Level.SEVERE, null, ex);
      return null;
      }
}

public String getFonte(){return fontAS400;}

    public ArrayList retornaBancos() {
        /* 107 */     ArrayList<String> acum = new ArrayList<String>();
        /*     */     try {

            executeSQL("select DISTINCT   SYSTEM_TABLE_SCHEMA,TABLE_SCHEMA \n" +
                    "from qsys2.SYSTABLES\n" +
                    "   order by  SYSTEM_TABLE_SCHEMA asc"
            );
            /* 109 */       ResultSet rs =resultset;
            acum.add("EDMARCO561");
            /* 110 */       while (rs.next()) {
                /*     */         try {
                    /* 112 */           acum.add(rs.getString(1));
                    /* 113 */         } catch (SQLException ex) {
                    /* 114 */           Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, (String)null, ex);
                    /*     */         }
                /*     */       }
            /* 118 */     } catch (SQLException ex) {
            /* 119 */      pri.Mensagem("ERRO",""+ex.toString());
            /*     */     }
        /* 117 */       return acum;

        /*     */   }
    /*     */
    /*     */
    /*     */   public ArrayList retornaTabelas(String nomebacno) {
        /* 126 */     executeSQL("select *\n" +
                "from QSYS2.SYSTABLES\n" +
                "where TABLE_SCHEMA\n" +
                "like '"+nomebacno+"'\n" +
                "and TYPE = 'T'");
        /* 127 */     ArrayList<String> acum = new ArrayList<String>();
        /* 128 */     while (next()) {
            /*     */       try {
                /* 130 */         acum.add(this.resultset.getString(1));
                /* 131 */       } catch (SQLException ex) {
                /* 132 */ pri.Mensagem("ERRO",""+ex.toString());
                /* 133 */
                /*     */       }
            /*     */     }
        /*     */
        /* 137 */     return acum;
        /*     */   }
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

    /*     */
    /*     */
    /*     */   public ArrayList retornaColunasTabela(String tabela) {
        /* 142 */     executeSQL("select * from " + tabela+" limit 1");

        /* 143 */     ArrayList<String> acum = new ArrayList<String>();
        /* 144 */    try {
            /* 144 */     for (int cont=  1;cont<=resultset.getMetaData().getColumnCount();cont++) {
                /* 146 */         acum.add(this.resultset.
                        getMetaData().getColumnName(cont)+"   ");
                /* 147 */
                /*     */     }
        } catch (SQLException ex) {
            pri.Mensagem("ERRO",""+ex.toString());
        }
        return acum;
        /*     */   }
    /*     */
    /*     */


    ArrayList<String> cln=new ArrayList<String>();

    public ArrayList<String> getColunas(){

        return cln;

    };
    public void setColunas(ArrayList<String> colunas){
        cln=colunas;

    };

    /*     */
    /*     */   public ArrayList<Object[]> retornaTodaSql(String bancoc, String tabela) {
        /* 159 */     ArrayList tabelas = retornaColunasTabela(tabela);
   setColunas(tabelas);
        /* 161 */     int mx = tabelas.size();
        /*     */
        /* 163 */     executeSQL("select * from " + bancoc+"."+tabela);
        /*     */
        /* 165 */      acum = new ArrayList();
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
    /*     */
    /*     */   public int getNcolunas() {
        /*     */     try {
            /* 190 */       return this.resultset.getMetaData().getColumnCount();
            /* 191 */     } catch (SQLException ex) {
            /* 192 */       Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, (String)null, ex);
            /* 193 */       return 0;
            /*     */     }
        /*     */   }
    /*     */   public String getString(int cl) {
        /*     */     try {
            /* 198 */       return this.resultset.getString(cl);
            /* 199 */     } catch (SQLException ex) {
            /* 200 */       Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, (String)null, ex);
            /* 201 */       return null;
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */   public String getString(String cl) {
        /*     */     try {
            /* 208 */       return this.resultset.getString(cl);
            /* 209 */     } catch (SQLException ex) {
            /* 210 */       Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, (String)null, ex);
            /* 211 */       return null;
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */   public boolean next() {
        /*     */     try {
            /* 220 */       return this.resultset.next();
            /* 221 */     } catch (SQLException ex) {
            /* 222 */       Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, (String)null, ex);
            /* 223 */       return false;
            /*     */     }
        /*     */   }
}


 