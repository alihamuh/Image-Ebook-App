package books.urdu.alihamuh.urdubooks3;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import books.urdu.alihamuh.urdubooks3.TouchImageView;

public class PageFragment extends Fragment {

    private int imageResource;
    private Bitmap bitmap;
    CommonAppClass Myapp=new CommonAppClass();

    private static final float[] NEGATIVE = {
            -1.0f,     0,     0,    0, 255, // red
            0, -1.0f,     0,    0, 255, // green
            0,     0, -1.0f,    0, 255, // blue
            0,     0,     0, 1.0f,   0  // alpha
    };

    //public static Boolean nightMode=false;
    //public static Boolean highlight=false;





    public static PageFragment getInstance(int resourceID) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("image_source", resourceID);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResource = getArguments().getInt("image_source");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view =inflater.inflate(R.layout.fragment_page, container, false);
        //view.setRotationY(180);

                return view;

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TouchImageView imageView = (TouchImageView) view.findViewById(R.id.fragmentImageView);

        BitmapFactory.Options o = new BitmapFactory.Options();
        //o.inSampleSize = -1;
        o.inDither = false;
        bitmap = BitmapFactory.decodeResource(getResources(), imageResource, o);
        imageView.setImageBitmap(bitmap);
        if(Myapp.getNightmode()) {
            imageView.setColorFilter(new ColorMatrixColorFilter(NEGATIVE));
        }
        if(Myapp.getHighlight()){
            imageView.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }

        onTouchListenersforHidingAndShowingToolbar(imageView);

    }


    @SuppressLint("ClickableViewAccessibility")
    public void onTouchListenersforHidingAndShowingToolbar(TouchImageView imageView){

        imageView.setOnTouchListener(new View.OnTouchListener() {
            private float pointX;
            private float pointY;
            private int tolerance = 100;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        return false; //This is important, if you return TRUE the action of swipe will not take place.
                    case MotionEvent.ACTION_DOWN:
                        pointX = event.getX();
                        pointY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        boolean sameX = pointX + tolerance > event.getX() && pointX - tolerance < event.getX();
                        boolean sameY = pointY + tolerance > event.getY() && pointY - tolerance < event.getY();

                        if(sameX && sameY){
                        //The user "clicked" certain point in the screen or just returned to the same position an raised the finger
                        if (((AppCompatActivity)getActivity()).getSupportActionBar().isShowing()) {

                            //toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

                        } else {
                            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                            //toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                        }

                        if(((AppCompatActivity)getActivity()).findViewById(R.id.seebar_layout).getVisibility()==View.VISIBLE){
                            ((AppCompatActivity)getActivity()).findViewById(R.id.seebar_layout).setVisibility(View.GONE);
                        }else{
                            ((AppCompatActivity)getActivity()).findViewById(R.id.seebar_layout).setVisibility(View.VISIBLE);
                        }

                        }

                }
                return false;
            }
        });

    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        bitmap.recycle();
        bitmap = null;
    }
}