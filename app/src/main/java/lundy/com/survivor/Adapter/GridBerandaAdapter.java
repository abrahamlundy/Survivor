package lundy.com.survivor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import lundy.com.survivor.SeeOnMaps;
import lundy.com.survivor.SetGet.BerandaSetGet;
import lundy.com.survivor.R;
import lundy.com.survivor.Stopwatch;

public class GridBerandaAdapter extends RecyclerView.Adapter<GridBerandaAdapter.GridViewHolder> {

    private Context context;
    private ArrayList<BerandaSetGet> listBerandaSetGet;

    /* =========== MULAI SET GET LOCAL==================*/
    //deklarasi list dengan data bersumber dari BerandaSetGet yang di-listing di BerandaData
    private ArrayList<BerandaSetGet> getListBerandaSetGet() {
        return listBerandaSetGet;
    }

    public void setListBerandaSetGet(ArrayList<BerandaSetGet> listBerandaSetGet) {
        this.listBerandaSetGet = listBerandaSetGet;
    }
    /* ===========AKHIR SET GET LOCAL==================*/



    /* ===========CONTEXT GET DARI BERANDA==================*/
    public GridBerandaAdapter(Context context) {
        this.context = context;
    }


    /* =========== PENEMPAT(INFLATER) HOLDER VIEW DI VIEW UTAMA ==================*/
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_template, parent, false);
            return new GridViewHolder(view);
    }


    /* =========== PENGISI HOLDER ==================
    * Diisi dengan semua item view yang dibuat  di xml
    * */
        @Override
        public void onBindViewHolder(GridViewHolder holder, final int position) {
            Glide.with(context)
                    .load(getListBerandaSetGet().get(position).getPhoto())
                    .override(350, 550)
                    .into(holder.imgPhoto);

            holder.Comm.setText(getListBerandaSetGet().get(position).getName());
            /* ===== Deklarasi tombol aksi bersta listenernya ====*/
            holder.Comm.setOnClickListener(new View.OnClickListener() {
                //Menangkap remarks yang berisi informasi nama activity lanjutan dari tombol aksi
                String activ = String.valueOf(getListBerandaSetGet().get(position).getRemarks());
                @Override
                public void onClick(View view) {
                    //Intent maps = new Intent(context,Stopwatch.class);
                    //view.getContext().startActivity(maps);

                    Intent maps = null;
                    try {
                        //Mencari nama Class dalam variabel 'activ'
                        maps = new Intent(context, Class.forName(activ));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    //Memulai activity baru sesuai class yang diisi pada Intent tujuan
                    view.getContext().startActivity(maps);

                }
            });
    }

    /* =========== PENCACAH POSISI DESKRIPSI ITEM DALAM LIST==================*/
    @Override
    public int getItemCount() {
        return getListBerandaSetGet().size();
    }

    /* ===========PEMBENTUK HOLDER==================*/
    class GridViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        Button Comm;
        GridViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            Comm=itemView.findViewById(R.id.btn_set_comm);
        }

    }
}
