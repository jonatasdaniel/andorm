package br.com.viisi.teste;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.viisi.teste.infra.persistence.PMFactory;
import br.com.viisi.teste.infra.persistence.dao.PessoaDao;
import br.com.viisi.teste.model.Pessoa;

public class MainActivity extends Activity {

	private EditText nomeId;
	private EditText enderecoId;
	private Button buttonSalvarId;
	private Button buttonBuscarId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createComponentsView();
		createComponentsListners();
	}

	private OnClickListener salvarListner = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String nome = nomeId.getText().toString();
			String endereco = enderecoId.getText().toString();

			Pessoa p = new Pessoa();
			p.setNome(nome);
			p.setEndereco(endereco);

			PessoaDao pdao = new PessoaDao(PMFactory.get(getApplicationContext()));
			pdao.save(p);
		}
	};

	private OnClickListener buscarListner = new OnClickListener() {
		@Override
		public void onClick(View v) {
			PessoaDao pdao = new PessoaDao(PMFactory.get(getApplicationContext()));

			List<Pessoa> pessoas = pdao.findByNome("nome");
			for (Pessoa pessoa : pessoas) {
				System.out.println("Id: " + pessoa.getId() + " - Nome: " + pessoa.getNome() + " - Endereço: " + pessoa.getEndereco());
			}
		}
	};

	private void createComponentsListners() {
		buttonSalvarId.setOnClickListener(salvarListner);
		buttonBuscarId.setOnClickListener(buscarListner);
	}

	private void createComponentsView() {
		nomeId = (EditText) findViewById(R.id.nomeId);
		enderecoId = (EditText) findViewById(R.id.enderecoId);
		buttonSalvarId = (Button) findViewById(R.id.buttonSalvarId);
		buttonBuscarId = (Button) findViewById(R.id.buttonBuscarId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
