package lundy.com.survivor.DataPicker;

import java.util.ArrayList;

import lundy.com.survivor.SetGet.BerandaSetGet;

// List String untuk data Menu yang tampil di Beranda beserta source aset dan Activity yang di Start dalam Package
public class BerandaData {
    public static String[][] data = new String[][]{
                {"Marathon", "lundy.com.survivor.Stopwatch",
                        "https://race131.com/wp-content/themes/race131/assets/images/races-green.png"},
                        {"Pull Up", "lundy.com.survivor.PullUp",
                                "https://cdn0.iconfinder.com/data/icons/gym-workout/308/workout008-512.png"},
                                {"Push Up", "lundy.com.survivor.PushUp",
                                        "https://cdn2.iconfinder.com/data/icons/arrows-56/98/14-arrow-push-out-up-down-512.png"},
                                        {"Compass", "lundy.com.survivor.Compass",
                                                "https://cdn2.iconfinder.com/data/icons/flaturici-set-6/512/compass-512.png"},
                                                {"Calori", "lundy.com.survivor.SeeOnMaps",
                                                        "https://cdn1.iconfinder.com/data/icons/fitness-sport/512/calories-512.png"},
                                                    {"Azimuth", "lundy.com.survivor.RouteDistance",
                                                            "https://cdn2.iconfinder.com/data/icons/picons-basic-2/57/basic2-059_pin_location-512.png"},
    };
        //Untuk menambah Menu Grid cukup tambahkan array string 'Data' diatas
        //Fungsi untuk List String ini akan dimasukkan ke Array List Objek 'BerandaSetget'
        public static ArrayList<BerandaSetGet> getListData(){
            //Deklarasi Objek
            BerandaSetGet berandaSetGet = null;
            //Menyusun Objek ke dalam Array Objek
            ArrayList<BerandaSetGet> list = new ArrayList<>();
            //Mengisi array yang masih kosong dengan isian data satu per satu dari array String
            for (int i = 0; i <data.length; i++) {
                berandaSetGet = new BerandaSetGet();
                berandaSetGet.setName(data[i][0]);
                berandaSetGet.setRemarks(data[i][1]);
                berandaSetGet.setPhoto(data[i][2]);
                list.add(berandaSetGet);
            }
            return list;
        }
    }

