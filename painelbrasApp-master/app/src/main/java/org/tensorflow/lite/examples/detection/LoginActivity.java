package org.tensorflow.lite.examples.detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import org.tensorflow.lite.examples.detection.OpIO.Recognition;
import org.tensorflow.lite.examples.detection.Threads.Tr;
import org.tensorflow.lite.examples.detection.bdM.BancoT;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public  class LoginActivity extends AppCompatActivity {
   public static BancoT b=null;
    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtSenha;
    private String textoEmail;
    private String textoSenha;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        new Tr(this){
            @Override
            public void Durante() {
                 b=new BancoT(LoginActivity.this, true);
b.insert(b.admin_usuario.Dado("teste"),
        b.admin_senha.Dado("teste"));



            }

            @Override
            public void Depois() {
                // Mensagem("Conectou","conexão concluida");
            }
        }
        ;


        setContentView(R.layout.activity_login);

        verifyLogin();
        iniciarComponentes();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textoEmail = edtEmail.getText().toString();
                textoSenha = edtSenha.getText().toString();

                if (!textoEmail.isEmpty()){
                    if (!textoSenha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin();

                    }else {
                        Toast.makeText(LoginActivity.this, "Preencha o campo Senha!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Preencha o campo E-mail!", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    public void abrirTelaPrincipal(){
        Intent intent = new Intent(this, DetectorActivity.class);
        startActivity(intent);
    }



    public void verifyLogin(){

    }

    @Override
    protected void onStart() {
        super.onStart();
        verifyLogin();
    }

    public void validarLogin(){
        EditText user=findViewById(R.id.editTextTextEmailAddress);
        EditText senha=findViewById(R.id.editTextSenha);

        new Tr(true,this,"Acessando, Aguarde!","Autenticando!",user.getText().toString(),senha.getText().toString() ){
            @Override
            public void Durante() {
                while(LoginActivity.this.b==null){



                    tempo(100);
                }


                setUsoGeralB(b.slsW(b.admin_usuario.Dado(getParamet()[2]),
                        b.admin_senha.Dado(getParamet()[3])).numlinha()>0);


            }

            @Override
            public void Depois() {
                if(getUsoGeralB()) {
                    Intent intent = new Intent(LoginActivity.this, DetectorActivity.class);
                    startActivity(intent);
                }else{

                    Mensagem("Erro de acesso!","Usuário ou senha não estão corretos!");


                }
                tempo(800);

            }
        };





    }


    public void Mensagem(final String titulo, final String texto) {
       runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder mensagem = new AlertDialog.Builder(LoginActivity.this);
                mensagem.setTitle(titulo);
                mensagem.setMessage(texto);
                mensagem.setNeutralButton("OK", null);
                mensagem.show();
            }
        });


    }


    public void MensagemOFF(final String titulo, final String texto) {
       runOnUiThread(new Runnable() {

            @Override
            public void run() {



            }
        });


    }


    private void iniciarComponentes(){
        btnLogin = findViewById(R.id.buttonLogin);
        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        edtSenha = findViewById(R.id.editTextSenha);




    }
}