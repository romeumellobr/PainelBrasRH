package org.tensorflow.lite.examples.detection.bdM;


import org.tensorflow.lite.examples.detection.LoginActivity;
import org.tensorflow.lite.examples.detection.OpIO.Recognition;

public class BancoT extends Banco implements Cloneable {

    public static String getvarchar(int num) {

        return " varchar(" + num + ")";

    }

    public BancoT(LoginActivity pri, boolean exe) {
        super(pri,exe);
    }

//    public BancoT clone() {
//
//        return (BancoT) super.clone()   ;
//
//    }
    public static String blob = "longblob";
    public static String varchar = "varchar(250)";
    public static String integer = "integer";
    public static String numero = "float";
    public static String real = "float ";
    public static String auto_increment = "INTEGER PRIMARY KEY AUTO_INCREMENT";
    public static String date = "date";
    public static String datetime = "datetime";
    public static String text = "text";
    //---------


    public static String reconhecimento="reconhecimento";
    public static colunaT reconhecimento_id=new colunaT("reconhecimento_id",
            reconhecimento,varchar,true,"Id");
    public static colunaT reconhecimento_title=new colunaT("reconhecimento_title",
            reconhecimento,varchar,true,"Nome");
    public static colunaT reconhecimento_distance=new colunaT("reconhecimento_distance2",
            reconhecimento,varchar,true,"Distance");
    public static colunaT reconhecimento_location_x=new colunaT("reconhecimento_location_x",
            reconhecimento,varchar,true,"Location");
    public static colunaT reconhecimento_location_y=new colunaT("reconhecimento_location_y",
            reconhecimento,varchar,true,"Location");
    public static colunaT reconhecimento_location_width=new colunaT("reconhecimento_location_width",
            reconhecimento,varchar,true,"Location");
    public static colunaT reconhecimento_location_height=new colunaT("reconhecimento_location_height",
            reconhecimento,varchar,true,"Location");
    public static colunaT reconhecimento_color=new colunaT("reconhecimento_color",
            reconhecimento,varchar,true,"Color");
    public static colunaT reconhecimento_extra=new colunaT("reconhecimento_extra",
            reconhecimento,blob,true,"Extra");
    //Reconhecimento Funcionario
    /*
    *      funcionario.setEndereco(enderecoDigitado);
        funcionario.setTelefone(phone);
        funcionario.setCPF(cpf);
        funcionario.setSetor(sector);
    * */
    public static colunaT reconhecimento_endereco=new colunaT("reconhecimento_endereco",
            reconhecimento,varchar,true,"Endereço");
    public static colunaT reconhecimento_telefone=new colunaT("reconhecimento_telefone",
            reconhecimento,varchar,true,"Telefone");
    public static colunaT reconhecimento_cpf=new colunaT("reconhecimento_cpf",
            reconhecimento,varchar,true,"Cpf");
    public static colunaT reconhecimento_setor=new colunaT("reconhecimento_setor",
            reconhecimento,varchar,true,"Setor");





    public static String admin="admin";
    public static colunaT admin_usuario=new colunaT("admin_usuario",
            admin,varchar,true,"Nome");
    public static colunaT admin_senha=new colunaT("admin_senha",
            admin,varchar,true,"Nome");





    public static String config="config";
    public static colunaT config_deteccaofacial=new colunaT("config_deteccaofacial",
            config,varchar,true,"Reconhecimento Facial");
    public static colunaT config_deteccaomascara=new colunaT("config_deteccaomascara",
            config,varchar,true,"Deteccção Máscara");
    public static colunaT config_leituratermica=new colunaT("config_leituratermica",
            config,varchar,true,"Leitura Térmica");
    public static colunaT config_imagemtermica=new colunaT("config_imagemtermica",
            config,varchar,true,"Imagem Térmica");
    public static colunaT config_emissividade=new colunaT("config_emissividade",
            config,varchar,true,"Emissividade");


}
