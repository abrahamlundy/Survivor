package lundy.com.survivor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import lundy.com.survivor.Adapter.GridBerandaAdapter;
import lundy.com.survivor.DataPicker.BerandaData;
import lundy.com.survivor.SetGet.BerandaSetGet;

public class Beranda extends AppCompatActivity {

    private RecyclerView rvCategory;
    private ArrayList<BerandaSetGet> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        /* === Deklarasi untuk Toolbar Custom */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        rvCategory = (RecyclerView)findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        list = new ArrayList<>();
        list.addAll(BerandaData.getListData());
        showRecyclerGrid();

    }

    //Fungsu untuk menambahkan layout menu ke Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    // Fungsi untuk memanggil Adapater pengisi Recycle View
    private void showRecyclerGrid(){
        rvCategory.setLayoutManager(new GridLayoutManager(this, 2));
        GridBerandaAdapter gridBerandaAdapter = new
                GridBerandaAdapter(this);
        gridBerandaAdapter.setListBerandaSetGet(list);
        rvCategory.setAdapter(gridBerandaAdapter);
    }

    // Fungsi untuk menghandle aksi menu saat diTap
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu1:
                Toast.makeText(getApplicationContext(), "Simpan", Toast.LENGTH_LONG).show();
               // simpan(ax,ay,az);

                return true;
            case R.id.menu2:
             //   String hasil = load();

                Toast.makeText(getApplicationContext(), "Load:", Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
