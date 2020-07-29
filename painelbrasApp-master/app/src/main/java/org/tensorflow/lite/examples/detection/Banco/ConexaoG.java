package org.tensorflow.lite.examples.detection.Banco;

import android.app.Activity;


import org.tensorflow.lite.examples.detection.LoginActivity;

import java.io.Serializable;
import java.util.ArrayList;

public interface ConexaoG extends Serializable {


    public static String fontMysql="fontMysql";
    public static String fontPostGreeSql="fontPostGreeSql";
    public static String fontAS400="fontAS400";

    public boolean Conecta(String banco, String host, String porta, String usuarioe, String senhae, LoginActivity pri);
    public void desconecta();
    public   boolean executeUpdateL(String sql);
    public String executeUpdateS(String sql);
    public ArrayList retorna1SQL(String sql, String coluna);

    public ArrayList retornaNSQL(String sql, String[] coluna);

    public ArrayList retornaBancos();
  public ArrayList retornaTabelas(String nomebacno);

    public ArrayList<String> setBancos(String banco);

    public ArrayList<String> setTabelas(ArrayList<String> tabelas);

    public ArrayList<String> getBancos();

    public ArrayList<String> getTabelas();


   public ArrayList retornaColunasTabela(String tabela);
    public ArrayList<String> getColunas();
    public void setColunas(ArrayList<String> colunas);
    public String getErro();
    public ArrayList<Object[]> retornaTodaSql(String bancoc, String tabela);
    /*     */   public int getNcolunas();
    /*     */   public String getString(int cl) ;
    /*     */   public String getString(String cl) ;
    /*     */   public boolean next() ;

    public String getFonte();


    public String getTable();

    public String getBanco();


    public void setBanco(String banco);
     public void setTable(String table);

    public ArrayList<Object[]> retornaTodaSql();
    }
