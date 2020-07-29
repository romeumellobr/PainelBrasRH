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
import com.google.android.gms.tasks.Task;

import org.tensorflow.lite.examples.detection.Threads.Tr_Cadastro;
import org.tensorflow.lite.examples.detection.bdM.BancoT;


public class CadastroActivity extends AppCompatActivity {


    public BancoT b=null;

    private EditText edtNomeCad;
    private EditText edtEmailCad;
    private EditText edtSenhaCad;
    private Button btnCadastar;
    private Button btnVoltar;

    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

b= LoginActivity.b;

        edtNomeCad = findViewById(R.id.editNome);
        edtEmailCad = findViewById(R.id.editEmail);
        edtSenhaCad = findViewById(R.id.editSenha);
        btnCadastar = findViewById(R.id.buttonCadUser);
        btnVoltar = findViewById(R.id.buttonVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaLogin();
            }
        });

        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoNome = edtNomeCad.getText().toString();
                String textoEmail = edtEmailCad.getText().toString();
                String textoSenha = edtSenhaCad.getText().toString();

                if (!textoNome.isEmpty()){
                    if (!textoEmail.isEmpty()){
                        if (!textoSenha.isEmpty()){

                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrarUsuario();
                            Intent intent = new Intent(CadastroActivity.this, DetectorActivity.class);
                            startActivity(intent);
                        }
                    }
                }

            }
        });
    }




    public void abrirTelaLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void cadastrarUsuario(){
new Tr_Cadastro(true,this,"Salvando","Aguarde!"){

    @Override
    public void Durante() {
       b.insert(b.admin_usuario.Dado(usuario.getEmail()),
               b.admin_senha.Dado(usuario.getSenha()));
    }

    @Override
    public void Depois() {
      //  Mensagem("Salvo com sucesso!","Usuário salvo no banco!");
    }
};

    }

    public void Mensagem(final String titulo, final String texto) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder mensagem = new AlertDialog.Builder(CadastroActivity.this);
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
}