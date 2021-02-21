package freaktemplate.nearbydoctor.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import freaktemplate.nearbydoctor.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Redixbit 2 on 13-08-2016.
 */
public class Doctorlist_adapter extends BaseAdapter {
    private final ArrayList<DoctorlistGetSet> data1;
    private final Activity activity;
    private LayoutInflater inflater = null;
    private final Context context;
    private final String doctor_id;
    private final Typeface TF_opensansRegular;

    private final Typeface TF_ralewayRegular;
    public Doctorlist_adapter(Activity a, ArrayList<DoctorlistGetSet> nearbylist,Context context1,String doctor) {

        activity = a;
        data1 = nearbylist;
        context = context1;
        doctor_id=doctor;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TF_opensansRegular= Typeface.createFromAsset(activity.getAssets(), "fonts/OpenSans-Regular.ttf");
        TF_ralewayRegular= Typeface.createFromAsset(activity.getAssets(),"fonts/Raleway-Regular_4.ttf");
    }

    @Override
    public int getCount() {
        return data1.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.cell_doctorlist, parent,false);
        }

        ImageView img_corner= vi.findViewById( R.id.corner);
        TextView txt_letter= vi.findViewById(R.id.letter);
        String CurrentLang = Locale.getDefault().getDisplayLanguage();
        Log.e( "langyage", "setLanguage: " + CurrentLang );


        switch (doctor_id) {
            case "1":
                if (CurrentLang.equals( "العربية" )) {
                    img_corner.setBackgroundResource(R.drawable.d_bg1);
                    txt_letter.setText(R.string.doctor_symbol);
                }else{
                    img_corner.setBackgroundResource(R.drawable.d_bg);
                    txt_letter.setText(R.string.doctor_symbol);
                }

                break;
            case "2":
                if (CurrentLang.equals( "العربية" )) {
                    LanguageSelectore.setLanguage( "ar", getApplicationContext() );
                    img_corner.setBackgroundResource(R.drawable.p_bg1);
                    txt_letter.setText(R.string.pharmacy_symbol);
                }else{
                    img_corner.setBackgroundResource(R.drawable.p_bg);
                    txt_letter.setText(R.string.pharmacy_symbol);
                }

                break;
            case "3":
                if (CurrentLang.equals( "العربية" )) {
                    LanguageSelectore.setLanguage( "ar", getApplicationContext() );
                    img_corner.setBackgroundResource(R.drawable.h_bg1);
                    txt_letter.setText(R.string.hospital_symbol);
                }else{
                    img_corner.setBackgroundResource(R.drawable.h_bg);
                    txt_letter.setText(R.string.hospital_symbol);
                }
                break;
        }

        TextView txt_name = vi.findViewById(R.id.txt_name);
        txt_name.setText((data1.get(position).getName()));
        txt_name.setTypeface(TF_opensansRegular);

        TextView txt_desc1 = vi.findViewById(R.id.txt_desc);
        txt_desc1.setText((data1.get(position).getServices()));
        txt_desc1.setTypeface(TF_ralewayRegular);

        ImageView imgdoc = vi.findViewById(R.id.imgdoc);
        String image = data1.get(position).getIcon().replace(" ", "%20");
        Picasso.with(getApplicationContext()).load(activity.getString(R.string.link1)+"uploads/"+image).fit().into(imgdoc);

        RatingBar rb = vi.findViewById(R.id.txt_rate);
        rb.setRating(Float.parseFloat(data1.get(position).getRatting()));
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Auto-generated
                Log.d("rate", "" + rating);
            }
        });
        Typeface TF_ralewayMidium = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Medium_2.ttf");

        TextView txt_ratenumber = vi.findViewById(R.id.txt_ratenumber);
        txt_ratenumber.setText(""+Float.parseFloat(data1.get(position).getRatting()));
        txt_desc1.setTypeface(TF_ralewayMidium);

        TextView txt_distance = vi.findViewById(R.id.txt_distance);
        double numbar=roundMyData(Double.parseDouble(data1.get(position).getDistance()),1);
        txt_distance.setText(numbar +" "+activity.getString(R.string.distance_miles));
        txt_desc1.setTypeface(TF_ralewayMidium);

        TextView txt_distancetitle = vi.findViewById(R.id.txt_distancetitle);
        TextView txt_ratetitle = vi.findViewById(R.id.txt_ratetitle);

        txt_distancetitle.setTypeface(TF_ralewayMidium);
        txt_ratetitle.setTypeface(TF_ralewayMidium);

        return vi;
    }

    private static double roundMyData(double Rval, int numberOfDigitsAfterDecimal) {
        double p = (float) Math.pow(10,numberOfDigitsAfterDecimal);
        Rval = Rval * p;
        double tmp = Math.floor(Rval);
        return tmp /p;
    }

}
