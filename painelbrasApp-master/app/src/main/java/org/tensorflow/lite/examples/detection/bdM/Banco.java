package org.tensorflow.lite.examples.detection.bdM;




import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.detection.Banco.Conexao;
import org.tensorflow.lite.examples.detection.Biblis.Datas;
import org.tensorflow.lite.examples.detection.LoginActivity;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Banco implements Cloneable {

    public String uuid;
    public String mysql = "painelbrass";
    public String bdNomeL = "painelbrass";
    public String bdNome;
// public String host = "servertestes.ddns.net";
     public String host = "db4free.net";
    public String porta = "3306";
  String usuarioBanco = "painelbrass";
    //   String usuarioBanco = "Usuario";
    public String senha = "painelbrass";
    /*
     public String mysql="sirlenei_bdsirleneimoveis";
     public  String bdNomeL="sirlenei_bdsirleneimoveis";
     public String bdNome;
     public String host="localhost";
     public String porta="3306";
     String usuarioBanco="sirlenei_joao";
     public String senha="ferrari07H";
     */

    public Conexao banco = null;

    public static String div = ";q~p~;";

    public String groupBY = "";
    public boolean sum = false;
    public String tabsJOIN = "";
    public String LIMIT = "";
    public String Where = "";
    String Distinct = "";
    String asdes = " desc";

    StringBuffer li = new StringBuffer();
    StringBuffer wi = new StringBuffer();
    boolean wiE = false;
    boolean ordemespecifica = false;
    colunaT colunasOrdem[] = null;
    public LoginActivity pri=null;
    public boolean conectou=false;


    public Banco(LoginActivity pri,boolean exe) {
        bdNome = mysql;
        this.pri=pri;
        abreBanco();
        banco.executeUpdateL("create database " + bdNomeL);

        bdNome = bdNomeL;
        abreBanco();
        if (exe) {
            new montaTabela();
        }

    }

    public synchronized void fechaBanco() {

       // banco.desconecta();

    }

    public synchronized void abreBanco() {

        try {

            banco = new Conexao(host, porta, bdNome, usuarioBanco, senha,pri);
conectou=true;
        } catch (Exception erro) {
            pri.Mensagem("erro banco",erro.toString());
conectou=false;
        }

    }

    public static ArrayList<colunaT> tabelas = new ArrayList<colunaT>();
    public static ArrayList<String> tabelasN = new ArrayList<String>();

    public synchronized String getUuid() {
        banco.executeSQL("SELECT uuid();");

        try {
            banco.resultset.next();

            uuid = banco.resultset.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uuid;
    }

    public synchronized void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public synchronized String getNomePref(String tabela, String coluna) {

        for (int cont = 0; cont < tabelas.size(); cont++) {

            if (tabelas.get(cont).T().contentEquals(tabela)
                    && tabelas.get(cont).C().contentEquals(coluna)) {
                return tabelas.get(cont).nomePref;
            }
        }

        return coluna;

    }

    public synchronized String geraGetSet(String tabela) {
        String acum = "\n";

        for (int cont = 0; cont < tabelas.size(); cont++) {

            if (!tabelas.get(cont).T().contentEquals(tabela)) {
                continue;
            }

            acum += "public synchronized  string "
                    + tabelas.get(cont).C()
                    + " { get; set; }\n";

        }

        String resto = "   Tabela." + tabela + "V.Add(new Objetos." + tabela + "{";

        for (int cont = 0; cont < tabelas.size(); cont++) {

            if (!tabelas.get(cont).T().contentEquals(tabela)) {
                continue;
            }

            resto += tabelas.get(cont).C() + "=(o." + tabelas.get(cont).C() + "==null)?\"Null\":"
                    + "o." + tabelas.get(cont).C() + ".ToString()\n,";

        }
        resto = resto.substring(0, resto.length() - 2) + "}); ";

        String dados2 = " public synchronized  string Busca" + tabela + "()\n"
                + "        {\n"
                + "            var Tabela = new " + tabela + "C(Filial);\n"
                + "\n"
                + "            using (BDBONZAY bd = new BDBONZAY())\n"
                + "            {\n"
                + "\n"
                + "                var c0 = bd." + tabela + "Collection.GetAll();\n"
                + "\n"
                + "\n"
                + "                foreach (var o in c0)\n"
                + "                {\n"
                + "\n" + resto
                + "\n"
                + "\n"
                + "                }\n"
                + "            }\n"
                + "\n"
                + "            return ageitaJSON(Tabela.ObjectToJson());\n"
                + "\n"
                + "        }";
        String dados = "using Newtonsoft.Json;\n"
                + "using System;\n"
                + "using System.Collections.Generic;\n"
                + "using System.Linq;\n"
                + "using System.Web;\n"
                + "using Bonzay.BD;\n"
                + "using BonzayBO;\n"
                + "using BonzayBO.Fachada;\n"
                + "using BonzayBO.Fachada.Gerencial;\n"
                + "using BonzayBO.Tesouraria;\n"
                + "using System;\n"
                + "using System.Collections.Generic;\n"
                + "using System.Configuration;\n"
                + "using System.Data;\n"
                + "using System.IO;\n"
                + "using System.Linq;\n"
                + "using System.Text;\n"
                + "using System.Web;\n"
                + "using System.Web.Script.Serialization;\n"
                + "using System.Web.Script.Services;\n"
                + "using System.Web.Services;\n"
                + "using ClassesBanco.Objetos;\n"
                + "\n"
                + "namespace ClassesBanco.Objetos\nnamespace WebServiceConsultaFinanceira.Objetos\n\n{\n"
                + "    [Serializable]\n"
                + "    public synchronized  class " + tabela + "C\n"
                + "    {String Filial=\"\"; \n"
                + "        [JsonProperty(\"" + tabela + "\")]\n"
                + "        public synchronized  List<" + tabela + "> " + tabela + "V   { get; set; }\n"
                + "\n"
                + "        public synchronized  " + tabela + "C(String Filial)\n"
                + "        { this.Filial=Filial;\n"
                + "            " + tabela + "V = new List<" + tabela + ">();\n"
                + "        }\n" + dados2
                + "      public synchronized  string ageitaJSON(string json)"
                + " {"
                + "   return json.Remove(json.Length - 1).Substring(1);"
                + " }}\n"
                + "    [Serializable]\n"
                + "    public synchronized  class " + tabela + "\n"
                + "\n"
                + "    {\n" + acum
                + "\n"
                + "\n"
                + "\n"
                + "    }\n"
                + "}"
                + ""
                + "\n\n"
                + ""
                + "";
        return tabela + div + dados;

    }

    public synchronized String JSONLinhaIdem(Linhas l) {
        JSONObject jt = new JSONObject();

        JSONObject jt2 = new JSONObject();

        JSONObject jt3 = new JSONObject();

        while (l.next()) {

            for (int cont = 0; cont < l.numcoluna(); cont++) {

                try {
                    jt.put(l.getC(cont), l.getS(l.colunasrealT[cont],l.getC( cont)));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            try {
                jt2.accumulate(l.table, jt);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        try {
            jt2.accumulate(l.table, jt3);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jt2.toString();
    }

    public synchronized String JSONLinha(Linhas l) {
        JSONObject jt = null;

        JSONObject jt2 = new JSONObject();

        JSONObject jt3 = new JSONObject();

        while (l.next()) {
            jt = new JSONObject();
            for (int cont = 0; cont < l.numcoluna(); cont++) {

                try {
                    jt.put(l.getC(cont), l.getS(l.colunasrealT[cont],l.getC(cont)));
                    //Log.d("JSONLINHA", "JSONLINHA "+l.getC(cont)+" "+l.getS(l.getC(cont)));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            try {
                jt2.accumulate("" + l.table, jt);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        try {
            jt2.accumulate("" + l.table, jt3);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jt2.toString();
    }

    public synchronized JSONObject JSONLinhaLimpo(Linhas l) {
        JSONObject jt = null;

        JSONObject jt2 = new JSONObject();

        while (l.next()) {
            jt = new JSONObject();
            for (int cont = 0; cont < l.numcoluna(); cont++) {

                try {
                    jt.put(l.getC(cont), l.getS(l.colunasrealT[cont],l.getC(cont)));
                    //Log.d("JSONLINHA", "JSONLINHA "+l.getC(cont)+" "+l.getS(l.getC(cont)));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            try {
                jt2.accumulate("" + l.table, jt);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
               // return new JSONObject("{erro:'" + e + "'}");
            }

        }

        return jt2;
    }

    public synchronized JSONObject JSONTabela(String tabela) {
        JSONObject jt = new JSONObject();

        JSONObject jt2 = new JSONObject();

        Linhas l = sls(tabela);

        while (l.next()) {

            for (int cont = 0; cont < l.numcoluna(); cont++) {

                try {
                    jt.put(l.getC(cont), l.getS(l.colunasrealT[cont],l.getC(cont)));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            try {
                jt2.accumulate(tabela, jt);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return jt2;
    }

    public synchronized void inseridadosTudo(String value, String data) {

        for (int tc = 0; tc < tabelasN.size(); tc++) {
            clear(tabelasN.get(tc));
            for (int cont = 0; cont < Banco.tabelas.size(); cont++) {

                if (Banco.tabelas.get(cont).T().contentEquals(tabelasN.get(tc))) {

                    if (Banco.tabelas.get(cont).tipo.contentEquals(BancoT.date)) {
                        Banco.tabelas.get(cont).Dado(data);
                    } else {
                        Banco.tabelas.get(cont).Dado(value);
                    }

                }

            }

            insert(lI(tabelasN.get(tc)));

        }

    }

    public synchronized void inseridadosTudoB(String value) {

        for (int tc = 0; tc < tabelasN.size(); tc++) {
            clear(tabelasN.get(tc));
            for (int cont = 0; cont < Banco.tabelas.size(); cont++) {

                if (Banco.tabelas.get(cont).T().contentEquals(tabelasN.get(tc))) {

                    if (Banco.tabelas.get(cont).tipo.contentEquals(BancoT.date)) {
                        Banco.tabelas.get(cont).Dado(new Datas().data());
                    } else {
                        Banco.tabelas.get(cont).Dado(value);
                    }

                }

            }
            dW(lI(tabelasN.get(tc)));
            insert(lI(tabelasN.get(tc)));

        }

    }
    public synchronized void limpaBanco() {
        String acum = "";

        for (int tc = 0; tc < tabelasN.size(); tc++) {

            d(l(tabelasN.get(tc)));

        }

    }
    public synchronized void LimpaBanco() {
        String acum = "";

        for (int tc = 0; tc < tabelasN.size(); tc++) {

            d(l(tabelasN.get(tc)));

        }

    }

    public synchronized String ExibeSize() {
        String acum = "";
        ArrayList<String> li = new ArrayList<String>(tabelasN);

        Collections.sort(li);
        for (int tc = 0; tc < li.size(); tc++) {

            acum += li.get(tc) + ": " + sls(li.get(tc)).numlinha() + "\n";

        }

        return acum;

    }

    public synchronized void setOrderEsp(colunaT... colunas) {
        ordemespecifica = true;
        colunasOrdem = colunas;
    }

    public synchronized String getOrdem() {
        ordemespecifica = false;
        StringBuffer ordem = new StringBuffer();
        for (int cont = 0; cont < colunasOrdem.length; cont++) {

            if (cont + 1 == colunasOrdem.length) {
                ordem.append(colunasOrdem[cont].C() + " " + asdes + " ");
            } else {
                ordem.append(colunasOrdem[cont].C() + " " + asdes + " ,");
            }

        }

        return ordem.toString();
    }

    public synchronized void inseridadosTudoB(String tabela, String value) {

        for (int tc = 0; tc < tabelasN.size(); tc++) {
            clear(tabelasN.get(tc));
            for (int cont = 0; cont < Banco.tabelas.size(); cont++) {

                if (tabela.contentEquals(Banco.tabelas.get(cont).T()) && Banco.tabelas.get(cont).T().contentEquals(tabelasN.get(tc))) {

                    if (Banco.tabelas.get(cont).tipo.contentEquals(BancoT.date)) {
                        Banco.tabelas.get(cont).Dado(new Datas().data());
                    } else {
                        Banco.tabelas.get(cont).Dado(value);
                    }

                }

            }
            dW(lI(tabelasN.get(tc)));
            insert(lI(tabelasN.get(tc)));

        }

    }

    public synchronized double Max(colunaT colunas) {
        setOrderAsc();
        Linhas linha = sl(colunas);
        double max = 0;

        while (linha.next()) {

            if (linha.getD(colunas,0) > max) {
                max = linha.getD(colunas,0);
            }

        }

        return max;

    }

    public synchronized void clear(String tabela) {

        ArrayList<colunaT> ar = new ArrayList<colunaT>();
        for (int cont = 0; cont < Banco.tabelas.size(); cont++) {

            if (Banco.tabelas.get(cont).T().contentEquals(tabela)) {

                Banco.tabelas.get(cont).Dado("");
            }
        }
    }

    public synchronized void insertCI(String tabela) {
        insert(lI(tabela));

        colunaT[] colunas = l(tabela);

        clear(colunas[0].tabela);

    }

    public synchronized void insertC(String tabela) {
        colunaT[] colunas = l(tabela);

        insert(colunas);
        clear(colunas[0].tabela);

    }

    public synchronized void insertDW(colunaT... colunas) {

        dW(colunas);
        insert(colunas);
    }

    public synchronized void insertDelete(colunaT... colunas) {

        d(colunas);
        insert(colunas);
    }

    public synchronized void insertD(colunaT... colunas) {

        if (slWU(colunas).size() == 0) {
            insert(colunas);
        }
    }

    public synchronized void insertUp(List<colunaT> l, colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + l.get(cont).C() + "= '" + l.get(cont).D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        String query = "update  " + colunas[0].T() + " set ";

        for (int cont = 0; cont < colunas.length; cont++) {

            query += colunas[cont].C() + "=" + " ? ,";

        }
        query = query.substring(0, query.length() - 1) + " " + Where;

// pri.Mensagem("", query);
        PreparedStatement stmt;
        try {
            stmt = banco.Conexao.prepareStatement(query);

            // pri.escreveTexto2(query);
            for (int cont = 0; cont < colunas.length; cont++) {

                if (colunas[cont].tipo.contentEquals(BancoT.blob)) {

                    stmt.setBytes(cont + 1, (byte[]) (colunas[cont].D()));
                } else {
                    stmt.setString(cont + 1, colunas[cont].D() + "");
                }

            }
            Where = "";
            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
        //banco.execSQL(query);
        //banco.execSQL(query);

    }

    public synchronized void insertUpM(List<colunaT> l, colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + l.get(cont).C() + " < '" + l.get(cont).D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        String query = "update  " + colunas[0].T() + " set ";

        for (int cont = 0; cont < colunas.length; cont++) {

            query += colunas[cont].C() + "=" + " ? ,";

        }
        query = query.substring(0, query.length() - 1) + " " + Where;

// pri.Mensagem("", query);
        PreparedStatement stmt;
        try {
            stmt = banco.Conexao.prepareStatement(query);

            // pri.escreveTexto2(query);
            for (int cont = 0; cont < colunas.length; cont++) {

                stmt.setString(cont + 1, colunas[cont].D() + "");

            }
            Where = "";
            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
        //banco.execSQL(query);
        //banco.execSQL(query);

    }

    public synchronized String insert(colunaT... colunas) {

        String query = "insert into " + colunas[0].T() + " (";
        for (int cont = 0; cont < colunas.length; cont++) {

            query += colunas[cont].C() + ",";

        }
        query = query.substring(0, query.length() - 1) + ") values (";

        for (int cont = 0; cont < colunas.length; cont++) {

            query += "?,";

        }

        query = query.substring(0, query.length() - 1) + ") ";
        PreparedStatement stmt;
        try {
            stmt = banco.Conexao.prepareStatement(query);

            for (int cont = 0; cont < colunas.length; cont++) {

                // Log.d("Inserido "+colunas[cont].C(),"Inserido "+colunas[cont].D());
                if (colunas[cont].tipo.contentEquals(BancoT.blob)) {

                    stmt.setBytes(cont + 1, (byte[]) (colunas[cont].D()));
                } else if (colunas[cont].tipo.contentEquals(BancoT.real)) {

                    stmt.setDouble(cont + 1, Double.parseDouble(colunas[cont].DD()));
                } else {
                    stmt.setString(cont + 1, colunas[cont].D() + "");
                }

            }

            stmt.execute();
            stmt.close();
//banco.execSQL(query);
        } catch (SQLException e) {

            System.out.println(e.toString());
            return e + "";
        }
        return "OK";
    }

    public synchronized Linhas slsWO(colunaT ...colunas){
        String acum="";
        for(int cont=0;cont<colunas.length;cont++){

            acum+=" "+colunas[cont].C()+"= '"+colunas[cont].D()+"'  or";

        }
        Where="where "+acum.substring(0, acum.length()-3);
        return sl(l(colunas[0].T()));

    }


    public   Linhas slsWOC(int c,colunaT ...colunas){
        String acum=" (";
        for(int cont=0;cont<colunas.length;cont++){

            acum+=" "+colunas[cont].C()+"= '"+colunas[cont].D()+"'  "+(cont<c?" or ":cont==c?" ) and":" and");

        }

        Where="where "+acum.substring(0, acum.length()-3);
        //Log.d("tetessMeu","tetessMeu "+Where);
        return sl(l(colunas[0].T()));

    }


    public colunaT[] lNull(String tabela){


        ArrayList<colunaT> ar=new ArrayList<colunaT>();
        for(int cont=0;cont<Banco.tabelas.size();cont++){

            if(Banco.tabelas.get(cont).T().contentEquals(tabela)){
                ar.add(Banco.tabelas.get(cont));
            }

        }

        colunaT[] arT=new colunaT[ar.size()];
        for(int cont=0;cont<ar.size();cont++){
            ar.get(cont).Dado(null);
            arT[cont]=ar.get(cont);

        }


        return arT;
    }


    class montaJOIN {

        StringBuffer li = new StringBuffer();

        public synchronized String getJOIN() {

            return li.toString();
        }






        public synchronized Linhas slsWO(colunaT ...colunas){
            String acum="";
            for(int cont=0;cont<colunas.length;cont++){

                acum+=" "+colunas[cont].C()+"= '"+colunas[cont].D()+"'  or";

            }
            Where="where "+acum.substring(0, acum.length()-3);
            return sl(l(colunas[0].T()));

        }
        public synchronized void setJOIN(colunaT... colunas) {

            String INNERJOINN = "";
            if (colunas.length % 2 != 0) {
                return;
            }

            INNERJOINN = "INNER JOIN " + colunas[1].T() + " ON (";
            if (!tabsJOIN.contains(colunas[0].T())) {
                tabsJOIN += colunas[0].T() + " ";
            }
            String acum = "";
            for (int cont = 0; cont < colunas.length; cont += 2) {

                acum += "  " + colunas[cont].CT() + " = " + colunas[cont + 1].CT() + " and";

            }

            INNERJOINN += acum.substring(0, acum.length() - 3) + ") \n";
            li.append(INNERJOINN);
        }
    }

    public synchronized void setJOIN(colunaT... colunas) {

        String INNERJOINN = "";
        if (colunas.length % 2 != 0) {
            return;
        }

        INNERJOINN = "INNER JOIN " + colunas[1].T() + " ON (";
        if (!tabsJOIN.contains(colunas[0].T())) {
            tabsJOIN += colunas[0].T() + " ";
        }
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont += 2) {

            acum += "  " + colunas[cont].CT() + " = " + colunas[cont + 1].CT() + " and";

        }

        INNERJOINN += acum.substring(0, acum.length() - 3) + ") \n";
        li.append(INNERJOINN);
    }
    public synchronized String checaValores(String msg, colunaT ...colunas){

        String acum="";
        for(int cont=0;cont<colunas.length;cont++){

            if(colunas[cont].D()==null){


                return msg + colunas[cont].CPref();
            }


        }



        return null;


    }
    public synchronized void setLimit(int limit) {
        LIMIT = " LIMIT " + limit;

    }

    public synchronized void setGroupBY(colunaT... colunas) {

        for (int cont = 0; cont < colunas.length; cont += 2) {

            groupBY += "  " + colunas[cont].CT() + ",";

        }
        groupBY = "group by " + groupBY.substring(0, groupBY.length() - 1);

    }

    public synchronized void setWR(colunaT... colunas) {

        if (colunas.length % 2 != 0) {
            return;
        }

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont += 2) {

            acum += " ( " + colunas[cont].CT() + " = " + colunas[cont + 1].CT() + ") and";

        }

        wi.append(acum.substring(0, acum.length() - 3));
        wiE = true;
    }

    public synchronized void Distinct() {
        Distinct = " distinct ";

    }

    public synchronized void setDistinct() {
        Distinct = " distinct ";

    }

    public synchronized void setOrderAsc() {

        asdes = " asc";

    }

    public synchronized void setOrderDes() {

        asdes = " desc";

    }

    public synchronized Linhas slsWUnico(colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + "= '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        Linhas l = sl(l(colunas[0].T()));
        insertUp(ls(colunas[0]), l(colunas[0].T()));
        return l;
    }

    public synchronized Linhas slsWD(colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + "= '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);
        return sl(l(colunas[0].T()));

    }

    public synchronized Linhas slsWAndOr(int orlike, int or, colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < orlike; cont++) {

            acum += " " + colunas[cont].C() + " like '%" + colunas[cont].D() + "%'" + " and";

        }

        for (int cont = orlike; cont < or; cont++) {

            acum += " " + colunas[cont].C() + "= '" + colunas[cont].D() + "'" + " and";

        }
        acum += "(";
        for (int cont = or; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + " = '" + colunas[cont].D() + "'" + "  or";

        }
        Where = "where " + acum.substring(0, acum.length() - 3) + ")";

        return sl(l(colunas[0].T()));

    }

    public synchronized Linhas slsWLA(colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + " like '%" + colunas[cont].D() + "%'  and";

        }

        Where = "where " + acum.substring(0, acum.length() - 3);
        return sl(l(colunas[0].T()));

    }

    public synchronized Linhas slsWLO(colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + " like '%" + colunas[cont].D() + "%'  or";

        }

        Where = "where " + acum.substring(0, acum.length() - 3);
        return sl(l(colunas[0].T()));

    }

    public synchronized Linhas slsWL1(colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + " like '%" + colunas[cont].D() + "%' and";
            break;
        }

        for (int cont = 1; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + "= '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);
        return sl(l(colunas[0].T()));

    }

    public synchronized Linhas slsW(colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + "= '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        return sl(l(colunas[0].T()));

    }

    public synchronized Linhas slsWE(colunaT... colunas) {
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].C() + " " + colunas[cont].condi + "  '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);
        //JOptionPane.showMessageDialog(null, Where);
        return sl(l(colunas[0].T()));

    }

    public synchronized String slsWCondiS(colunaT... colunas) {
        String acum = "";
        boolean entrou = false;
        for (int cont = 0; cont < colunas.length; cont++) {
            if (!colunas[cont].S().contains("null") && colunas[cont].S().length() > 0) {
                acum += " " + colunas[cont].C() + " " + colunas[cont].condi + "  '" + colunas[cont].D() + "' and";
                entrou = true;
            }

        }
        if (entrou) {
            Where = "where " + acum.substring(0, acum.length() - 3);
        }
        return Where;

    }

    public synchronized String slsWCondiSTodosString(colunaT... colunas) {
        String acum = "";
        boolean entrou = false;
        for (int cont = 0; cont < colunas.length; cont++) {
            if (!colunas[cont].S().contains("todos") && colunas[cont].S().length() > 0) {
                acum += " " + colunas[cont].C() + " " + colunas[cont].condi + "  '" + colunas[cont].D() + "' and";
                entrou = true;
            }

        }
        if (entrou) {
            Where = "where " + acum.substring(0, acum.length() - 3);
        }

        return Where;

    }

    public synchronized Linhas slsWCondiSTodos(colunaT... colunas) {
        String acum = "";
        boolean entrou = false;
        for (int cont = 0; cont < colunas.length; cont++) {
            if (!colunas[cont].S().contains("todos") && colunas[cont].S().length() > 0) {
                acum += " " + colunas[cont].C() + " " + colunas[cont].condi + "  '" + colunas[cont].D() + "' and";
                entrou = true;
            }

        }
        if (entrou) {
            Where = "where " + acum.substring(0, acum.length() - 3);
        }
        return sl(l(colunas[0].T()));

    }

    public synchronized Linhas slsWCondi(colunaT... colunas) {
        String acum = "";
        boolean entrou = false;
        for (int cont = 0; cont < colunas.length; cont++) {
            if (!colunas[cont].S().contains("null") && colunas[cont].S().length() > 0) {
                acum += " " + colunas[cont].C() + " " + colunas[cont].condi + "  '" + colunas[cont].D() + "' and";
                entrou = true;
            }

        }
        if (entrou) {
            Where = "where " + acum.substring(0, acum.length() - 3);
        }
        return sl(l(colunas[0].T()));

    }

    public synchronized Linhas sls(String tabela) {

        return sl(l(tabela));

    }

    public synchronized Linhas sl(String query, String tabs) {
        Linhas linha = new Linhas();
        try {
            linha.add(banco.Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY), query, tabs);
        } catch (SQLException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linha;
    }

    public synchronized void setSum() {
        sum = true;

    }

    public synchronized Linhas sl(colunaT... colunas) {
        Linhas linha = new Linhas();

        String query = " ";
        String order = "";
        String tabs = "";
        boolean tb1 = true;
        for (int cont = 0; cont < colunas.length; cont++) {
            String tabsv[] = tabs.split(",");
            boolean vt = true;
            for (int t = 0; t < tabsv.length; t++) {
                if (tabsv[t].contentEquals(colunas[cont].T())) {
                    vt = false;
                }
            }
            if (vt) {
                if (tb1) {
                    tb1 = false;
                    tabs += "" + colunas[cont].T();
                } else {
                    tabs += "," + colunas[cont].T();
                }
            }

            if (sum && colunas[cont].tipo.contentEquals(BancoT.real)) {

                query += "sum(" + colunas[cont].CT() + ") AS "
                        + colunas[cont].T() + colunas[cont].C() + " ,";
                colunas[cont].setSoma(true);

            } else {
                query += colunas[cont].CT() + ",";
            }

        }

        String mQ = query.substring(0, query.length() - 1);
        String mQO = mQ.replaceAll(",", asdes + ",") + asdes;
        String consultaPL = "select " + Distinct
                + " " + mQ + " from " + ((li.length() > 0) ? tabsJOIN : tabs) + " " + li.toString() + " " + Where + " "
                + ((wiE && Where.length() == 0) ? "where " : "") + wi.toString() + " " + groupBY + " " + div + " " + LIMIT;

        query = "select " + Distinct
                + " " + mQ + " from " + ((li.length() > 0) ? tabsJOIN : tabs) + " " + li.toString() + " " + Where + " "
                + ((wiE && Where.length() == 0) ? "where " : "") + wi.toString() + " " + groupBY + " order by "
                + ((ordemespecifica) ? getOrdem() : mQO)
                + "  " + LIMIT;
        // System.out.println(" "+query);
        // System.out.println(" "+consultaPL);
        //pri.escreveTexto2(query);
        Where = "";
        ordemespecifica = false;
        Distinct = "";
        LIMIT = "";
        tabsJOIN = "";
        groupBY = "";
        sum = false;
        wiE = false;
        li.delete(0, li.length());
        wi.delete(0, wi.length());
        //li.add("");
        setOrderDes();

        /*
         Cursor cursorOB=Banco.pri.banco.rawQuery("Select count("+colunas[0].C()+") from "+colunas[0].T()+"", null);
         if(cursorOB.getCount()>0){
         cursorOB.moveToFirst();
         ob=new Object[(int) cursorOB.getDouble(0)];
		
         }
         cursorOB.close();*/
        //pri.escreveTexto2(query);
        //Log.d("Errata ","Errata "+query);
        try {
            /* pri.startManagingCursor(cursor);
             Cursor cursor2=banco.rawQuery(query, null);
             pri.startManagingCursor(cursor2);*/
            linha.add(banco.Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY), this, tabs, colunas, query, consultaPL);
        } catch (SQLException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }

        return linha;

    }

    public synchronized Linhas slWE(List<colunaT> l, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + colunas[cont].C() + " " + colunas[cont].condi + "  '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        return sl(colunas);

    }

    public synchronized Linhas slW(List<colunaT> l, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + l.get(cont).CT() + "= '" + l.get(cont).D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        return sl(colunas);

    }

    public synchronized Linhas slW(String tabela, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].CT() + "= '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        return sl(l(tabela));

    }

    public synchronized Linhas slWI(List<colunaT> l, montaJOIN mj, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + l.get(cont).T() + "." + l.get(cont).CT() + "='" + l.get(cont).D() + "' and";

        }
        Where = mj.getJOIN() + " where " + acum.substring(0, acum.length() - 3);

        return sl(colunas);

    }

    public synchronized Linhas slT(List<colunaT> l, colunaT... colunas) {
//Data Inicial vem primeiro
        boolean inicio = true;
        boolean edata = false;
        String btw = "";
        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            String cd = "=";
            String ct = "and";
            String cp1 = "";
            String cp2 = "";

            if (inicio && l.get(cont).tipo.contentEquals(BancoT.date)) {

                btw = "BETWEEN";
                inicio = false;
            } else if (l.get(cont).tipo.contentEquals(BancoT.date)) {
                edata = true;

            }

            if (l.get(cont).mtipo == l.get(cont).like) {
                cd = " like ";
                cp1 = "%";
                cp2 = "%";
            } else if (l.get(cont).mtipo == l.get(cont).like1) {

                cd = " like ";

                cp2 = "%";

            } else if (l.get(cont).mtipo == l.get(cont).maiorigual) {

                cd = " >= ";

            } else if (l.get(cont).mtipo == l.get(cont).menorigual) {

                cd = " <= ";

            }

            if (l.get(cont).ct == l.get(cont).or) {

                ct = "or";

            }

            if (edata) {
                acum += " '" + cp1 + l.get(cont).D() + cp2 + "' " + ct + "";

            } else {
                acum += " " + l.get(cont).CT() + " " + ((btw.length() > 0) ? btw : cd) + " '"
                        + cp1 + l.get(cont).D() + cp2 + "' " + ct + "";
            }

            btw = "";
            edata = false;
        }
        Where = "where " + acum.substring(0, acum.length() - 3);
        //pri.Mensagem("", Where);
        //pri.escreveTexto2(Where);
        return sl(colunas);

    }

    public synchronized Linhas slWUU(colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].CT() + "= '" + colunas[cont].D() + "' and";
            break;
        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        return sl(colunas);

    }

    public synchronized Linhas slWU(colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].CT() + "= '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);
        // System.out.println(""+Where);
        return sl(colunas);

    }

    public synchronized Linhas slWL(List<colunaT> l, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + l.get(cont).CT() + " like '%" + l.get(cont).D() + "%' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        return sl(colunas);

    }

    public synchronized Linhas slWL1(List<colunaT> l, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + l.get(cont).CT() + " like '" + l.get(cont).D() + "%' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        return sl(colunas);

    }

    public synchronized Linhas slWLO(List<colunaT> l, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + l.get(cont).CT() + " like '%" + l.get(cont).D() + "%' or";

        }
        Where = "where " + acum.substring(0, acum.length() - 2);

        return sl(colunas);

    }

    public synchronized Linhas slWLO1(List<colunaT> l, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < l.size(); cont++) {

            acum += " " + l.get(cont).CT() + " like '" + l.get(cont).D() + "%' or";

        }
        Where = "where " + acum.substring(0, acum.length() - 2);

        return sl(colunas);

    }

    public synchronized void dlT(colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {
            String cd = "=";
            String ct = "and";
            String cp1 = "";
            String cp2 = "";
            if (colunas[cont].mtipo == colunas[cont].like) {
                cd = " like ";
                cp1 = "%";
                cp2 = "%";
            } else if (colunas[cont].mtipo == colunas[cont].like1) {

                cd = " like ";

                cp2 = "%";

            }

            if (colunas[cont].ct == colunas[cont].or) {

                ct = "or";

            }

            acum += " " + colunas[cont].C() + " " + cd + " '" + cp1 + colunas[cont].D() + cp2 + "' " + ct + "";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        d(colunas);

    }

    public synchronized void d(colunaT... colunas) {
        String query = " ";

        query = "delete from " + colunas[0].T() + " " + Where + " ";
        //Log.d("query", "query "+query);
        Where = "";
        Distinct = "";
        banco.executeUpdateL(query);

    }

    public synchronized void dWEx(List<colunaT> ex, colunaT... colunas) {
        boolean pula = false;
        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            pula = false;
            for (int ce = 0; ce < ex.size(); ce++) {
                if (ex.get(ce).CT().contentEquals(colunas[cont].CT())) {
                    pula = true;
                }
            }
            if (pula) {
                continue;
            }
            acum += " " + colunas[cont].CT() + " = '" + colunas[cont].D() + "' and";
        }

        Where = "where " + acum.substring(0, acum.length() - 3);
        d(colunas);

    }

    public synchronized void dWEx(colunaT ex, colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            if (ex.C().contentEquals(colunas[cont].C())
                    && ex.T().contentEquals(colunas[cont].T())) {
                continue;
            }

            acum += " " + colunas[cont].CT() + " = '" + colunas[cont].D() + "' and";
        }

        Where = "where " + acum.substring(0, acum.length() - 3);
        d(colunas);

    }

    public synchronized void dW(colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].CT() + " = '" + colunas[cont].D() + "' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        d(colunas);

    }

    public synchronized void dWL(colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].CT() + " like '%" + colunas[cont].D() + "%' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        d(colunas);

    }

    public synchronized void dWL1(colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].CT() + " like '" + colunas[cont].D() + "%' and";

        }
        Where = "where " + acum.substring(0, acum.length() - 3);

        d(colunas);

    }

    public synchronized void dWLO(colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].CT() + " like '%" + colunas[cont].D() + "%' or";

        }
        Where = "where " + acum.substring(0, acum.length() - 2);

        d(colunas);

    }

    public synchronized void dWLO1(colunaT... colunas) {

        String acum = "";
        for (int cont = 0; cont < colunas.length; cont++) {

            acum += " " + colunas[cont].CT() + " like '" + colunas[cont].D() + "%' or";

        }
        Where = "where " + acum.substring(0, acum.length() - 2);

        d(colunas);

    }

    public synchronized List ls(colunaT... colunas) {
        return Arrays.asList(colunas);
    }

    public synchronized colunaT[] l(String tabela) {

        ArrayList<colunaT> ar = new ArrayList<colunaT>();
        for (int cont = 0; cont < Banco.tabelas.size(); cont++) {

            if (Banco.tabelas.get(cont).T().contentEquals(tabela)) {
                ar.add(Banco.tabelas.get(cont));
            }

        }

        colunaT[] arT = new colunaT[ar.size()];
        for (int cont = 0; cont < ar.size(); cont++) {

            arT[cont] = ar.get(cont);

        }

        return arT;
    }

    public synchronized colunaT[] lISinc(String tabela) {

        ArrayList<colunaT> ar = new ArrayList<colunaT>();
        for (int cont = 0; cont < Banco.tabelas.size(); cont++) {

            if (Banco.tabelas.get(cont).T().contentEquals(tabela)) {
                if (!Banco.tabelas.get(cont).autoincrement && Banco.tabelas.get(cont).sinc) {
                    ar.add(Banco.tabelas.get(cont));
                }

            }

        }

        colunaT[] arT = new colunaT[ar.size()];
        for (int cont = 0; cont < ar.size(); cont++) {

            arT[cont] = ar.get(cont);

        }

        return arT;
    }

    public synchronized colunaT[] lI(String tabela) {

        ArrayList<colunaT> ar = new ArrayList<colunaT>();
        for (int cont = 0; cont < Banco.tabelas.size(); cont++) {

            if (Banco.tabelas.get(cont).T().contentEquals(tabela)) {
                if (!Banco.tabelas.get(cont).autoincrement) {
                    ar.add(Banco.tabelas.get(cont));
                }

            }

        }

        colunaT[] arT = new colunaT[ar.size()];
        for (int cont = 0; cont < ar.size(); cont++) {

            arT[cont] = ar.get(cont);

        }

        return arT;
    }

    class montaTabela {

        String div = Banco.div;
        StringBuffer buf = new StringBuffer();
        ;
	String monta_tabela[];

        public montaTabela() {

            for (int cont = 0; cont < Banco.tabelas.size(); cont++) {
                System.gc();
                if (buf.length() == 0) {

                    buf.append(Banco.tabelas.get(cont).T() + "");
                } else if (!buf.toString().contains(Banco.tabelas.get(cont).T())) {
                    //	Log.d("erro3", stb.toString()+" erro3 "+Banco.tabelas.get(cont).T()+"");
                    buf.append(div + Banco.tabelas.get(cont).T());
                }
            }

            monta_tabela = new String[buf.toString().split(div).length];

            for (int contexe = 0; contexe < buf.toString().split(div).length; contexe++) {
                String table = buf.toString().split(div)[contexe];
                boolean temJa = true;
                for (int cn = 0; cn < tabelasN.size(); cn++) {
                    if (tabelasN.get(cn).contentEquals(table)) {
                        temJa = false;
                        break;
                    }

                }

                if (temJa) {
                    tabelasN.add(table);

                }

                String exec = "create table if not exists " + table + " (";
                String pselect = "";
                String pcreate = "";

                for (int cont = 0; cont < Banco.tabelas.size(); cont++) {

                    if (Banco.tabelas.get(cont).T().contentEquals(table)) {

                        if (Banco.tabelas.get(cont).E()) {

                            exec += "" + Banco.tabelas.get(cont).C() + " " + Banco.tabelas.get(cont).P() + ",";

                            pselect += ("" + Banco.tabelas.get(cont).C() + ",");

                            pcreate += ("" + Banco.tabelas.get(cont).C() + " " + Banco.tabelas.get(cont).P() + ",");
                        }
                    }
                }

                pselect = pselect.substring(0, pselect.length() - 1);

                pcreate = "(" + pcreate.substring(0, pcreate.length() - 1) + ")";

                banco.executeUpdateL(exec.substring(0, exec.length() - 1) + ")");
                boolean drop = false;
                for (int cont = 0; cont < Banco.tabelas.size(); cont++) {
                    if (Banco.tabelas.get(cont).T().contentEquals(table)) {
                        Banco.tabelas.get(cont).pselect = pselect;
                        Banco.tabelas.get(cont).pcreate = pcreate;

                        if (Banco.tabelas.get(cont).E()) {
                            if (!existeColuna(Banco.tabelas.get(cont).T(), Banco.tabelas.get(cont).C())) {
                                banco.executeUpdateL("ALTER TABLE " + Banco.tabelas.get(cont).T() + " "
                                        + "ADD COLUMN " + Banco.tabelas.get(cont).C() + " " + Banco.tabelas.get(cont).P() + "");

                            }
                        } else {

                            if (existeColuna(Banco.tabelas.get(cont).T(), Banco.tabelas.get(cont).C())) {//ALTER TABLE table_name  DROP COLUMN column_name;
						/*Banco.pri.banco.execSQL("ALTER TABLE "+Banco.tabelas.get(cont).T()+" "
                                 + " DROP COLUMN "+Banco.tabelas.get(cont).C()+" ");*/

                                drop = true;
                            }

                        }

                    }
                }

                if (drop) {

                    banco.executeUpdateL("create table if not exists temporario_" + table + " " + pcreate);
                    banco.executeUpdateL("insert into temporario_" + table + " select " + pselect + "  from " + table);
                    banco.executeUpdateL("drop table " + table + " ");
                    banco.executeUpdateL("create table " + table + " " + pcreate);
                    banco.executeUpdateL("insert into " + table + " select " + pselect + " from  temporario_" + table);
                    banco.executeUpdateL("drop table temporario_" + table + " ");

                    /*
                     BEGIN TRANSACTION;
                     CREATE TEMPORARY TABLE t1_backup(a,b);
                     INSERT INTO t1_backup SELECT a,b FROM t1;
                     DROP TABLE t1;
                     CREATE TABLE t1(a,b);
                     INSERT INTO t1 SELECT a,b FROM t1_backup;
                     DROP TABLE t1_backup;
                     COMMIT;*/
                }

            }

            buf.setLength(0);
            buf = null;
            System.gc();

        }

        private boolean existeColuna(String tabela, String coluna) {
            try {
                banco.executeSQL("SELECT * FROM " + tabela + " LIMIT 1");

                DatabaseMetaData md = banco.Conexao.getMetaData();
                ResultSet rs = md.getColumns(null, null, tabela, coluna);
                if (rs.next()) {
                    return true;
                }

                return false;

            } catch (Exception Exp) {
                return false;
            }
        }

    }

//    public synchronized Banco clone() {
//        try {
//
//            return (Banco) super.clone();
//
//        } catch (CloneNotSupportedException e) {
//
//            e.printStackTrace();
//            return null;
//        }
//    }
}
